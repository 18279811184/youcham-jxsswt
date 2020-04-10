package io.youcham.modules.wmyj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.exception.RRException;
import io.youcham.common.utils.ListUtils;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.sys.shiro.ShiroUtils;
import io.youcham.modules.wmyj.dao.WmyjInfoDao;
import io.youcham.modules.wmyj.entity.WmyjCityareaEntity;
import io.youcham.modules.wmyj.entity.WmyjInfoEntity;
import io.youcham.modules.wmyj.service.WmyjInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("wmyjInfoService")
public class WmyjInfoServiceImpl extends ServiceImpl<WmyjInfoDao, WmyjInfoEntity> implements WmyjInfoService {

    @Autowired
    private WmyjCityareaServiceImpl wmyjCityareaService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String queryDate = (String) params.get("queryDate");
        String auditFlag = (String)params.get("auditFlag");
        String enterpriseName = (String)params.get("enterpriseName");
        String city = (String)params.get("city");
        String area = (String)params.get("area");
        String userid = ShiroUtils.getUserId();
        String userType = userid.substring(2,6);  //后四位为0，表示省级用户
        String userStr = null;
        if("0000".equals(userType)){
            //省级统计
            //params.put("userid","36%");   //36%
            userStr = "36%";
        }else{
            String userType2 = userid.substring(4,6); //最后2位为0，表示市级用户
            if("00".equals(userType2)){
                //市级统计
                /*params.put("userid",userid.substring(0,4)+"%");  //市级前4位*/
                userStr = userid.substring(0,4)+"%";
            }else{
                //县级用户
                //params.put("userid",userid);
                userStr = userid;
            }
        }
        Page<WmyjInfoEntity> page = this.selectPage(
                new Query<WmyjInfoEntity>(params).getPage(),
                new EntityWrapper<WmyjInfoEntity>()
                        .eq(StringUtils.isNotEmpty(auditFlag),"SHOW_FLAG",auditFlag)
                        .eq(StringUtils.isNotEmpty(city),"CITY_CODE",city)
                        .eq(StringUtils.isNotEmpty(area),"AREA_CODE",area)
                        .like("USERID",userStr)
                        .eq("BE_DELETE",0)
                        .eq(StringUtils.isNotEmpty(enterpriseName),"ENTERPRISE_NAME",enterpriseName)
                        .and(StringUtils.isNotEmpty(queryDate),"to_char(DATE_IN,'yyyy-MM') = {0}",queryDate)
                        .orderBy("DATE_IN desc")
        );

        return new PageUtils(page);
    }
    /*
    审核
     */
    @Override
    public void audit(String[] ids, String flag) {
        //一条条更新，业务全写在这里
        String userid = ShiroUtils.getUserId();
        String city =userid.substring(2,6);
        for(String id:ids){
            WmyjInfoEntity wmyjInfo = selectById(id);
            if(city.equals("0000")&&flag.equals("1")){
                //String id,String showFlag,String statisFlag,String statisUserid
                wmyjInfo.setShowFlag("2");
                wmyjInfo.setStatisFlag("1");
                wmyjInfo.setStatisUserid(userid);
                //m = service.UpdateFlag(id,"2","1",userid);
            }else {
                if(!city.equals("0000")&&flag.equals("1")){
                    wmyjInfo.setShowFlag("1");
                    wmyjInfo.setStatisFlag("1");
                    wmyjInfo.setStatisUserid(userid);
                   // m = service.UpdateFlag(id,"1","1",userid);
                }else {
                    if(city.equals("0000")&&flag.equals("0")){
                        wmyjInfo.setShowFlag("4");
                        wmyjInfo.setStatisFlag("0");
                        wmyjInfo.setStatisUserid(userid);
                        //m = service.UpdateFlag(id,"4","0",userid);
                    }else {
                        if(!city.equals("0000")&&flag.equals("0")){
                            wmyjInfo.setShowFlag("3");
                            wmyjInfo.setStatisFlag("0");
                            wmyjInfo.setStatisUserid(userid);
                            //m = service.UpdateFlag(id,"3","0",userid);
                        }
                    }
                }
            }
            updateAllColumnById(wmyjInfo);
        }

    }
    /*
    汇总统计数据
     */
    @Override
    public PageUtils countData(Map<String, Object> params){
       List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  //表格list内容，sql返回列表
        //params.put("queryDate","2020-01");
        Page<Map<String, Object>> page = new Query<Map<String,Object>>(params).getPage();  //分页插件
        //根据用户userid来筛选记录
        String userid = ShiroUtils.getUserId();
       // System.out.println("userid是：" + userid);
        String city = ShiroUtils.getUserEntity().getCity();
       // System.out.println("用户所在县市是：" + city);
        //判断用户类型，分为省级用户、市级用户、县级用户
        String userType = userid.substring(2,6);  //后四位为0，表示省级用户
        if("0000".equals(userType)){
            //省级统计
            params.put("userid","36%");   //36%
            list =  baseMapper.countDataQSHJ(page,params);

        }else{
            String userType2 = userid.substring(4,6); //最后2位为0，表示市级用户
           // System.out.println("用户的最后二个字符是：" + userType2 + "它对应的userid参数值是：" +userid.substring(0,4)+"%");
            if("00".equals(userType2)){
                //市级统计
                params.put("userid",userid.substring(0,4)+"%");  //市级前4位
                params.put("CITY_AREA",ShiroUtils.getUserEntity().getName()+"全市");  //名字就是各市名
                list =  baseMapper.countDataSJHZ(page,params);
            }else{
                //县级用户
                params.put("userid",userid);
                params.put("CITY_AREA",ShiroUtils.getUserEntity().getName());  //名字就是县区名
                list =  baseMapper.countDataXJHZ(page,params);
            }
        }
        //算本月同比
        for(Map<String,Object> entry:list){
            BigDecimal month_amount = (BigDecimal) entry.get("MONTH_AMOUNT");
            BigDecimal YEAR_AMOUNT = (BigDecimal) entry.get("YEAR_AMOUNT");
            //上个月不能为0，不然除不了
            int i=YEAR_AMOUNT.compareTo(BigDecimal.ZERO);
            //num12.subtract(num22)减法
            //BigDecimal result52 = num22.divide(num12,20,BigDecimal.ROUND_HALF_UP);除法
            if(i==1){ //比0大
                BigDecimal MONTH_PRECENT = (month_amount.subtract(YEAR_AMOUNT)).divide(YEAR_AMOUNT,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                entry.put("MONTH_PRECENT",MONTH_PRECENT);
            }else{
                entry.put("MONTH_PRECENT","");
            }



            List<WmyjCityareaEntity> wmyjCityareaList = wmyjCityareaService.selectList(
                    new EntityWrapper<WmyjCityareaEntity>().eq("area",entry.get("CITY_AREA")));
            if(ListUtils.isNotEmpty(wmyjCityareaList)){
                entry.put("AREA_CODE",wmyjCityareaList.get(0).getCityareacode());
            }

        }


        page.setRecords(list);

        return new PageUtils(page);
    }

    @Override
    public List<WmyjInfoEntity> queryData2Export(Map<String, Object> params) {
        String queryDate = (String) params.get("queryDate");
        String auditFlag = (String)params.get("auditFlag");
        String enterpriseName = (String)params.get("enterpriseName");
        String city = (String)params.get("city");
        String area = (String)params.get("area");
        String userid = ShiroUtils.getUserId();
        String userType = userid.substring(2,6);  //后四位为0，表示省级用户
        String userStr = null;
        if("0000".equals(userType)){
            //省级统计
            //params.put("userid","36%");   //36%
            userStr = "36%";
        }else{
            String userType2 = userid.substring(4,6); //最后2位为0，表示市级用户
            if("00".equals(userType2)){
                //市级统计
                /*params.put("userid",userid.substring(0,4)+"%");  //市级前4位*/
                userStr = userid.substring(0,4)+"%";
            }else{
                //县级用户
                //params.put("userid",userid);
                userStr = userid;
            }
        }
        List<WmyjInfoEntity> list = new ArrayList<WmyjInfoEntity>();
        list = this.selectList(new EntityWrapper<WmyjInfoEntity>()
                .eq(StringUtils.isNotEmpty(auditFlag),"SHOW_FLAG",auditFlag)
                .eq(StringUtils.isNotEmpty(city),"CITY_CODE",city)
                .eq(StringUtils.isNotEmpty(area),"AREA_CODE",area)
                .like("USERID",userStr)
                .eq("BE_DELETE",0)
                .eq(StringUtils.isNotEmpty(enterpriseName),"ENTERPRISE_NAME",enterpriseName)
                .and(StringUtils.isNotEmpty(queryDate),"to_char(DATE_IN,'yyyy-MM') = {0}",queryDate)
                .orderBy("DATE_IN desc"));
       /* Page<WmyjInfoEntity> page = this.selectPage(
                new Query<WmyjInfoEntity>(params).getPage(),
                new EntityWrapper<WmyjInfoEntity>()
                        .eq(StringUtils.isNotEmpty(auditFlag),"SHOW_FLAG",auditFlag)
                        .eq(StringUtils.isNotEmpty(city),"CITY_CODE",city)
                        .eq(StringUtils.isNotEmpty(area),"AREA_CODE",area)
                        .like("USERID",userStr)
                        .eq("BE_DELETE",0)
                        .eq(StringUtils.isNotEmpty(enterpriseName),"ENTERPRISE_NAME",enterpriseName)
                        .and(StringUtils.isNotEmpty(queryDate),"to_char(DATE_IN,'yyyy-MM') = {0}",queryDate)
                        .orderBy("DATE_IN desc")
        );*/
        return list;
        //return new PageUtils(page);
    }

    @Transactional(rollbackOn = Exception.class)
    public String importAllExcel(List<Map<String, Object>> customerList,String dt) {

        //int updateRepeat = 0;//失败：重复导入数
        int importSuccess = 0;//成功导入数
        DecimalFormat df = new DecimalFormat("#.00");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        //这里要从0开始，因为读excel的时候，就已经没读表头
        for (int i = 0; i < customerList.size(); i++) {

            Map<String, Object> map = customerList.get(i);
            if(map == null || (String) map.get("cell0") == null || ((String) map.get("cell0")).equalsIgnoreCase("")){
                break;
            }
            int countNum = i + 2;
            /*System.out.println("---i=" + i);*/
            /*if (i < 2) {
                continue;
            }*/

            WmyjInfoEntity wmyjInfoEntity = new WmyjInfoEntity();
            //默认值设置
            wmyjInfoEntity.setCreateId(ShiroUtils.getUserId());
            wmyjInfoEntity.setCreateTime(new Date());
            wmyjInfoEntity.setCreateName(ShiroUtils.getUserEntity().getName());
            wmyjInfoEntity.setUserid(ShiroUtils.getUserId());
            //wmyjInfo.setAreaCode(ShiroUtils.getUserId());
            //填空所属市，区字段

            WmyjCityareaEntity cityareaEntity = wmyjCityareaService.getWmyjCityareaEntityByCode(ShiroUtils.getUserId());
            if (cityareaEntity != null) {
                wmyjInfoEntity.setAreaCode(cityareaEntity.getArea());
                wmyjInfoEntity.setCityCode(cityareaEntity.getCity());
            }
            //填写excel的数据

            wmyjInfoEntity.setEnterpriseCode((String) map.get("cell0"));// 获取第一列值，企业代码
            wmyjInfoEntity.setEnterpriseName((String) map.get("cell1"));// 获取第二列值，企业名称
            wmyjInfoEntity.setUnitCode((String) map.get("cell2"));//第三列
            try {
                String temp = (String) map.get("cell3");   //第四列  ,日期
                if (!StringUtils.isEmpty(temp)) {
                    wmyjInfoEntity.setDateIn(sdf1.parse(temp));
                }
            } catch (ParseException e) {
                e.printStackTrace();
                //stJjtossShipline.setKbDate(null);
                throw new RRException("第" + countNum + "条数据 报出日期 日期格式有误！");
            }
            wmyjInfoEntity.setMonthAmount(new BigDecimal((String) map.get("cell4")));  //第5列，数字，本月预计
            wmyjInfoEntity.setYearAmount(new BigDecimal((String) map.get("cell5")));  //第6列，上月
            wmyjInfoEntity.setNewtelAmount(new BigDecimal((String) map.get("cell6")));
            wmyjInfoEntity.setElecAmount(new BigDecimal((String) map.get("cell7")));
            wmyjInfoEntity.setFarmAmount(new BigDecimal((String) map.get("cell8")));
            wmyjInfoEntity.setMetalAmount(new BigDecimal((String) map.get("cell9")));
            wmyjInfoEntity.setWeaveAmount(new BigDecimal((String) map.get("cell10")));
            wmyjInfoEntity.setLightinduAmount(new BigDecimal((String) map.get("cell11")));
            wmyjInfoEntity.setSteelAmount(new BigDecimal((String) map.get("cell12")));
            wmyjInfoEntity.setMedicineAmount(new BigDecimal((String) map.get("cell13")));
            wmyjInfoEntity.setMaterialsAmount(new BigDecimal((String) map.get("cell14")));
            wmyjInfoEntity.setOtherAmount(new BigDecimal((String) map.get("cell15")));
            //第17列，订单情况，是要转成数字的 -1，0， 1
            String orderFlag = (String) map.get("cell16");
            if ("订单增加".equals(orderFlag)) {
                wmyjInfoEntity.setOrderFlag(1);
            } else if ("订单持平".equals(orderFlag)) {
                wmyjInfoEntity.setOrderFlag(0);
            } else {
                wmyjInfoEntity.setOrderFlag(-1);
            }

            //第18列，价格情况，是要转成数字的 -1，0， 1
            String priceFlag = (String) map.get("cell17");
            if ("价格上涨".equals(priceFlag)) {
                wmyjInfoEntity.setPriceFlag(1);
            } else if ("价格持平".equals(priceFlag)) {
                wmyjInfoEntity.setPriceFlag(0);
            } else {
                wmyjInfoEntity.setPriceFlag(-1);
            }
            //第19列，存在的问题
            wmyjInfoEntity.setQuestions((String) map.get("cell18"));
            //第20列，意见
            wmyjInfoEntity.setSuggest((String) map.get("cell19"));
            //第21列，联系人
            wmyjInfoEntity.setContact((String) map.get("cell20"));
            //第22列，电话
            wmyjInfoEntity.setTelephone((String) map.get("cell21"));
            this.insert(wmyjInfoEntity);
            importSuccess++;


        }
        String str = "导入新增" + importSuccess + "条。";
        return str;
    }
}

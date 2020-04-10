package io.youcham.modules.wmyj.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.youcham.common.utils.*;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.modules.sys.shiro.ShiroUtils;
import io.youcham.modules.wmyj.entity.WmyjCityareaEntity;
import io.youcham.modules.wmyj.entity.WmyjInfoEntity;
import io.youcham.modules.wmyj.service.WmyjCityareaService;
import io.youcham.modules.wmyj.service.WmyjInfoService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 出口信息金额汇总表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-24 09:03:48
 */
@RestController
@RequestMapping("wmyj/wmyjinfo")
public class WmyjInfoController {


    @Autowired
    private WmyjInfoService wmyjInfoService;
    @Autowired
    private WmyjCityareaService wmyjCityareaService;


    /**
     * 导入
     *
     * @param
     * @return
     */

    @RequestMapping("/importExcel")
    /*@RequiresPermissions("st:stjjtossshipline:save")*/
 /*   @Transactional*/
    public R loadData(WmyjInfoEntity wmyjInfoEntity, HttpServletRequest request) throws ParseException {
        MultipartFile multipartFile = null;

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String dt = "";
        if (isMultipart) {
            MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request,
                    MultipartHttpServletRequest.class);
            multipartFile = multipartRequest.getFile("file");
            // dt = (String) multipartRequest.getParameter("dtDate");
            if (null == multipartFile) {// 有更改文件
                return R.error("未上传文件");
            }

        }
        // 创建处理EXCEL
        ReadExcel readExcel = new ReadExcel();
        // 解析excel，获取信息集合，传入文件名和文件对象。
        List<Map<String, Object>> customerList = readExcel.getExcelInfo_wmyjByLhf(multipartFile.getOriginalFilename(),
                multipartFile);
        if (customerList.size() == 0) {
            return R.error("上传失败");
        }

        String str = wmyjInfoService.importAllExcel(customerList, dt);


        return R.ok(str);
    }


    /**
     * 汇总数据
     */
    @RequestMapping("/countData")
    /*@RequiresPermissions("wmyj:wmyjinfo:countData")*/
    public R countData(@RequestParam Map<String, Object> params){

        PageUtils page  = wmyjInfoService.countData(params);
        return R.ok().put("page", page);
    }


    /**
     * 市级导出数据
     */
    @RequestMapping("/cityExcept")
    /*@RequiresPermissions("wmyj:wmyjinfo:cityExcept")*/
    public R cityExcept(@RequestParam Map<String, Object> params){
        //查出数据
        //PageUtils page  = wmyjInfoService.countData(params);
        //将数据转成excel导出，导出格式有三页，要注意

        ExcelUtil excelUtil = new ExcelUtil();
        List<String> columnList= new ArrayList<>();


        return R.ok();
        //return R.ok().put("page", page);
    }


    /**
     * 查询列表 查询框查询
     */
    @RequestMapping("/queryInfo")
    @RequiresPermissions("wmyj:wmyjinfo:queryInfo")
    public R queryInfo(@RequestParam Map<String, Object> params){
        //params.put("limit","20");
        PageUtils page = wmyjInfoService.queryPage(params);

        return R.ok().put("page", page);
    }
    /*
    导出
     */
    @RequestMapping("/exportExcel")
    public R export (@RequestParam Map<String, Object> params,HttpServletResponse response) {



        ExcelUtil excelUtil = new ExcelUtil();
        List<String> columnList= new ArrayList<>();
        columnList.add("审核状态");
        columnList.add("所属市");

        columnList.add("企业代码");
        columnList.add("企业名称");
        columnList.add("所属单位代码");
        columnList.add("报出日期");
        columnList.add("本月预计出口额");
        columnList.add("上年本月出口额");
        columnList.add("高新技术产品");
        columnList.add("机电产品");
        columnList.add("农产品及其深加工产品");
        columnList.add("有色金属");
        columnList.add("纺织服装");
        columnList.add("轻工产品");
        columnList.add("钢材和铁合金");
        columnList.add("医药化工");
        columnList.add("建材");
        columnList.add("其他产品");
        columnList.add("订单情况");
        columnList.add("价格情况");
        Map<String,Object> map =  excelUtil.exportExcel("出口信息汇总表",columnList);
        HSSFSheet sheet = (HSSFSheet) map.get("sheet");
        HSSFWorkbook wb = (HSSFWorkbook) map.get("wb");
        // 创建一个时间转换类型作为文件名
        SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> map1 = new HashMap<>();
        //Date queryDate = new Date();
        //测试，只查询当前月的数据并导出
        //map1.put("queryDate","2020-02");
       /* String queryDate = (String)params.get("queryDate");
        String auditFlag = (String)params.get("auditFlag");

        System.out.println("queryDate值为：" + queryDate );
        System.out.println("auditFlag值为：" + auditFlag );*/
        map.put("limit","99999");
        //PageUtils pageUtils = wmyjInfoService.queryPage(params);
        //List<WmyjInfoEntity> list = (List<WmyjInfoEntity>) pageUtils.getList();
        List<WmyjInfoEntity> list = wmyjInfoService.queryData2Export(params);
        //System.out.println("list的长度为" + list.size());
        for (int i = 0; i < list.size();i++) {
            WmyjInfoEntity entity = list.get(i);
           // SysDeptEntity entity1 = sysDeptService.selectById(ShiroUtils.getUserEntity().getDeptId());
            HSSFRow row3 = sheet.createRow(i + 2);
            //row3.createCell(0).setCellValue(sj.format(entity.getShowFlag()));
            String showFlagStr = entity.getShowFlag();
            String showFlag = null;
            if ("0".equals(showFlagStr)) {
                showFlag = "未审核";
            } else if("1".equals(showFlagStr)){
                showFlag = "市级已通过";
            }else if("2".equals(showFlagStr)){
                showFlag = "省级已通过";
            }else if("3".equals(showFlagStr)){
                showFlag = "市级未通过";
            }else if("4".equals(showFlagStr)){
                showFlag = "省级未通过";
            }
            row3.createCell(0).setCellValue(showFlag);
            row3.createCell(1).setCellValue(entity.getCityCode());
            row3.createCell(2).setCellValue(entity.getEnterpriseCode());
            row3.createCell(3).setCellValue(entity.getEnterpriseName());
            row3.createCell(4).setCellValue(entity.getUnitCode());
            row3.createCell(5).setCellValue(sj.format(entity.getDateIn()));
            row3.createCell(6).setCellValue(String.valueOf(entity.getMonthAmount()));
            row3.createCell(7).setCellValue(String.valueOf(entity.getYearAmount()));
            row3.createCell(8).setCellValue(String.valueOf(entity.getNewtelAmount()));
            row3.createCell(9).setCellValue(String.valueOf(entity.getElecAmount()));
            row3.createCell(10).setCellValue(String.valueOf(entity.getFarmAmount()));
            row3.createCell(11).setCellValue(String.valueOf(entity.getMetalAmount()));
            row3.createCell(12).setCellValue(String.valueOf(entity.getWeaveAmount()));
            row3.createCell(13).setCellValue(String.valueOf(entity.getLightinduAmount()));
            row3.createCell(14).setCellValue(String.valueOf(entity.getSteelAmount()));
            row3.createCell(15).setCellValue(String.valueOf(entity.getMedicineAmount()));
            row3.createCell(16).setCellValue(String.valueOf(entity.getMetalAmount()));
            row3.createCell(17).setCellValue(String.valueOf(entity.getOtherAmount()));
            int orderFlag = entity.getOrderFlag();
            String orderFlagStr = null;
            if (orderFlag == 0) {
                orderFlagStr = "订单持平";
            } else if(orderFlag == 1){
                orderFlagStr = "订单增加";
            }else if(orderFlag == -1){
                orderFlagStr = "订单减少";
            }

            row3.createCell(18).setCellValue(orderFlagStr);
            int priceFlag = entity.getPriceFlag();
            String priceFlagStr = null;
            if (priceFlag == 0) {
                priceFlagStr = "价格持平";
            } else if(priceFlag == 1){
                priceFlagStr = "价格增加";
            }else if(priceFlag == -1){
                priceFlagStr = "价格减少";
            }
            row3.createCell(19).setCellValue(priceFlagStr);
           // row3.createCell(7).setCellValue(entity1.getName());
        }
        for (int i = 0; i < 25; i++) {
            sheet.autoSizeColumn((short) i);
        }
        // 创建一个时间转换类型作为文件名
        SimpleDateFormat filename = new SimpleDateFormat("yyyyMMddHHmmss");
        // 生成一个100以内的随机数
        Integer x = (int) (Math.random() * 100);
        // 把随机数拼接到文件名
        String fileNamez = filename.format(new Date()) + x;

        // 读取配置文件中的路径
        String fileName = fileNamez + x;

        try {
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + "x.xls");
            response.setContentType("application/json;charset=UTF-8");
            wb.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wmyj:wmyjinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmyjInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 自动填充
     */
    @RequestMapping("/autoFill")
    /*@RequiresPermissions("wmyj:wmyjinfo:autoFill")*/
    public R autoFill(@RequestBody WmyjInfoEntity wmyjInfo2){
        ValidatorUtils.validateEntity(wmyjInfo2,AddGroup.class);

        List<WmyjInfoEntity> wmyjInfos = wmyjInfoService.selectList(new EntityWrapper<WmyjInfoEntity>()
                .eq("ENTERPRISE_CODE",wmyjInfo2.getEnterpriseCode()).orderBy("CREATE_TIME desc")
        );
        if(wmyjInfos.size()==0){
            return R.ok();
        }else{
            WmyjInfoEntity wmyjInfo = wmyjInfos.get(0);
            return R.ok().put("wmyjInfo", wmyjInfo);
        }

    }

    /**
     * 根据企业代码查询填报信息
     */
    @RequestMapping("/infoByCode/{code}")
    @RequiresPermissions("wmyj:wmyjinfo:infoByCode")
    public R infoByCode(@PathVariable("code") String code){
        List<WmyjInfoEntity> wmyjInfos = wmyjInfoService.selectList(new EntityWrapper<WmyjInfoEntity>()
            .eq("ENTERPRISE_CODE",code).orderBy("CREATE_TIME desc")
        );
        if(wmyjInfos.size()==0){
            return R.ok();
        }else{
            WmyjInfoEntity wmyjInfo = wmyjInfos.get(0);
            return R.ok().put("wmyjInfo", wmyjInfo);
        }

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wmyj:wmyjinfo:info")
    public R info(@PathVariable("id") String id){
        WmyjInfoEntity wmyjInfo = wmyjInfoService.selectById(id);

        return R.ok().put("wmyjInfo", wmyjInfo);
    }
    /**
     * 审核
     */
    @RequestMapping("/audit")
    @RequiresPermissions("wmyj:wmyjinfo:audit")

    public R audit(@RequestParam(value = "ids[]")String[] ids,@RequestParam(value = "auditFlag")String auditFlag){
        wmyjInfoService.audit(ids,auditFlag);
        return R.ok();
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wmyj:wmyjinfo:save")
    public R save(@RequestBody WmyjInfoEntity wmyjInfo){
    	ValidatorUtils.validateEntity(wmyjInfo,AddGroup.class);

   	 	wmyjInfo.setCreateId(ShiroUtils.getUserId());
    	wmyjInfo.setCreateTime(new Date());
        wmyjInfo.setCreateName(ShiroUtils.getUserEntity().getName());
        wmyjInfo.setUserid(ShiroUtils.getUserId());
        //wmyjInfo.setAreaCode(ShiroUtils.getUserId());
        //填空所属市，区字段

        WmyjCityareaEntity cityareaEntity = wmyjCityareaService.getWmyjCityareaEntityByCode(ShiroUtils.getUserId());
        if(cityareaEntity!=null){
            wmyjInfo.setAreaCode(cityareaEntity.getArea());
            wmyjInfo.setCityCode(cityareaEntity.getCity());
        }
        wmyjInfoService.insert(wmyjInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wmyj:wmyjinfo:update")
    public R update(@RequestBody WmyjInfoEntity wmyjInfo){
        ValidatorUtils.validateEntity(wmyjInfo,AddGroup.class);
       
        wmyjInfo.setUpdateId(ShiroUtils.getUserId());
    	wmyjInfo.setUpdateTime(new Date());
        wmyjInfo.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = wmyjInfoService.updateAllColumnById(wmyjInfo);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wmyj:wmyjinfo:delete")
    public R delete(@RequestBody String[] ids){
        wmyjInfoService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     *  导出金额汇总
     * @param params
     * @param response
     * @return
     */
    @RequestMapping("exportMoneyTotalExcel")
    public R exportMoneyTotalExcel(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        /* 文件保存路径 */
        String filePath = (String) params.get("filePath");
        //用工具类把数据转换为map（用于替换模板参数）
        Map<String, Object> map = new HashMap<String, Object>();

        // 行数据集合
        params.put("limit","65536");
        PageUtils pageRes  =  wmyjInfoService.countData(params);
        List<Map<String, Object>> dataListRes = (List<Map<String, Object>>) pageRes.getList();
        //加上省市代码
        Map<String,String> area_code = new HashMap<>();
        area_code.put("全省合计","360000");
        area_code.put("南昌市","360100");
        area_code.put("景德镇市","360200");
        area_code.put("萍乡市","360300");
        area_code.put("九江市","360400");
        area_code.put("新余市","360500");
        area_code.put("鹰潭市","360600");
        area_code.put("赣州市","360700");
        area_code.put("宜春市","360800");
        area_code.put("上饶市","360900");
        area_code.put("吉安市","361000");
        area_code.put("抚州市","361100");
        for(Map<String, Object> entry:dataListRes){
            String CITY_AREA = (String)entry.get("CITY_AREA");
            entry.put("AREA_CODE",area_code.get(CITY_AREA));
        }

        map.put("dataList",dataListRes);

        try {
            DocUtil du = new DocUtil();
            response.setCharacterEncoding("utf-8");
            du.createAndDown(map, "wmyj_info_money_total", response.getWriter());

            System.out.println("导出外贸预警金额汇总");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }


}

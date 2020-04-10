package io.youcham.modules.ins.service.impl;

import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.ins.dao.InsInformDao;
import io.youcham.modules.ins.entity.InsColumnEntity;
import io.youcham.modules.ins.entity.InsInformEntity;
import io.youcham.modules.ins.entity.SysFiletableEntity;
import io.youcham.modules.ins.service.InsColumnService;
import io.youcham.modules.ins.service.InsInformService;
import io.youcham.modules.ins.service.SysFiletableService;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("insInformService")
public class InsInformServiceImpl extends ServiceImpl<InsInformDao, InsInformEntity> implements InsInformService {

	@Autowired
	private InsColumnService insColumnService;
    @Autowired
    private SysFiletableService sysFiletableService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	String columnid = (String)params.get("columnid");
    	String informtitle = (String)params.get("informtitle");
    	//发布状态
    	String bePublish = (String)params.get("bePublish");
    			
        Page<InsInformEntity> page = this.selectPage(
                new Query<InsInformEntity>(params).getPage(),
                /*new EntityWrapper<InsInformEntity>()*/
                new EntityWrapper<InsInformEntity>()
                .eq(StringUtils.isNotBlank(columnid),"column_id", columnid)
                .eq(StringUtils.isNotBlank(bePublish),"be_publish", bePublish)
                .like(StringUtils.isNotBlank(informtitle),"inform_title", informtitle)
                .orderBy("column_id,inform_order desc,createTime desc")
              
        );
       

        for (InsInformEntity inform : page.getRecords()) {
        	InsColumnEntity insColumn = insColumnService.selectById(inform.getColumnId());
        	if(insColumn!=null){
        		inform.setInsColumn(insColumn);
        	}
        	
        	
        	//若有多图 只读最后一章
        	String bytearrayId = inform.getInformImage();
   		    if(bytearrayId.indexOf(",") >= 0){
   			 bytearrayId = bytearrayId.substring(0,bytearrayId.length() - 1);
   			 int one = bytearrayId.lastIndexOf(",");
   			 bytearrayId = bytearrayId.substring((one+1),bytearrayId.length());
            }
        	if(inform.getInformImage()!=null&&inform.getInformImage()!=""){
        	SysFiletableEntity sysFiletable = sysFiletableService.selectById(bytearrayId);
        	if(sysFiletable == null){
        		inform.setInformImage("000");
        	}
        	}
        	//set 图片路径
        	//add by xcg        	
        	/*String getimgid = inform.getInformImage();
        	
        	if(getimgid != "" && getimgid !=null){
    		// console.log("几区图片id"+getimgid.substring(0,getimgid.indexOf(',')));
    		 String mying = getimgid.substring(0,getimgid.indexOf(','));
    		 if(mying !=null && mying!=""){
    			   //查询图片的方法
    			 SysFiletableEntity sysfile = sysFiletableService.selectById(mying);
    			 inform.setInformImageurl(sysfile.getFileUrl());
    			 
    		 }
        	}*/
		}
        
        return new PageUtils(page);
    }

	@Override
	public List<InsInformEntity> queryInformList() {
		return baseMapper.queryInformList();
	}

	@Override
	public Integer getMaxOrderNum(String columnId) {
		return baseMapper.getMaxOrderNum(columnId);
	}
    
}

package io.youcham.modules.activity.service.impl;

import io.youcham.common.constant.Variable;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.activity.dao.ActUserStepDao;
import io.youcham.modules.activity.entity.ActUserStepEntity;
import io.youcham.modules.activity.service.ActUserStepService;
import io.youcham.modules.activity.service.ActivityService;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysDeptService;
import io.youcham.modules.sys.service.SysUserService;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("actUserStepService")
public class ActUserStepServiceImpl extends ServiceImpl<ActUserStepDao, ActUserStepEntity> implements ActUserStepService {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDeptService sysdept;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private RepositoryService repositoryService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
    	
    	//按权限查询
		// 获取当前用户的部门信息

		// 获取登录用户
		SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());

		String deptid = "";
		if (user.getUserId().equals("1")) {
		    deptid = "";
		}else {
//			deptid = sysdept.getDeptIdstuString(user.getDeptId());
		}
		//params.put("deptid", deptid);
    	
    	
    	String zdr = (String) params.get("zdr");
    	String lcdy = (String) params.get("lcdy");
        Page<ActUserStepEntity> page = this.selectPage(
                new Query<ActUserStepEntity>(params).getPage(),
                new EntityWrapper<ActUserStepEntity>()
                .like(StringUtils.isNoneEmpty(zdr), "RESERVE_ONE", zdr)
                .eq(StringUtils.isNoneEmpty(lcdy), "LC_KEY", lcdy)
                .in(StringUtils.isNoneEmpty(deptid), "COM_AFF", deptid)
                .orderBy("com_aff,lcKey,RESERVE_TWO")
                
        );
    	// 循环赋值部门名称
		for (ActUserStepEntity awardedMarksEntity : page.getRecords()) {
			String ids2 = awardedMarksEntity.getComAff();
        	if(ids2!=null&&ids2!=""){
        		SysDeptEntity sd = sysdept.selectById(ids2);
        		if(sd!=null){
        			awardedMarksEntity.setComAffname(sd.getName());
        		}
            	
        	}
        	//begin 流程定义key改为名字
        	Map<String, Object> nmap = new HashMap<>();
        	nmap.put("processDefinitionId", awardedMarksEntity.getLcKey());
        	ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
    		if(StringUtils.isNotEmpty(awardedMarksEntity.getLcKey())){
           	 query.processDefinitionKey(awardedMarksEntity.getLcKey());
           }
    		query.latestVersion();
    		List<ProcessDefinition> processDefinitionList = query.list();
    		if(processDefinitionList.size()>0){
    			ProcessDefinition pd = processDefinitionList.get(0);
    			awardedMarksEntity.setLcKeyName(pd.getName());
    		}else{
    			awardedMarksEntity.setLcKeyName("");
    		}
    				
    		
        	/*List<ProcessDefinition> processDefinitionList = query
    				.listPage((page.getCurrent()-1)*page.getLimit(), page.getCurrent()*page.getLimit()-1);
        	*/
        	//end
			
			String ids =  awardedMarksEntity.getAppoint();
			
			if(StringUtils.isNotEmpty(ids)){
				String[] arr = ids.split(","); // 用,分割
				
				List columnMap = new ArrayList<>();
				if(ids != null && ids != ""){
					for (int i = 0; i < arr.length; i++) {
						columnMap.add(arr[i]);	
					}
					
				}
				
				List<SysUserEntity> sysuser = sysUserService.selectBatchIds(columnMap);
				String gname = "";
				if(sysuser.size()>0){
					for (int i = 0; i < sysuser.size(); i++) {
						gname = gname +","+ sysuser.get(i).getLegalPerson();
					}
					
				}
				
				if (sysuser.size()>0) {
					awardedMarksEntity.setAppointname(gname.substring(1, gname.length()));
				} else {
					awardedMarksEntity.setAppointname(null);
				}
			}

		}
        

        return new PageUtils(page);
    }

	@Override
	public Map<String, Object> getActUserByDefineKey(String defineKey) {
		//add by xcg
		// 获取当前用户的部门信息
		//SysDeptEntity dept = (SysDeptEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue());

		// 获取登录用户
//		SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());
		// 获取当前用户的部门信息
		//SysDeptEntity dept = (SysDeptEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue());

//		String deptid = null;
//		deptid = sysdept.getDeptIdstuString(user.getDeptId());

		List<ActUserStepEntity> actUsers = this.selectList(new EntityWrapper<ActUserStepEntity>()
				//add xcg
//				.in("COM_AFF", deptid)
				.eq("lc_key",defineKey).orderBy("create_time desc"));
		
		Map<String, Object> mapObj = new HashMap<String, Object>();
		for (ActUserStepEntity actUserStep : actUsers) {
			mapObj.put(actUserStep.getReserveOne(), actUserStep.getAppoint());
		}
		
		return mapObj;
	}

}

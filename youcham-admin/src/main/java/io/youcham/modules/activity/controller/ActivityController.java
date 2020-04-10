package io.youcham.modules.activity.controller;

import io.youcham.common.utils.R;
import io.youcham.modules.activity.service.ActUserStepService;
import io.youcham.modules.activity.service.ActivityService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.json.JSONObject;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.explorer.util.XmlUtil;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/** 
 * @description: 
 * @author : guoqiang
 * @date : 2018年12月17日 下午3:55:54 
 * @version : v1.0
 * @since :
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired  
    private RuntimeService runtimeService;
    @Autowired  
    private TaskService taskService;  
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
	private ActUserStepService actUserStepService;
    
    /** 
     * @Description:  根据流程定义启动流程实例----操作的数据表：act_ru_execution act_ru_task
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:42:58 
     */
    @RequestMapping("/startActivityDemo")
    public R startActivityDemo(String key) {  
    	// 获取流程用户
    	Map<String,Object> map = actUserStepService.getActUserByDefineKey(key);
    	
    	activityService.startActivityBykey(key,map);
    	return R.ok();
    }  
    
    /** 
     * @Description: 查询流程定义 
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:43:33 
     */
    @RequestMapping("/getProcessDefinitions")
    public R queryProcessDefinitions(@RequestParam Map<String,Object> params){
    	return R.ok().put("page",activityService.getProcessDefinitions(params));
    	
    }
    
    /** 
     * @Description: 查询流程实例 
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:43:33 
     */
    @RequestMapping("/getProcessInstances")
    public R getRunningProcessInstaces(@RequestParam Map<String,Object> params){
    	return R.ok().put("page",activityService.getProcessInstances(params));
    	
    }
    
    /** 
     * @Description: 查询任务列表
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:44:27 
     * @param params assignee：对象
     * @return
     */
    @RequestMapping("/getTasks")
    public R getTasks(@RequestParam Map<String,Object> params){
    	return R.ok().put("page",activityService.getTasks(params));
    }
    
    /** 
     * @Description:  //办理任务 //办理个人任务，操作的表是act_ru_execution,act_ru_task
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:44:56 
     * @param params
     */
//    @RequestMapping("/completeTask")
    public R completeTask(@RequestParam Map<String,Object> params){
    	activityService.completeTask(params);
    	return R.ok();
	}
    
    /**
     * 读取资源，通过流程ID
     *
     * @param resourceType      资源类型(xml|image)
     * @param  流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-definition")
    public void loadByProcessDefinition(@RequestParam("type") String resourceType, @RequestParam("pid") String processDefinitionId, HttpServletResponse response)
            throws Exception {
        InputStream resourceAsStream = null;
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
                .singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
      
        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    
    /**
     * 读取资源，通过流程ID
     *
     * @param resourceType      资源类型(xml|image)
     * @param processInstanceId 流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-instance")
    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
            throws Exception {
        InputStream resourceAsStream = null;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
            BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processInstance.getProcessDefinitionId());
            List<String> activeIds = runtimeService.getActiveActivityIds(processInstance.getId());
            ProcessDiagramGenerator p =  processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
            resourceAsStream = p.generateDiagram(bpmnModel, "png", activeIds, Collections.<String>emptyList(), "宋体", "宋体", "宋体", null, 1.0);
           
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
            resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        }
      
        
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    
    /** 
     * @Description: 模型列表
     * @author : xcg
     * @date : 2018年12月18日 上午9:43:33 
     */
    @RequestMapping("/getModel")
    public R getModel(@RequestParam Map<String,Object> params){
    	return R.ok().put("page",activityService.getModels(params));
    	
    }      
    
    /**
     * 新建一个空模型
     */
    /*@RequestMapping("/create")
    public void newModel(HttpServletResponse response) throws IOException {
       
    }*/
    @RequestMapping("/save")
	//@RequiresPermissions("hd:hdairexport:save")
	public R save(@RequestBody JSONObject obj) {
    	
    	 RepositoryService repositoryService = processEngine.getRepositoryService();
         //初始化一个空模型
         Model model = repositoryService.newModel();
  
         //设置一些默认信息
         String name = obj.getString("name");
         String description = obj.getString("category");
         int revision = 1;
         String key = obj.getString("key");
  
         ObjectNode modelNode = objectMapper.createObjectNode();
         modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
         modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
         modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
  
         model.setName(name);
         model.setKey(key);
         model.setMetaInfo(modelNode.toString());
  
         repositoryService.saveModel(model);
         String id = model.getId();
  
         //完善ModelEditorSource
         ObjectNode editorNode = objectMapper.createObjectNode();
         editorNode.put("id", "canvas");
         editorNode.put("resourceId", "canvas");
         ObjectNode stencilSetNode = objectMapper.createObjectNode();
         stencilSetNode.put("namespace",
                 "http://b3mn.org/stencilset/bpmn2.0#");
         editorNode.put("stencilset", stencilSetNode);
         try {
			repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // response.sendRedirect("/modeler.html?modelId="+id);
    	

		return R.ok();
	}
    
    /** 
     * @Description: 删除模型
     * @author : xcg
     * @date : 2018年12月18日 上午9:43:33 
     */
    @RequestMapping("/delModel")
    public R delModel(@RequestBody String id){
    	 RepositoryService repositoryService = processEngine.getRepositoryService();
         repositoryService.deleteModel(id);
    	
    	return R.ok();
    	
    }  
    
    /** 
     * @Description: 审核记录
     * @author : xcg
     * @date : 2018年12月18日 上午9:43:33 
     */
    @RequestMapping("/getHisTaskInstanceList")
    public R getHisTaskInstanceList(@RequestParam Map<String,Object> params){
    	
    	return R.ok().put("page",activityService.getHisTaskInstanceList(params));
    	
    }  
    
    /**
     * 删除申请
     */
//    @RequestMapping("/deleteInstance")
    @RequiresPermissions("hd:hdaccountlist:delete")
    public R deleteInstance(String id, String taskId) {
    	runtimeService.deleteProcessInstance(id, "删除实例");
        return R.ok();
    }
    
    	/**
         * 流程转化为可编辑模型

         *

         * @param processDefineModel

         */
    @RequestMapping("/rmodle")
    public String changeProcessToModel(String modelId) {

    	try {
    		Model modelData = repositoryService.newModel();
    	       //Model modelData = repositoryService.getModel(modelId);
    	       
    	       ProcessDefinition processDefineModel = repositoryService.getProcessDefinition(modelId);
    	       
    	        // 初始化Model

    	       ObjectNode modelObjectNode = objectMapper.createObjectNode();

    	      modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefineModel.getName());

    	     modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);

    	     modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefineModel.getDescription());

    	     modelData.setMetaInfo(modelObjectNode.toString());

    	      modelData.setName(processDefineModel.getName());

    	      modelData.setKey(processDefineModel.getKey());

    	    //        // 保存模型

    	      repositoryService.saveModel(modelData);


    	     String deploymentId = processDefineModel.getDeploymentId();

    	     String processDefineResourceName = null;

    	    //        // 通过deploymentId取得某个部署的资源的名称

    	    /* */
            if(processDefineModel.getResourceName()!=null&&processDefineModel.getResourceName()!=""){
            	processDefineResourceName = processDefineModel.getResourceName();
            }else{
            	List<String> resourceNames = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
	      	    if (resourceNames != null && resourceNames.size() > 0) {
	      	    for (String temp : resourceNames) {
	      	         if (temp.indexOf(".bpmn") > 0) {
	      	          processDefineResourceName = temp;
	      			   }
	      	         if (temp.indexOf(".xml") > 0) {
	      	             processDefineResourceName = temp;
	      	   		   }
	      			}
	
	      	    }
            }
    	      
    	      		/* 
    	             * 读取资源

    	             * deploymentId:部署的id

    	             * resourceName：资源的文件名

    	             */

    	      
    	       InputStream bpmnStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, processDefineResourceName);

    	    	 this.creatModelByImpoutStream(bpmnStream, modelData.getId());
    	           
    	     
    	          return "true";

		} catch (Exception e) {
			// TODO: handle exception
			return "false";
		}
       
  }

    
    /**

         * 创建Model二进制信息

         * @param bpmnStream

         * @param ModelID

         */

     public void creatModelByImpoutStream(InputStream bpmnStream, String ModelID) {

			XMLInputFactory xif;
			
			 InputStreamReader in = null;
			
			 XMLStreamReader xtr = null;
			
			try {
			
			 xif = XmlUtil.createSafeXmlInputFactory();
			
			 in = new InputStreamReader(bpmnStream, "UTF-8");
			
			 xtr = xif.createXMLStreamReader(in);
			
			 BpmnModel bpmnModel = (new BpmnXMLConverter()).convertToBpmnModel(xtr);
			
			
			 ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);
			
			 repositoryService.addModelEditorSource(ModelID, modelNode.toString().getBytes("utf-8"));}
			
			catch (XMLStreamException e) {
	
			e.printStackTrace();
	
	    	} catch (UnsupportedEncodingException e2) {
	
	    		e2.printStackTrace();
	
	    	} finally {
	
		 if (xtr != null) {
		
		    	try {
		
		    	xtr.close();
		
		    	} catch (XMLStreamException e1) {
		
		    	 e1.printStackTrace();
		
		    	}
		
		 }
		
		 if (in != null) {
		
		try {
		
		    	in.close();
		
		    	} catch (IOException e1) {
		
		    	e1.printStackTrace();
		    	}
		
		    	}
		
		
		 if (bpmnStream != null) {
		
		try {
		
		    	bpmnStream.close();
		} catch (IOException e3) {
			e3.printStackTrace();}}

		}
		
		
		}
     
    
}

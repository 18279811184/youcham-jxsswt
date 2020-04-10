package io.youcham.modules.activity.service;

import io.youcham.common.utils.DateUtils;
import io.youcham.common.utils.LogUtil;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.common.utils.R;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysUserService;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;


@Service
public class ActivityService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private IdentityService identityService;

    /**
     * @Description: 根据流程定义启动流程实例----操作的数据表：act_ru_execution act_ru_task
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:42:58
     */
    public ProcessInstance startActivityBykey(String key, Map<String, Object> map) {
        //流程启动  
        identityService.setAuthenticatedUserId(ShiroUtils.getUserId());
        ProcessInstance processInstance = (ProcessInstance) runtimeService.startProcessInstanceByKey(key, map);
        LogUtil.info("启动流程{0}", new Object[]{key});

        return processInstance;
    }

    /**
     * @Description: 根据流程定义启动流程实例----操作的数据表：act_ru_execution act_ru_task
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:42:58
     */
    public ProcessInstance startActivityBykey(String key, String businessKey, Map<String, Object> map) {
        //流程启动  
        identityService.setAuthenticatedUserId(ShiroUtils.getUserId());
        ProcessInstance processInstance = (ProcessInstance) runtimeService.startProcessInstanceByKey(key, businessKey, map);
        LogUtil.info("启动流程{0},businessKey:{1}", new Object[]{key, businessKey});

        return processInstance;
    }

    /**
     * @Description: 查询流程定义
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:43:33
     */
    public PageUtils getProcessDefinitions(Map<String, Object> params) {

        String processDefinitionId = (String) params.get("processDefinitionId");
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        // 流程定义查询对象，用于查询流程定义表----act_re_procdef
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        if (StringUtils.isNotEmpty(processDefinitionId)) {
            query.processDefinitionKey(processDefinitionId);
        }
        // 添加过滤条件,取最新版本
        query.latestVersion();
        // query.processDefinitionId(processDefinitionId)
        // query.processDefinitionKey(processDefinitionKey);
        // 添加排序条件
        query.orderByProcessDefinitionVersion().desc();

        // 查询所有流程
        List<ProcessDefinition> processDefinitionList = query
                .listPage((page.getCurrent() - 1) * page.getLimit(), page.getCurrent() * page.getLimit() - 1);

        List<Map<String, Object>> customList = new ArrayList<>();
        for (ProcessDefinition process : processDefinitionList) {
            Map<String, Object> map = new HashMap<>();
            map.put("processDefinitionId", process.getId());
            map.put("key", process.getKey());
            map.put("name", process.getName());
            map.put("description", process.getDescription());
            map.put("version", process.getVersion());
            map.put("deploymentId", process.getDeploymentId());
            map.put("resourceName", process.getResourceName());
            map.put("diagramResourceName", process.getDiagramResourceName());

            customList.add(map);
        }

        page.setRecords(customList);
        Long conut = query.count();
        page.setTotal(conut.intValue());

        return new PageUtils(page);
    }

    /**
     * 获取流程实例
     *
     * @return
     */
    public PageUtils getProcessInstances(Map<String, Object> params) {
        //
        String processDefinitionId = (String) params.get("processDefinitionId");
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();

        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
//        query;
        if (StringUtils.isNotEmpty(processDefinitionId)) {
            query.processDefinitionId(processDefinitionId);
        }

        query.orderByProcessDefinitionId().desc();
        List<ProcessInstance> list = query.listPage((page.getCurrent() - 1) * page.getLimit(), page.getCurrent() * page.getLimit() - 1);

        List<Map<String, Object>> customList = new ArrayList<>();
        for (ProcessInstance processInstance : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("processInstanceId", processInstance.getId());
            map.put("name", processInstance.getName());
            map.put("description", processInstance.getDescription());
            map.put("definitionKey", processInstance.getProcessDefinitionKey());
            map.put("definitionId", processInstance.getProcessDefinitionId());
            map.put("isSuspended", processInstance.isSuspended());
            map.put("startTime", DateUtils.formatTime(processInstance.getStartTime()));
            customList.add(map);
        }

        page.setRecords(customList);
        Long conut = query.count();
        page.setTotal(conut.intValue());

        return new PageUtils(page);
    }


    /**
     * @param params assignee：对象
     * @return
     * @Description: 查询任务列表
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:44:27
     */
    public PageUtils getTasks(Map<String, Object> params) {
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();

        TaskQuery query = taskService.createTaskQuery();
        //添加过滤条件
        String assignee = (String) params.get("assignee");
        if (StringUtils.isNotEmpty(assignee)) {
            query.taskAssignee(assignee);
        }
        String processInstanceId = (String) params.get("processInstanceId");
        if (StringUtils.isNotEmpty(processInstanceId)) {
            query.processInstanceId(processInstanceId);
        }
        //排序
        query.orderByTaskCreateTime().desc();

        List<Task> taskList = query.listPage((page.getCurrent() - 1) * page.getLimit(), page.getCurrent() * page.getLimit());

        List<Map<String, Object>> customList = new ArrayList<>();
        for (Task task : taskList) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            map.put("createTime", DateUtils.formatTime(task.getCreateTime()));
            map.put("definitionKey", task.getTaskDefinitionKey());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("processInstanceId", task.getProcessInstanceId());

            customList.add(map);
        }

        page.setRecords(customList);
        Long conut = query.count();
        page.setTotal(conut.intValue());

        return new PageUtils(page);
    }

    /**
     * @param params assignee：对象
     * @return
     * @Description: 查询任务列表
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:44:27
     */
    public List<Map<String, Object>> getTaskList(Map<String, Object> params) {

        TaskQuery query = taskService.createTaskQuery();
        //添加过滤条件
        String assignee = (String) params.get("assignee");
        if (StringUtils.isNotEmpty(assignee)) {
            query.taskAssignee(assignee);
        }
        String processInstanceId = (String) params.get("processInstanceId");
        if (StringUtils.isNotEmpty(processInstanceId)) {
            query.processInstanceId(processInstanceId);
        }
        //排序
        query.orderByTaskCreateTime().desc();

        List<Task> taskList = query.list();

        List<Map<String, Object>> customList = new ArrayList<>();
        for (Task task : taskList) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("name", task.getName());
            map.put("assignee", task.getAssignee());
            map.put("description", task.getDescription());
            map.put("createTime", DateUtils.formatTime(task.getCreateTime()));
            map.put("definitionKey", task.getTaskDefinitionKey());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("processInstanceId", task.getProcessInstanceId());

            customList.add(map);
        }

        return customList;
    }

    /**
     * @param params
     * @Description: //办理任务 //办理个人任务，操作的表是act_ru_execution,act_ru_task
     * @author : guoqiang
     * @date : 2018年12月18日 上午9:44:56
     */
    public void completeTask(Map<String, Object> params) {
        String taskId = (String) params.get("taskId");
//    	String message = (String)params.get("message");

        //完成任务的同时，设置流程变量，让流程变量判断连线该如何执行
//		Map<String, Object> variables = new HashMap<String, Object>();
        //其中message对应sequenceFlow.bpmn中的${message=='不重要'}，不重要对应流程变量的值
//		variables.put("message", "不重要");
//    	params.put("auditResult", false);
        taskService.setVariable(taskId, "auditResult", true);//true批准，false不批准
        taskService.complete(taskId);

        System.out.println("完成任务：" + taskId);
    }

    /**
     * @param params assignee：对象
     * @return
     * @Description: 查询模型列表
     * @author : xcg
     * @date : 2018年12月18日 上午9:44:27
     */
    public PageUtils getModels(Map<String, Object> params) {
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();

        TaskQuery query = taskService.createTaskQuery();
        //添加过滤条件
        String assignee = (String) params.get("assignee");
        if (StringUtils.isNotEmpty(assignee)) {
            query.taskAssignee(assignee);
        }
        //排序
        query.orderByTaskCreateTime().desc();

        //List<Task> taskList = query.listPage((page.getCurrent()-1)*page.getLimit(), page.getCurrent()*page.getLimit());
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Model> models = repositoryService.createModelQuery().orderByCreateTime().desc()
                .listPage((page.getCurrent() - 1) * page.getLimit(), page.getCurrent() * page.getLimit());

        List<Map<String, Object>> customList = new ArrayList<>();
        for (Model task : models) {
            Map<String, Object> map = new HashMap<>();
            map.put("Id", task.getId());
            map.put("name", task.getName());
            map.put("key", task.getKey());
            map.put("Category", task.getCategory());
            map.put("CreateTime", task.getCreateTime());
            map.put("metaInfo", task.getMetaInfo());
            customList.add(map);
        }

        page.setRecords(customList);
        Long conut = query.count();
        page.setTotal(conut.intValue());

        return new PageUtils(page);
    }

    /**
     * @param params
     * @return
     * @Description: 获取审核记录
     * @author : guoqiang
     * @date : 2018年12月20日 下午4:04:53
     */
    @SuppressWarnings("unchecked")
    public PageUtils getHisTaskInstanceList(Map<String, Object> params) {
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();

        String declareId = (String) params.get("declareId");
        String createId = (String) params.get("createId");
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
//    	query.processFinished();

        // 注入条件
        String hisInstanceId = (String) params.get("hisInstanceId");
        if (StringUtils.isNotEmpty(hisInstanceId)) {
            query.processInstanceId(hisInstanceId);
        }
        String assignee = (String) params.get("assignee");
        if (StringUtils.isNotEmpty(assignee)) {
            query.taskAssignee(assignee);
        }
        String name = (String) params.get("name");
        if (StringUtils.isNotEmpty(name)) {
            query.taskName(name);
        }

        List<String> auditTaskNameList = (List<String>) params.get("auditTaskNameList");
        if (null != auditTaskNameList) {
            query.taskNameIn(auditTaskNameList);
        }

        query.orderByHistoricTaskInstanceEndTime().desc();

        List<HistoricTaskInstance> hisTaskInstanceList = query
                .listPage((page.getCurrent() - 1) * page.getLimit(), page.getCurrent() * page.getLimit());
        List<Map<String, Object>> customList = new ArrayList<>();

        for (HistoricTaskInstance taskInstance : hisTaskInstanceList) {
            Map<String, Object> map = new HashMap<>();
            boolean flag = false;
            //boolean flag = comReturnStateService.isState(taskInstance.getId(),declareId);
            if (!flag) {
                map.put("isState", "exits");
            }
            boolean isMyAudit = false;
            if (null != ShiroUtils.getUserEntity() && ShiroUtils.getUserId().equals(taskInstance.getAssignee())) {
                isMyAudit = true;
            }
            /* 可以退回申述的人 */
            if (ShiroUtils.getUserId().equals(createId)) {
                map.put("isCanStated", "exist");
            }
            map.put("id", taskInstance.getId());
            map.put("name", taskInstance.getName());
            map.put("key", taskInstance.getTaskDefinitionKey());
            map.put("assignee", taskInstance.getAssignee());

            // 审核人
            SysUserEntity user = sysUserService.selectById(taskInstance.getAssignee());
            if (null != user) {
                map.put("assigneeName", user.getLegalPerson());
            } else {
                map.put("assigneeName", "");
            }

            map.put("startTime", DateUtils.formatTime(taskInstance.getStartTime()));
            map.put("endTime", DateUtils.formatTime(taskInstance.getEndTime()));
            map.put("isMyAudit", isMyAudit);

            // 备注信息
            List<Comment> comments = taskService.getTaskComments(taskInstance.getId());
            if (comments.size() > 0 && comments.get(0).getFullMessage() != null) {
                String comment = comments.get(0).getFullMessage();
                map.put("comment", comment);
                if (comment.startsWith("[通过]")) {
                    map.put("status", 1);
                } else if (comment.startsWith("[退回]")) {
                    map.put("status", 2);
                } else if (comment.startsWith("[撤销]")) {
                    map.put("status", 3);
                } else {
                    map.put("status", 0);
                }
            } else {
                map.put("comment", "");
            }

            customList.add(map);
        }

        page.setRecords(customList);
        Long conut = query.count();
        page.setTotal(conut.intValue());

        return new PageUtils(page);
    }

    /**
     * 删除申请
     */
    public R deleteInstance(String id) {

        return R.ok();
    }

}  

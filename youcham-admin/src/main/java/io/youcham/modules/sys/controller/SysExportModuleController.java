package io.youcham.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.youcham.common.validator.ValidatorUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.youcham.modules.sys.entity.SysExportModuleEntity;
import io.youcham.modules.sys.service.SysExportModuleService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 导出功能
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-10-11 09:49:51
 */
@RestController
@RequestMapping("sys/sysexportmodule")
public class SysExportModuleController {
    @Autowired
    private SysExportModuleService sysExportModuleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysexportmodule:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysExportModuleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysexportmodule:info")
    public R info(@PathVariable("id") String id){
        SysExportModuleEntity sysExportModule = sysExportModuleService.selectById(id);

        return R.ok().put("sysExportModule", sysExportModule);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysexportmodule:save")
    public R save(@RequestBody SysExportModuleEntity sysExportModule){
    	//sysExportModule.setId(UUID.randomUUID().toString());
        sysExportModuleService.insert(sysExportModule);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysexportmodule:update")
    public R update(@RequestBody SysExportModuleEntity sysExportModule){
        ValidatorUtils.validateEntity(sysExportModule);
        sysExportModuleService.updateAllColumnById(sysExportModule);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysexportmodule:delete")
    public R delete(@RequestBody String[] ids){
        sysExportModuleService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /** 
     * @Description: 获取下拉框
     * @author : guoqiang
     * @date : 2018年10月11日 下午2:50:26 
     * @return
     */
    @RequestMapping("/select")
    public List<SysExportModuleEntity> select(){
    	List<SysExportModuleEntity> modules = sysExportModuleService.selectList(null);
    	
    	return modules;
    }
    
}

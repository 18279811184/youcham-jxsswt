package io.youcham.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.youcham.modules.sys.entity.SysExportParameterEntity;
import io.youcham.modules.sys.service.SysExportParameterService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 导出参数
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-10-11 11:56:57
 */
@RestController
@RequestMapping("sys/sysexportparameter")
public class SysExportParameterController {
    @Autowired
    private SysExportParameterService sysExportParameterService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysexportparameter:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysExportParameterService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysexportparameter:info")
    public R info(@PathVariable("id") String id){
        SysExportParameterEntity sysExportParameter = sysExportParameterService.selectById(id);

        return R.ok().put("sysExportParameter", sysExportParameter);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysexportparameter:save")
    public R save(@RequestBody SysExportParameterEntity sysExportParameter){
    	ValidatorUtils.validateEntity(sysExportParameter,AddGroup.class);
    	//sysExportParameter.setId(UUID.randomUUID().toString());
        sysExportParameterService.insert(sysExportParameter);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysexportparameter:update")
    public R update(@RequestBody SysExportParameterEntity sysExportParameter){
    	ValidatorUtils.validateEntity(sysExportParameter,AddGroup.class);
    	
        sysExportParameterService.updateAllColumnById(sysExportParameter);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysexportparameter:delete")
    public R delete(@RequestBody String[] ids){
        sysExportParameterService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

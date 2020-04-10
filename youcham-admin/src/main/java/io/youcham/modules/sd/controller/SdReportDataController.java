package io.youcham.modules.sd.controller;

import io.youcham.modules.sd.entity.SdReportDataEntity;
import io.youcham.modules.sd.service.SdReportDataService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.ValidatorUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 申报/提供信息
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
@RestController
@RequestMapping("sd/sdreportdata")
public class SdReportDataController {
    @Autowired
    private SdReportDataService sdReportDataService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sd:sdreportdata:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sdReportDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sd:sdreportdata:info")
    public R info(@PathVariable("id") String id){
        SdReportDataEntity sdReportData = sdReportDataService.selectById(id);

        return R.ok().put("sdReportData", sdReportData);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sd:sdreportdata:save")
    public R save(@RequestBody SdReportDataEntity sdReportData){
    	ValidatorUtils.validateEntity(sdReportData,AddGroup.class);
    
   	 	sdReportData.setCreateId(ShiroUtils.getUserId());
    	sdReportData.setCreateTime(new Date());
        sdReportData.setCreateName(ShiroUtils.getUserEntity().getName());
        sdReportDataService.insert(sdReportData);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sd:sdreportdata:update")
    public R update(@RequestBody SdReportDataEntity sdReportData){
        ValidatorUtils.validateEntity(sdReportData,AddGroup.class);
       
        sdReportData.setUpdateId(ShiroUtils.getUserId());
    	sdReportData.setUpdateTime(new Date());
        sdReportData.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = sdReportDataService.updateAllColumnById(sdReportData);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sd:sdreportdata:delete")
    public R delete(@RequestBody String[] ids){
        sdReportDataService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

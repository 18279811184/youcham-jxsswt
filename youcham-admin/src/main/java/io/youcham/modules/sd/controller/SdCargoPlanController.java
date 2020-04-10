package io.youcham.modules.sd.controller;

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

import io.youcham.modules.sd.entity.SdCargoPlanEntity;
import io.youcham.modules.sd.service.SdCargoPlanService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-01 14:52:06
 */
@RestController
@RequestMapping("sd/sdcargoplan")
public class SdCargoPlanController {
    @Autowired
    private SdCargoPlanService sdCargoPlanService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sd:sdcargoplan:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sdCargoPlanService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sd:sdcargoplan:info")
    public R info(@PathVariable("id") String id){
        SdCargoPlanEntity sdCargoPlan = sdCargoPlanService.selectById(id);

        return R.ok().put("sdCargoPlan", sdCargoPlan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sd:sdcargoplan:save")
    public R save(@RequestBody SdCargoPlanEntity sdCargoPlan){
    	ValidatorUtils.validateEntity(sdCargoPlan,AddGroup.class);
    
   	 	sdCargoPlan.setCreateId(ShiroUtils.getUserId());
    	sdCargoPlan.setCreateTime(new Date());
        sdCargoPlan.setCreateName(ShiroUtils.getUserEntity().getName());
        sdCargoPlan.setBeDelete(0);
        sdCargoPlanService.insert(sdCargoPlan);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sd:sdcargoplan:update")
    public R update(@RequestBody SdCargoPlanEntity sdCargoPlan){
        ValidatorUtils.validateEntity(sdCargoPlan,AddGroup.class);
       
        sdCargoPlan.setUpdateId(ShiroUtils.getUserId());
    	sdCargoPlan.setUpdateTime(new Date());
        sdCargoPlan.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = sdCargoPlanService.updateAllColumnById(sdCargoPlan);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sd:sdcargoplan:delete")
    public R delete(@RequestBody String[] ids){
        sdCargoPlanService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

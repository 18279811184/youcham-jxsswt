package io.youcham.modules.activity.controller;

import io.youcham.modules.activity.entity.ActUserStepEntity;
import io.youcham.modules.activity.service.ActUserStepService;
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
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-12-20 09:31:25
 */
@RestController
@RequestMapping("sys/actuserstep")
public class ActUserStepController {
    @Autowired
    private ActUserStepService actUserStepService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:actuserstep:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = actUserStepService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:actuserstep:info")
    public R info(@PathVariable("id") String id){
        ActUserStepEntity actUserStep = actUserStepService.selectById(id);

        return R.ok().put("actUserStep", actUserStep);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:actuserstep:save")
    public R save(@RequestBody ActUserStepEntity actUserStep){
    	ValidatorUtils.validateEntity(actUserStep,AddGroup.class);
    
   	 	actUserStep.setCreateId(ShiroUtils.getUserId());
    	actUserStep.setCreateTime(new Date());
        actUserStepService.insert(actUserStep);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:actuserstep:update")
    public R update(@RequestBody ActUserStepEntity actUserStep){
        ValidatorUtils.validateEntity(actUserStep,AddGroup.class);
       
        actUserStep.setUpdateId(ShiroUtils.getUserId());
    	actUserStep.setUpdateTime(new Date());
        Boolean re = actUserStepService.updateAllColumnById(actUserStep);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:actuserstep:delete")
    public R delete(@RequestBody String[] ids){
        actUserStepService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

package io.youcham.modules.student.controller;

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

import io.youcham.modules.student.entity.BigjavaClassEntity;
import io.youcham.modules.student.service.BigjavaClassService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-09 11:34:41
 */
@RestController
@RequestMapping("student/bigjavaclass")
public class BigjavaClassController {
    @Autowired
    private BigjavaClassService bigjavaClassService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("student:bigjavaclass:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bigjavaClassService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("student:bigjavaclass:info")
    public R info(@PathVariable("id") Integer id){
        BigjavaClassEntity bigjavaClass = bigjavaClassService.selectById(id);

        return R.ok().put("bigjavaClass", bigjavaClass);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("student:bigjavaclass:save")
    public R save(@RequestBody BigjavaClassEntity bigjavaClass){
    	ValidatorUtils.validateEntity(bigjavaClass,AddGroup.class);
    

        bigjavaClassService.insert(bigjavaClass);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("student:bigjavaclass:update")
    public R update(@RequestBody BigjavaClassEntity bigjavaClass){
        ValidatorUtils.validateEntity(bigjavaClass,AddGroup.class);
       

        Boolean re = bigjavaClassService.updateAllColumnById(bigjavaClass);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("student:bigjavaclass:delete")
    public R delete(@RequestBody Integer[] ids){
        bigjavaClassService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

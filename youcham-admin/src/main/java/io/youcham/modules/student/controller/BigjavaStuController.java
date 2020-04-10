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

import io.youcham.modules.student.entity.BigjavaStuEntity;
import io.youcham.modules.student.service.BigjavaStuService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-09 12:15:31
 */
@RestController
@RequestMapping("student/bigjavastu")
public class BigjavaStuController {
    @Autowired
    private BigjavaStuService bigjavaStuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("student:bigjavastu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bigjavaStuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("student:bigjavastu:info")
    public R info(@PathVariable("id") String id){
        BigjavaStuEntity bigjavaStu = bigjavaStuService.selectById(id);

        return R.ok().put("bigjavaStu", bigjavaStu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("student:bigjavastu:save")
    public R save(@RequestBody BigjavaStuEntity bigjavaStu){
    	ValidatorUtils.validateEntity(bigjavaStu,AddGroup.class);
    

        bigjavaStuService.insert(bigjavaStu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("student:bigjavastu:update")
    public R update(@RequestBody BigjavaStuEntity bigjavaStu){
        ValidatorUtils.validateEntity(bigjavaStu,AddGroup.class);
       

        Boolean re = bigjavaStuService.updateAllColumnById(bigjavaStu);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("student:bigjavastu:delete")
    public R delete(@RequestBody String[] ids){
        bigjavaStuService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

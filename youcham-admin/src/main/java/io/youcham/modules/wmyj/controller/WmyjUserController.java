package io.youcham.modules.wmyj.controller;

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

import io.youcham.modules.wmyj.entity.WmyjUserEntity;
import io.youcham.modules.wmyj.service.WmyjUserService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-19 15:45:33
 */
@RestController
@RequestMapping("wmyj/wmyjuser")
public class WmyjUserController {
    @Autowired
    private WmyjUserService wmyjUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wmyj:wmyjuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmyjUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wmyj:wmyjuser:info")
    public R info(@PathVariable("id") String id){
        WmyjUserEntity wmyjUser = wmyjUserService.selectById(id);

        return R.ok().put("wmyjUser", wmyjUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wmyj:wmyjuser:save")
    public R save(@RequestBody WmyjUserEntity wmyjUser){
    	ValidatorUtils.validateEntity(wmyjUser,AddGroup.class);
    

        wmyjUserService.insert(wmyjUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wmyj:wmyjuser:update")
    public R update(@RequestBody WmyjUserEntity wmyjUser){
        ValidatorUtils.validateEntity(wmyjUser,AddGroup.class);
       

        Boolean re = wmyjUserService.updateAllColumnById(wmyjUser);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wmyj:wmyjuser:delete")
    public R delete(@RequestBody String[] ids){
        wmyjUserService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

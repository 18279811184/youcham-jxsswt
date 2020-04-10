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

import io.youcham.modules.wmyj.entity.WmyjTblInfoamountREntity;
import io.youcham.modules.wmyj.service.WmyjTblInfoamountRService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 出口信息金额汇总表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:30
 */
@RestController
@RequestMapping("wmyj/wmyjtblinfoamountr")
public class WmyjTblInfoamountRController {
    @Autowired
    private WmyjTblInfoamountRService wmyjTblInfoamountRService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wmyj:wmyjtblinfoamountr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmyjTblInfoamountRService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wmyj:wmyjtblinfoamountr:info")
    public R info(@PathVariable("id") String id){
        WmyjTblInfoamountREntity wmyjTblInfoamountR = wmyjTblInfoamountRService.selectById(id);

        return R.ok().put("wmyjTblInfoamountR", wmyjTblInfoamountR);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wmyj:wmyjtblinfoamountr:save")
    public R save(@RequestBody WmyjTblInfoamountREntity wmyjTblInfoamountR){
    	ValidatorUtils.validateEntity(wmyjTblInfoamountR,AddGroup.class);
    
   	 	wmyjTblInfoamountR.setCreateId(ShiroUtils.getUserId());
    	wmyjTblInfoamountR.setCreateTime(new Date());
        wmyjTblInfoamountR.setCreateName(ShiroUtils.getUserEntity().getName());
        wmyjTblInfoamountRService.insert(wmyjTblInfoamountR);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wmyj:wmyjtblinfoamountr:update")
    public R update(@RequestBody WmyjTblInfoamountREntity wmyjTblInfoamountR){
        ValidatorUtils.validateEntity(wmyjTblInfoamountR,AddGroup.class);
       
        wmyjTblInfoamountR.setUpdateId(ShiroUtils.getUserId());
    	wmyjTblInfoamountR.setUpdateTime(new Date());
        wmyjTblInfoamountR.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = wmyjTblInfoamountRService.updateAllColumnById(wmyjTblInfoamountR);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wmyj:wmyjtblinfoamountr:delete")
    public R delete(@RequestBody String[] ids){
        wmyjTblInfoamountRService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

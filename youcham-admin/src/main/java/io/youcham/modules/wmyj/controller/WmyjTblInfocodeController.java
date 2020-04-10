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

import io.youcham.modules.wmyj.entity.WmyjTblInfocodeEntity;
import io.youcham.modules.wmyj.service.WmyjTblInfocodeService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 企业代表码
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:28
 */
@RestController
@RequestMapping("wmyj/wmyjtblinfocode")
public class WmyjTblInfocodeController {
    @Autowired
    private WmyjTblInfocodeService wmyjTblInfocodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wmyj:wmyjtblinfocode:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmyjTblInfocodeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wmyj:wmyjtblinfocode:info")
    public R info(@PathVariable("id") String id){
        WmyjTblInfocodeEntity wmyjTblInfocode = wmyjTblInfocodeService.selectById(id);

        return R.ok().put("wmyjTblInfocode", wmyjTblInfocode);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wmyj:wmyjtblinfocode:save")
    public R save(@RequestBody WmyjTblInfocodeEntity wmyjTblInfocode){
    	ValidatorUtils.validateEntity(wmyjTblInfocode,AddGroup.class);
    
   	 	wmyjTblInfocode.setCreateId(ShiroUtils.getUserId());
    	wmyjTblInfocode.setCreateTime(new Date());
        wmyjTblInfocode.setCreateName(ShiroUtils.getUserEntity().getName());
        wmyjTblInfocodeService.insert(wmyjTblInfocode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wmyj:wmyjtblinfocode:update")
    public R update(@RequestBody WmyjTblInfocodeEntity wmyjTblInfocode){
        ValidatorUtils.validateEntity(wmyjTblInfocode,AddGroup.class);
       
        wmyjTblInfocode.setUpdateId(ShiroUtils.getUserId());
    	wmyjTblInfocode.setUpdateTime(new Date());
        wmyjTblInfocode.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = wmyjTblInfocodeService.updateAllColumnById(wmyjTblInfocode);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wmyj:wmyjtblinfocode:delete")
    public R delete(@RequestBody String[] ids){
        wmyjTblInfocodeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

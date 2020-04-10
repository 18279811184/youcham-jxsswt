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

import io.youcham.modules.wmyj.entity.WmyjTblInfotextREntity;
import io.youcham.modules.wmyj.service.WmyjTblInfotextRService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 出口信息问题汇总表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:29
 */
@RestController
@RequestMapping("wmyj/wmyjtblinfotextr")
public class WmyjTblInfotextRController {
    @Autowired
    private WmyjTblInfotextRService wmyjTblInfotextRService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wmyj:wmyjtblinfotextr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmyjTblInfotextRService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wmyj:wmyjtblinfotextr:info")
    public R info(@PathVariable("id") String id){
        WmyjTblInfotextREntity wmyjTblInfotextR = wmyjTblInfotextRService.selectById(id);

        return R.ok().put("wmyjTblInfotextR", wmyjTblInfotextR);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wmyj:wmyjtblinfotextr:save")
    public R save(@RequestBody WmyjTblInfotextREntity wmyjTblInfotextR){
    	ValidatorUtils.validateEntity(wmyjTblInfotextR,AddGroup.class);
    
   	 	wmyjTblInfotextR.setCreateId(ShiroUtils.getUserId());
    	wmyjTblInfotextR.setCreateTime(new Date());
        wmyjTblInfotextR.setCreateName(ShiroUtils.getUserEntity().getName());
        wmyjTblInfotextRService.insert(wmyjTblInfotextR);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wmyj:wmyjtblinfotextr:update")
    public R update(@RequestBody WmyjTblInfotextREntity wmyjTblInfotextR){
        ValidatorUtils.validateEntity(wmyjTblInfotextR,AddGroup.class);
       
        wmyjTblInfotextR.setUpdateId(ShiroUtils.getUserId());
    	wmyjTblInfotextR.setUpdateTime(new Date());
        wmyjTblInfotextR.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = wmyjTblInfotextRService.updateAllColumnById(wmyjTblInfotextR);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wmyj:wmyjtblinfotextr:delete")
    public R delete(@RequestBody String[] ids){
        wmyjTblInfotextRService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

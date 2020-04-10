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

import io.youcham.modules.wmyj.entity.WmyjCityareaEntity;
import io.youcham.modules.wmyj.service.WmyjCityareaService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 市区代码表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-31 16:57:31
 */
@RestController
@RequestMapping("wmyj/wmyjcityarea")
public class WmyjCityareaController {
    @Autowired
    private WmyjCityareaService wmyjCityareaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wmyj:wmyjcityarea:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmyjCityareaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("wmyj:wmyjcityarea:info")
    public R info(@PathVariable("id") String id){
        WmyjCityareaEntity wmyjCityarea = wmyjCityareaService.selectById(id);

        return R.ok().put("wmyjCityarea", wmyjCityarea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wmyj:wmyjcityarea:save")
    public R save(@RequestBody WmyjCityareaEntity wmyjCityarea){
    	ValidatorUtils.validateEntity(wmyjCityarea,AddGroup.class);
    
   	 	wmyjCityarea.setCreateId(ShiroUtils.getUserId());
    	wmyjCityarea.setCreateTime(new Date());
        wmyjCityarea.setCreateName(ShiroUtils.getUserEntity().getName());
        wmyjCityareaService.insert(wmyjCityarea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wmyj:wmyjcityarea:update")
    public R update(@RequestBody WmyjCityareaEntity wmyjCityarea){
        ValidatorUtils.validateEntity(wmyjCityarea,AddGroup.class);
       
        wmyjCityarea.setUpdateId(ShiroUtils.getUserId());
    	wmyjCityarea.setUpdateTime(new Date());
        wmyjCityarea.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = wmyjCityareaService.updateAllColumnById(wmyjCityarea);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wmyj:wmyjcityarea:delete")
    public R delete(@RequestBody String[] ids){
        wmyjCityareaService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

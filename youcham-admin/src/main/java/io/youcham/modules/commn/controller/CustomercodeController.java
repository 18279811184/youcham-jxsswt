package io.youcham.modules.commn.controller;

import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.ValidatorUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.youcham.modules.commn.entity.CustomercodeEntity;
import io.youcham.modules.commn.service.CustomercodeService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-12-06 16:55:27
 */
@RestController
@RequestMapping("commn/customercode")
public class CustomercodeController {
    @Autowired
    private CustomercodeService customercodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("commn:customercode:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = customercodeService.queryPage(params);
        return R.ok().put("page", page);
    }
    
    @RequestMapping("/all")
    public R getOwnerCode(){
        List<CustomercodeEntity> list = customercodeService.getOwnerCode();
        return R.ok().put("customercode", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{code}")
    @RequiresPermissions("commn:customercode:info")
    public R info(@PathVariable("code") String code){
        CustomercodeEntity customercode = customercodeService.selectById(code);

        return R.ok().put("customercode", customercode);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("commn:customercode:save")
    public R save(@RequestBody CustomercodeEntity customercode){
    	ValidatorUtils.validateEntity(customercode,AddGroup.class);
    
//   	 	customercode.setCreateId(ShiroUtils.getUserId());
//    	customercode.setCreateTime(new Date());
        customercodeService.insert(customercode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("commn:customercode:update")
    public R update(@RequestBody CustomercodeEntity customercode){
        ValidatorUtils.validateEntity(customercode,AddGroup.class);
       
//        customercode.setUpdateId(ShiroUtils.getUserId());
//    	customercode.setUpdateTime(new Date());
        Boolean re = customercodeService.updateAllColumnById(customercode);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("commn:customercode:delete")
    public R delete(@RequestBody String[] codes){
        customercodeService.deleteBatchIds(Arrays.asList(codes));

        return R.ok();
    }

}

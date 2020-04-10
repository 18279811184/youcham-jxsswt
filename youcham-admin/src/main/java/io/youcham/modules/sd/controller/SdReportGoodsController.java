package io.youcham.modules.sd.controller;

import io.youcham.modules.sd.entity.SdReportGoodsEntity;
import io.youcham.modules.sd.service.SdReportGoodsService;
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
 * 申报物资清单
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
@RestController
@RequestMapping("sd/sdreportgoods")
public class SdReportGoodsController {
    @Autowired
    private SdReportGoodsService sdReportGoodsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("sd:sdreportgoods:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sdReportGoodsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sd:sdreportgoods:info")
    public R info(@PathVariable("id") String id){
        SdReportGoodsEntity sdReportGoods = sdReportGoodsService.selectById(id);

        return R.ok().put("sdReportGoods", sdReportGoods);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sd:sdreportgoods:save")
    public R save(@RequestBody SdReportGoodsEntity sdReportGoods){
    	ValidatorUtils.validateEntity(sdReportGoods,AddGroup.class);
    
   	 	sdReportGoods.setCreateId(ShiroUtils.getUserId());
    	sdReportGoods.setCreateTime(new Date());
        sdReportGoods.setCreateName(ShiroUtils.getUserEntity().getName());
        sdReportGoodsService.insert(sdReportGoods);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sd:sdreportgoods:update")
    public R update(@RequestBody SdReportGoodsEntity sdReportGoods){
        ValidatorUtils.validateEntity(sdReportGoods,AddGroup.class);
       
        sdReportGoods.setUpdateId(ShiroUtils.getUserId());
    	sdReportGoods.setUpdateTime(new Date());
        sdReportGoods.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = sdReportGoodsService.updateAllColumnById(sdReportGoods);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sd:sdreportgoods:delete")
    public R delete(@RequestBody String[] ids){
        sdReportGoodsService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}

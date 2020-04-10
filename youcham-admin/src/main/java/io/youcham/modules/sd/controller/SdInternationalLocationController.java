package io.youcham.modules.sd.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.youcham.modules.sys.shiro.ShiroUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.ValidatorUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.youcham.modules.sd.entity.SdInternationalLocationEntity;
import io.youcham.modules.sd.service.SdInternationalLocationService;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;



/**
 * 全球地市表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-01 21:09:05
 */
@RestController
@RequestMapping("sd/sdinternationallocation")
public class SdInternationalLocationController {
    @Autowired
    private SdInternationalLocationService sdInternationalLocationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sd:sdinternationallocation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sdInternationalLocationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sd:sdinternationallocation:info")
    public R info(@PathVariable("id") String id){
        SdInternationalLocationEntity sdInternationalLocation = sdInternationalLocationService.selectById(id);

        return R.ok().put("sdInternationalLocation", sdInternationalLocation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sd:sdinternationallocation:save")
    public R save(@RequestBody SdInternationalLocationEntity sdInternationalLocation){
    	ValidatorUtils.validateEntity(sdInternationalLocation,AddGroup.class);
    
   	 	sdInternationalLocation.setCreateId(ShiroUtils.getUserId());
    	sdInternationalLocation.setCreateTime(new Date());
        sdInternationalLocation.setCreateName(ShiroUtils.getUserEntity().getName());
        sdInternationalLocationService.insert(sdInternationalLocation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sd:sdinternationallocation:update")
    public R update(@RequestBody SdInternationalLocationEntity sdInternationalLocation){
        ValidatorUtils.validateEntity(sdInternationalLocation,AddGroup.class);
       
        sdInternationalLocation.setUpdateId(ShiroUtils.getUserId());
    	sdInternationalLocation.setUpdateTime(new Date());
        sdInternationalLocation.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = sdInternationalLocationService.updateAllColumnById(sdInternationalLocation);//全部更新
        
        return re?R.ok():R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sd:sdinternationallocation:delete")
    public R delete(@RequestBody String[] ids){
        sdInternationalLocationService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 获取国家或省
     */
    @RequestMapping("/getCountryOrProvince")
    public R getCountryOrProvince(@RequestParam Map<String, Object> params){
        String level = (String)params.get("level");
        String pid = (String)params.get("pid");

        List<SdInternationalLocationEntity> entities = sdInternationalLocationService.selectList(
                new EntityWrapper<SdInternationalLocationEntity>()
                        .eq(StringUtils.isNotBlank(level), "area_level",level)
                        .eq(StringUtils.isNotBlank(pid), "pid",pid)
        );

        return R.ok().put("list",entities);
    }
}

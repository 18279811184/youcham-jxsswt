package io.youcham.modules.swzj.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.Assert;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.modules.swzj.entity.SwzjCountyEntity;
import io.youcham.modules.swzj.service.impl.SwzjCountyService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 全国城市地区表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-05 18:15:08
 */
@RestController
@RequestMapping("swzj/swzjcounty")
public class SwzjCountyController {
    @Autowired
    private SwzjCountyService swzjCountyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("swzj:swzjcounty:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = swzjCountyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("swzj:swzjcounty:info")
    public R info(@PathVariable("id") String id){
        SwzjCountyEntity swzjCounty = swzjCountyService.selectById(id);

        return R.ok().put("swzjCounty", swzjCounty);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("swzj:swzjcounty:save")
    public R save(@RequestBody SwzjCountyEntity swzjCounty){
    	ValidatorUtils.validateEntity(swzjCounty, AddGroup.class);
        Assert.isNull(swzjCounty.getCode(), "地市代码不能为空");

        SwzjCountyEntity countyEntity = swzjCountyService.selectOne(
                new EntityWrapper<SwzjCountyEntity>()
                .eq("code",swzjCounty.getCode())
        );
        if(null != countyEntity){
            return R.error("地市代码已存在");
        }


   	 	swzjCounty.setCreateId(ShiroUtils.getUserId());
    	swzjCounty.setCreateTime(new Date());
        swzjCounty.setCreateName(ShiroUtils.getUserEntity().getName());
        swzjCountyService.insert(swzjCounty);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("swzj:swzjcounty:update")
    public R update(@RequestBody SwzjCountyEntity swzjCounty){
        ValidatorUtils.validateEntity(swzjCounty, AddGroup.class);

        SwzjCountyEntity countyEntity = swzjCountyService.selectOne(
                new EntityWrapper<SwzjCountyEntity>()
                        .eq("code",swzjCounty.getCode())
                        .ne("id",swzjCounty.getId())
        );

        if(null != countyEntity){
            return R.error("地市代码已存在");
        }

        swzjCounty.setUpdateId(ShiroUtils.getUserId());
    	swzjCounty.setUpdateTime(new Date());
        swzjCounty.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = swzjCountyService.updateAllColumnById(swzjCounty);//全部更新
        
        return re? R.ok(): R.oldVersion();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("swzj:swzjcounty:delete")
    public R delete(@RequestBody String[] ids){
        swzjCountyService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/select")
    public R select(@RequestParam Map<String, Object> params){

        String cityLevel = (String) params.get("cityLevel");
        String firstLevelCityId = (String) params.get("firstLevelCityId");
        String twoLevelCityId = (String) params.get("twoLevelCityId");
        String code = (String) params.get("code");
        String county = (String) ShiroUtils.getUserEntity().getCounty();
        Integer level = (Integer) ShiroUtils.getUserEntity().getAreaLevel();
        String twoLevelCityName = (String) params.get("twoLevelCityName");
        if(StringUtils.isNotEmpty(twoLevelCityName)){
            SwzjCountyEntity city = swzjCountyService.selectList(
                    new EntityWrapper<SwzjCountyEntity>().eq("name",twoLevelCityName)).get(0);
            twoLevelCityId = city.getId();
        }
        boolean flag = StringUtils.isBlank(cityLevel);
        boolean flag1 = StringUtils.isBlank(cityLevel);
        List<SwzjCountyEntity> list = swzjCountyService.selectList(
                new EntityWrapper<SwzjCountyEntity>()
                        .eq(StringUtils.isNotEmpty(firstLevelCityId),"first_Level_Parent",firstLevelCityId)
                        .eq(StringUtils.isNotEmpty(twoLevelCityId),"two_Level_Parent",twoLevelCityId)
                        .eq(StringUtils.isNotEmpty(cityLevel),"city_level",cityLevel)
                        .eq(StringUtils.isNotEmpty(code),"code",code)
                        /* 只针对县区限制 */
                        .eq(level == 2 && (flag || flag1), "id", county)
                       );
        return R.ok().put("list", list);
    }

    /**
     * 查询本省所有的市
     * @return
     */
    @RequestMapping("/selectCityByJiangXi")
    public R selectCityByJiangXi(){
        String city = (String) ShiroUtils.getUserEntity().getCity();
        String county = (String) ShiroUtils.getUserEntity().getCounty();
        Integer level = (Integer) ShiroUtils.getUserEntity().getAreaLevel();
        /* 江西省 */
        SwzjCountyEntity entity = swzjCountyService.selectOne(new EntityWrapper<SwzjCountyEntity>()
        .eq("code","360000")
        .eq("city_level",0)
        );
        List<SwzjCountyEntity> list  = new ArrayList<>();
        if (entity != null) {
            list = swzjCountyService
                    .selectList(new EntityWrapper<SwzjCountyEntity>()
                            .eq("first_Level_Parent", entity.getId())
                            .eq("city_level",1)
//                            .eq(level == 1 || level == 2,"id", city)
                    );
        }
        return R.ok().put("list", list);
    }

}

package io.youcham.modules.swzj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.swzj.dao.SwzjCountyDao;
import io.youcham.modules.swzj.entity.SwzjCountyEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("swzjCountyService")
public class SwzjCountyServiceImpl extends ServiceImpl<SwzjCountyDao, SwzjCountyEntity> implements io.youcham.modules.swzj.service.impl.SwzjCountyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        String parentId = (String) params.get("parentId");
        String parentCityLevel = (String) params.get("parentCityLevel");
        String cityLevel = (String) params.get("cityLevel");

        Page<SwzjCountyEntity> page = this.selectPage(
                new Query<SwzjCountyEntity>(params).getPage(),
                new EntityWrapper<SwzjCountyEntity>()
                        .like(StringUtils.isNotEmpty(name),"name","%"+name+"%")
                        .eq(StringUtils.isNotEmpty(cityLevel),"city_Level",cityLevel)
                        .eq(StringUtils.isNotEmpty(parentCityLevel) && StringUtils.isNotEmpty(parentId)
                                && "0".equals(parentCityLevel),"first_level_parent",parentId)
                        .eq(StringUtils.isNotEmpty(parentCityLevel) && StringUtils.isNotEmpty(parentId)
                                && "1".equals(parentCityLevel),"two_level_parent",parentId)
                .orderBy("city_level,first_level_parent,two_level_parent,order_num")
        );

        for (SwzjCountyEntity record : page.getRecords()) {
            SwzjCountyEntity firstLevelCity = this.selectById(record.getFirstLevelParent());
            if(null!=firstLevelCity){
                record.setFirstLevelParentName(firstLevelCity.getName());
            }
            SwzjCountyEntity twoLevelCity = this.selectById(record.getTwoLevelParent());
            if(null!=twoLevelCity){
                record.setTwoLevelParentName(twoLevelCity.getName());
            }
            SwzjCountyEntity relationCounty = this.selectById(record.getRelationCounty());
            if(null!=relationCounty){
                record.setRelationCountyName(relationCounty.getName());
            }

        }

        return new PageUtils(page);
    }

}

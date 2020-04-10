package io.youcham.modules.commn.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.datasources.DataSourceNames;
import io.youcham.datasources.annotation.DataSource;
import io.youcham.modules.commn.dao.CustomercodeDao;
import io.youcham.modules.commn.entity.CustomercodeEntity;
import io.youcham.modules.commn.service.CustomercodeService;


@Service("customercodeService")
public class CustomercodeServiceImpl extends ServiceImpl<CustomercodeDao, CustomercodeEntity> implements CustomercodeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CustomercodeEntity> page = this.selectPage(
                new Query<CustomercodeEntity>(params).getPage(),
                new EntityWrapper<CustomercodeEntity>()
        );

        return new PageUtils(page);
    }

    @DataSource(name = DataSourceNames.SECOND)
	@Override
	public List<CustomercodeEntity> getOwnerCode() {
		List<CustomercodeEntity> list = baseMapper.getOwnerCode();
		return list;
	}

}

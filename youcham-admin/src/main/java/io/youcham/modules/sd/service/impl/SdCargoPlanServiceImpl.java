package io.youcham.modules.sd.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.sd.dao.SdCargoPlanDao;
import io.youcham.modules.sd.entity.SdCargoPlanEntity;
import io.youcham.modules.sd.service.SdCargoPlanService;


@Service("sdCargoPlanService")
public class SdCargoPlanServiceImpl extends ServiceImpl<SdCargoPlanDao, SdCargoPlanEntity> implements SdCargoPlanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SdCargoPlanEntity> page = this.selectPage(
                new Query<SdCargoPlanEntity>(params).getPage(),
                new EntityWrapper<SdCargoPlanEntity>().orderBy("CREATE_TIME")
        );

        return new PageUtils(page);
    }

}

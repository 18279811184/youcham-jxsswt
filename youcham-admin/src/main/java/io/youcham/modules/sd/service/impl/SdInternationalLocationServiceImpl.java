package io.youcham.modules.sd.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.sd.dao.SdInternationalLocationDao;
import io.youcham.modules.sd.entity.SdInternationalLocationEntity;
import io.youcham.modules.sd.service.SdInternationalLocationService;


@Service("sdInternationalLocationService")
public class SdInternationalLocationServiceImpl extends ServiceImpl<SdInternationalLocationDao, SdInternationalLocationEntity> implements SdInternationalLocationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SdInternationalLocationEntity> page = this.selectPage(
                new Query<SdInternationalLocationEntity>(params).getPage(),
                new EntityWrapper<SdInternationalLocationEntity>()
        );

        return new PageUtils(page);
    }

}

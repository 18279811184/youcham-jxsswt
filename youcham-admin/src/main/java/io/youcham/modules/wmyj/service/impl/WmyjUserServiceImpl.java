package io.youcham.modules.wmyj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.wmyj.dao.WmyjUserDao;
import io.youcham.modules.wmyj.entity.WmyjUserEntity;
import io.youcham.modules.wmyj.service.WmyjUserService;


@Service("wmyjUserService")
public class WmyjUserServiceImpl extends ServiceImpl<WmyjUserDao, WmyjUserEntity> implements WmyjUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WmyjUserEntity> page = this.selectPage(
                new Query<WmyjUserEntity>(params).getPage(),
                new EntityWrapper<WmyjUserEntity>()
        );

        return new PageUtils(page);
    }

}

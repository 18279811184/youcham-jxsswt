package io.youcham.modules.wmyj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.wmyj.dao.WmyjTblInfocodeDao;
import io.youcham.modules.wmyj.entity.WmyjTblInfocodeEntity;
import io.youcham.modules.wmyj.service.WmyjTblInfocodeService;


@Service("wmyjTblInfocodeService")
public class WmyjTblInfocodeServiceImpl extends ServiceImpl<WmyjTblInfocodeDao, WmyjTblInfocodeEntity> implements WmyjTblInfocodeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WmyjTblInfocodeEntity> page = this.selectPage(
                new Query<WmyjTblInfocodeEntity>(params).getPage(),
                new EntityWrapper<WmyjTblInfocodeEntity>()
        );

        return new PageUtils(page);
    }

}

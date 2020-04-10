package io.youcham.modules.wmyj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.wmyj.dao.WmyjTblInfoamountRDao;
import io.youcham.modules.wmyj.entity.WmyjTblInfoamountREntity;
import io.youcham.modules.wmyj.service.WmyjTblInfoamountRService;


@Service("wmyjTblInfoamountRService")
public class WmyjTblInfoamountRServiceImpl extends ServiceImpl<WmyjTblInfoamountRDao, WmyjTblInfoamountREntity> implements WmyjTblInfoamountRService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WmyjTblInfoamountREntity> page = this.selectPage(
                new Query<WmyjTblInfoamountREntity>(params).getPage(),
                new EntityWrapper<WmyjTblInfoamountREntity>()
        );

        return new PageUtils(page);
    }

}

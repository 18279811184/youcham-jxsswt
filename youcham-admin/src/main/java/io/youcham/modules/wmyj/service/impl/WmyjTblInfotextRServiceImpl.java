package io.youcham.modules.wmyj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.wmyj.dao.WmyjTblInfotextRDao;
import io.youcham.modules.wmyj.entity.WmyjTblInfotextREntity;
import io.youcham.modules.wmyj.service.WmyjTblInfotextRService;


@Service("wmyjTblInfotextRService")
public class WmyjTblInfotextRServiceImpl extends ServiceImpl<WmyjTblInfotextRDao, WmyjTblInfotextREntity> implements WmyjTblInfotextRService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WmyjTblInfotextREntity> page = this.selectPage(
                new Query<WmyjTblInfotextREntity>(params).getPage(),
                new EntityWrapper<WmyjTblInfotextREntity>()
        );

        return new PageUtils(page);
    }

}

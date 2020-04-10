package io.youcham.modules.student.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.student.dao.BigjavaClassDao;
import io.youcham.modules.student.entity.BigjavaClassEntity;
import io.youcham.modules.student.service.BigjavaClassService;


@Service("bigjavaClassService")
public class BigjavaClassServiceImpl extends ServiceImpl<BigjavaClassDao, BigjavaClassEntity> implements BigjavaClassService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BigjavaClassEntity> page = this.selectPage(
                new Query<BigjavaClassEntity>(params).getPage(),
                new EntityWrapper<BigjavaClassEntity>()
        );

        return new PageUtils(page);
    }

}

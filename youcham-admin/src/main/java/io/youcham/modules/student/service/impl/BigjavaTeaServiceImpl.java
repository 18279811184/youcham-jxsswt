package io.youcham.modules.student.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;

import io.youcham.modules.student.dao.BigjavaTeaDao;
import io.youcham.modules.student.entity.BigjavaTeaEntity;
import io.youcham.modules.student.service.BigjavaTeaService;


@Service("bigjavaTeaService")
public class BigjavaTeaServiceImpl extends ServiceImpl<BigjavaTeaDao, BigjavaTeaEntity> implements BigjavaTeaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BigjavaTeaEntity> page = this.selectPage(
                new Query<BigjavaTeaEntity>(params).getPage(),
                new EntityWrapper<BigjavaTeaEntity>()

        );

        return new PageUtils(page);
    }

}

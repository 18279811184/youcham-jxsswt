package io.youcham.modules.wmyj.service.impl;

import io.youcham.modules.wmyj.dao.WmyjCityareaDao;
import io.youcham.modules.wmyj.entity.WmyjCityareaEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;


import io.youcham.modules.wmyj.service.WmyjCityareaService;


@Service("wmyjCityareaService")
public class WmyjCityareaServiceImpl extends ServiceImpl<WmyjCityareaDao, WmyjCityareaEntity> implements WmyjCityareaService {

    @Override
    public WmyjCityareaEntity getWmyjCityareaEntityByCode(String code) {

        List<WmyjCityareaEntity> list = this.selectList(new EntityWrapper<WmyjCityareaEntity>().eq("CITYAREACODE",code));
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WmyjCityareaEntity> page = this.selectPage(
                new Query<WmyjCityareaEntity>(params).getPage(),
                new EntityWrapper<WmyjCityareaEntity>()
        );

        return new PageUtils(page);
    }

}

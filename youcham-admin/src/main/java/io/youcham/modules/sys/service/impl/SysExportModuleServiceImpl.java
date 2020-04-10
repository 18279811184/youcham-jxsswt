package io.youcham.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.sys.dao.SysExportModuleDao;
import io.youcham.modules.sys.entity.SysExportModuleEntity;
import io.youcham.modules.sys.service.SysExportModuleService;


@Service("sysExportModuleService")
public class SysExportModuleServiceImpl extends ServiceImpl<SysExportModuleDao, SysExportModuleEntity> implements SysExportModuleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysExportModuleEntity> page = this.selectPage(
                new Query<SysExportModuleEntity>(params).getPage(),
                new EntityWrapper<SysExportModuleEntity>()
                .like(StringUtils.isNotEmpty((String)params.get("name")),"name", (String)params.get("name"))
                .like(StringUtils.isNotEmpty((String)params.get("flag")),"flag", (String)params.get("flag"))
        );

        return new PageUtils(page);
    }

}

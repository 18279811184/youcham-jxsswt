package io.youcham.modules.sys.service.impl;

import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.sys.dao.SysExportParameterDao;
import io.youcham.modules.sys.entity.SysExportModuleEntity;
import io.youcham.modules.sys.entity.SysExportParameterEntity;
import io.youcham.modules.sys.service.SysExportModuleService;
import io.youcham.modules.sys.service.SysExportParameterService;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysExportParameterService")
public class SysExportParameterServiceImpl extends ServiceImpl<SysExportParameterDao, SysExportParameterEntity> implements SysExportParameterService {
	
	@Autowired
	private SysExportModuleService sysExportModuleService;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysExportParameterEntity> page = this.selectPage(
                new Query<SysExportParameterEntity>(params).getPage(),
                new EntityWrapper<SysExportParameterEntity>()
                .like(StringUtils.isNotEmpty((String)params.get("name")),"name", (String)params.get("name"))
                .eq(StringUtils.isNotEmpty((String)params.get("exportModuleId")),"export_module_id", (String)params.get("exportModuleId"))
        );

        for (SysExportParameterEntity exportParameter : page.getRecords()) {
			SysExportModuleEntity exportModule = sysExportModuleService.selectById(exportParameter.getExportModuleId());
			exportParameter.setExportModule(exportModule);
		}
        
        return new PageUtils(page);
    }

}

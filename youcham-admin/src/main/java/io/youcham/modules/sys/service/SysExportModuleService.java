package io.youcham.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sys.entity.SysExportModuleEntity;

import java.util.Map;

/**
 * 导出功能
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-10-11 09:49:51
 */
public interface SysExportModuleService extends IService<SysExportModuleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


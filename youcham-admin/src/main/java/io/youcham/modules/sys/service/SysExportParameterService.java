package io.youcham.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sys.entity.SysExportParameterEntity;

import java.util.Map;

/**
 * 导出参数
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-10-11 11:56:57
 */
public interface SysExportParameterService extends IService<SysExportParameterEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


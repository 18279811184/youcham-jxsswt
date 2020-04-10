package io.youcham.modules.sd.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sd.entity.SdInternationalLocationEntity;

import java.util.Map;

/**
 * 全球地市表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-01 21:09:05
 */
public interface SdInternationalLocationService extends IService<SdInternationalLocationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


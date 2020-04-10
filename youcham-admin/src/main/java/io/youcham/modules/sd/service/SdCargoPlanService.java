package io.youcham.modules.sd.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sd.entity.SdCargoPlanEntity;

import java.util.Map;

/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-01 14:52:06
 */
public interface SdCargoPlanService extends IService<SdCargoPlanEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


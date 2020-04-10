package io.youcham.modules.swzj.service.impl;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.swzj.entity.SwzjCountyEntity;

import java.util.Map;

/**
 * 全国城市地区表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-05 18:15:08
 */
public interface SwzjCountyService extends IService<SwzjCountyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


package io.youcham.modules.commn.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.commn.entity.CustomercodeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-12-06 16:55:27
 */
public interface CustomercodeService extends IService<CustomercodeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<CustomercodeEntity> getOwnerCode();
}


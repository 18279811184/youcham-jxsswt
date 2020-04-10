package io.youcham.modules.activity.service;


import com.baomidou.mybatisplus.service.IService;

import io.youcham.common.utils.PageUtils;
import io.youcham.modules.activity.entity.ActUserStepEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-12-20 09:31:25
 */
public interface ActUserStepService extends IService<ActUserStepEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    Map<String,Object> getActUserByDefineKey(String defineKey);
    
}


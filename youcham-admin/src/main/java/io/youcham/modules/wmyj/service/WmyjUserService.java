package io.youcham.modules.wmyj.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.wmyj.entity.WmyjUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-19 15:45:33
 */
public interface WmyjUserService extends IService<WmyjUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


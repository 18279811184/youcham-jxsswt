package io.youcham.modules.wmyj.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.wmyj.entity.WmyjCityareaEntity;

import java.util.Map;

/**
 * 市区代码表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-31 16:57:31
 */
public interface WmyjCityareaService extends IService<WmyjCityareaEntity> {

    //根据code查询信息
    public WmyjCityareaEntity getWmyjCityareaEntityByCode(String code);

    PageUtils queryPage(Map<String, Object> params);
}


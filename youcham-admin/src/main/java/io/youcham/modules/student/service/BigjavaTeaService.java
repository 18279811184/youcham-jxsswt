package io.youcham.modules.student.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.student.entity.BigjavaTeaEntity;

import java.util.Map;

/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-09 12:15:31
 */
public interface BigjavaTeaService extends IService<BigjavaTeaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


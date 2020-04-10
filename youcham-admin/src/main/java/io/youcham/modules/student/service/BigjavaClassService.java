package io.youcham.modules.student.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.student.entity.BigjavaClassEntity;

import java.util.Map;

/**
 * 
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-09 11:34:41
 */
public interface BigjavaClassService extends IService<BigjavaClassEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


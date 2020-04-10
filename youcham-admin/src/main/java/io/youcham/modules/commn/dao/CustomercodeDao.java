package io.youcham.modules.commn.dao;

import io.youcham.modules.commn.entity.CustomercodeEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-12-06 16:55:27
 */
public interface CustomercodeDao extends BaseMapper<CustomercodeEntity> {
	List<CustomercodeEntity> getOwnerCode();
}

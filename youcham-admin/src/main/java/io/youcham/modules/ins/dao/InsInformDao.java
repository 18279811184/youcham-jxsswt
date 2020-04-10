package io.youcham.modules.ins.dao;

import io.youcham.modules.ins.entity.InsInformEntity;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 新闻信息表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-06 14:33:31
 */
public interface InsInformDao extends BaseMapper<InsInformEntity> {
	/**
	 * 查询出虽有栏目为在线学习的数据
	 * @return
	 */
	List<InsInformEntity> queryInformList();
	
	/**
	 * 获取最大排序字段
	 */
	Integer getMaxOrderNum(String columnId);
	
}

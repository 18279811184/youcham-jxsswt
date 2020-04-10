package io.youcham.modules.wmyj.dao;

import com.baomidou.mybatisplus.plugins.Page;
import io.youcham.modules.wmyj.entity.WmyjInfoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 出口信息金额汇总表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-24 09:03:48
 */
public interface WmyjInfoDao extends BaseMapper<WmyjInfoEntity> {
	public List<Map<String,Object>> countData(Page<Map<String, Object>> page,Map<String,Object> params);

	public List<Map<String, Object>> countDataQSHJ(Page<Map<String, Object>> page, Map<String, Object> params);

	public Map<String, Object> countDataGroupByCity(Page<Map<String, Object>> page,Map<String, Object> params);

	public List<Map<String, Object>> countDataSJHZ(Page<Map<String, Object>> page,Map<String, Object> params);

	public List<Map<String, Object>> countDataXJHZ(Page<Map<String, Object>> page,Map<String, Object> params);
}

package io.youcham.modules.ins.service;

import io.youcham.common.utils.PageUtils;
import io.youcham.modules.ins.entity.InsInformEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 新闻信息表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-06 14:33:31
 */
public interface InsInformService extends IService<InsInformEntity> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 查询出所有栏目为在线学习的信息
     * @return
     */
    List<InsInformEntity> queryInformList();
    /**
	 * 获取最大排序字段
	 */
	Integer getMaxOrderNum(String columnId);
    
}


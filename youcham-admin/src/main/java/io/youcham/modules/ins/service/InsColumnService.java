package io.youcham.modules.ins.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.ins.entity.InsColumnEntity;

import java.util.List;
import java.util.Map;

/**
 * 栏目表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-06 14:11:58
 */
public interface InsColumnService extends IService<InsColumnEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<InsColumnEntity> queryselectList(Map<String, Object> map);
}


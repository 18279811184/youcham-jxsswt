package io.youcham.modules.wmyj.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.wmyj.entity.WmyjTblInfoamountREntity;

import java.util.Map;

/**
 * 出口信息金额汇总表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:30
 */
public interface WmyjTblInfoamountRService extends IService<WmyjTblInfoamountREntity> {

    PageUtils queryPage(Map<String, Object> params);
}


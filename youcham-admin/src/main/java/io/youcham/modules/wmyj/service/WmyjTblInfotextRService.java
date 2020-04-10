package io.youcham.modules.wmyj.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.wmyj.entity.WmyjTblInfotextREntity;

import java.util.Map;

/**
 * 出口信息问题汇总表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:29
 */
public interface WmyjTblInfotextRService extends IService<WmyjTblInfotextREntity> {

    PageUtils queryPage(Map<String, Object> params);
}


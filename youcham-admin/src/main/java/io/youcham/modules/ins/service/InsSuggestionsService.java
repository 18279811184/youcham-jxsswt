package io.youcham.modules.ins.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.ins.entity.InsSuggestionsEntity;

import java.util.Map;

/**
 * 意见建议
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-10 10:45:30
 */
public interface InsSuggestionsService extends IService<InsSuggestionsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


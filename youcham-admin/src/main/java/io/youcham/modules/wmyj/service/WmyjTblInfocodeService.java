package io.youcham.modules.wmyj.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.wmyj.entity.WmyjTblInfocodeEntity;

import java.util.Map;

/**
 * 企业代表码
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:28
 */
public interface WmyjTblInfocodeService extends IService<WmyjTblInfocodeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


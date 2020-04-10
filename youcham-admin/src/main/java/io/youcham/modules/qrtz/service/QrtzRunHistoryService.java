package io.youcham.modules.qrtz.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.qrtz.entity.QrtzRunHistoryEntity;

import java.util.Map;

/**
 * 定时任务执行历史记录
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-01-04 15:43:04
 */
public interface QrtzRunHistoryService extends IService<QrtzRunHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


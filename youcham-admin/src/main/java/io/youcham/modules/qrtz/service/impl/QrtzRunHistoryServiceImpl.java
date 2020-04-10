package io.youcham.modules.qrtz.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.qrtz.dao.QrtzRunHistoryDao;
import io.youcham.modules.qrtz.entity.QrtzRunHistoryEntity;
import io.youcham.modules.qrtz.service.QrtzRunHistoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("qrtzRunHistoryService")
public class QrtzRunHistoryServiceImpl extends ServiceImpl<QrtzRunHistoryDao, QrtzRunHistoryEntity> implements QrtzRunHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String remark = (String) params.get("remark");

        Page<QrtzRunHistoryEntity> page = this.selectPage(
                new Query<QrtzRunHistoryEntity>(params).getPage(),
                new EntityWrapper<QrtzRunHistoryEntity>()
                .like(StringUtils.isNotEmpty(remark),"remark",remark)
                .orderBy("create_time desc")
        );

        return new PageUtils(page);
    }

}

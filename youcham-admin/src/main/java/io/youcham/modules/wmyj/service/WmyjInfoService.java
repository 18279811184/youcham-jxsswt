package io.youcham.modules.wmyj.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.wmyj.entity.WmyjInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 出口信息金额汇总表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-24 09:03:48
 */
public interface WmyjInfoService extends IService<WmyjInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public void audit(String[] ids,String auditFlag);


    //统计数据汇总
    public PageUtils countData(Map<String, Object> params);

    public List<WmyjInfoEntity> queryData2Export(Map<String, Object> params);

    String importAllExcel(List<Map<String, Object>> customerList, String dt);
}


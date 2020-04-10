package io.youcham.modules.ins.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.ins.entity.SysFiletableEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件表
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-18 09:56:24
 */
public interface SysFiletableService extends IService<SysFiletableEntity> {

    PageUtils queryPage(Map<String, Object> params);
	
	//批量更新
	String updatemark(String mark, List<String> ids);
	
	List<SysFiletableEntity> queryList(Map<String, Object> params);
	
}


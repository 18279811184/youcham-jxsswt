package io.youcham.modules.ins.dao;

import io.lettuce.core.dynamic.annotation.Param;
import io.youcham.modules.ins.entity.SysFiletableEntity;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 文件表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-18 09:56:24
 */
public interface SysFiletableDao extends BaseMapper<SysFiletableEntity> {
       int insertfile(SysFiletableEntity sysfile);
	
       int updatemark(Map<String, Object> condition);
}

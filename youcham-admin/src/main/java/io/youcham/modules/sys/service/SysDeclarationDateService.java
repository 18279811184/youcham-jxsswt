package io.youcham.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sys.entity.SysDeclarationDateEntity;

import java.util.Map;


public interface SysDeclarationDateService extends IService<SysDeclarationDateEntity> {

    PageUtils queryPage(Map<String,Object> params);
}

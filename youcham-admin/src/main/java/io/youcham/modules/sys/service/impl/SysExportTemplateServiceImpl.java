package io.youcham.modules.sys.service.impl;

import io.youcham.common.constant.StatusEnum;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.sys.dao.SysExportTemplateDao;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysExportModuleEntity;
import io.youcham.modules.sys.entity.SysExportTemplateEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysDeptService;
import io.youcham.modules.sys.service.SysExportModuleService;
import io.youcham.modules.sys.service.SysExportTemplateService;
import io.youcham.modules.sys.service.SysUserService;
import io.youcham.modules.sys.shiro.ShiroUtils;

import io.youcham.common.constant.Variable;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("sysExportTemplateService")
public class SysExportTemplateServiceImpl extends ServiceImpl<SysExportTemplateDao, SysExportTemplateEntity>
		implements SysExportTemplateService {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysExportModuleService sysExportModuleService;
	@Autowired
	private SysDeptService sysDeptService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {


		Page<SysExportTemplateEntity> page = null;
		// 获取当前用户的部门信息
		SysDeptEntity dept = (SysDeptEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue());
		//查询出该部门下的所有子部门
		String ids =sysDeptService.getPermissionDeptIdsStr(true,true);


		if (dept.getDeptId().equals("1")) {
			page = this.selectPage(new Query<SysExportTemplateEntity>(params).getPage(),
					new EntityWrapper<SysExportTemplateEntity>()
							.like(StringUtils.isNotEmpty((String) params.get("name")), "name",
									(String) params.get("name"))
							.in(StringUtils.isNotEmpty((String) params.get("exportModuleId")), "export_module_id",
									(String) params.get("exportModuleId")));
		}else {
			page = this.selectPage(new Query<SysExportTemplateEntity>(params).getPage(),
					new EntityWrapper<SysExportTemplateEntity>()
							.like(StringUtils.isNotEmpty((String) params.get("name")), "name",
									(String) params.get("name"))
							.eq(StringUtils.isNotEmpty((String) params.get("exportModuleId")), "export_module_id",
									(String) params.get("exportModuleId"))
							.and("MBTYPE = 0 or DEPT_ID in ("+ids+")" ));
		}


		for (SysExportTemplateEntity exportTemplate : page.getRecords()) {

			if(exportTemplate != null ){


			SysExportModuleEntity exportModule = sysExportModuleService.selectById(exportTemplate.getExportModuleId());
			exportTemplate.setExportModule(exportModule);
			}
			SysUserEntity user = sysUserService.selectById(exportTemplate.getCreateId());
			if (null != user) {
				exportTemplate.setCreateUser(user.getName());
			}
		}


		return new PageUtils(page);
	}

	@Override
	public SysExportTemplateEntity getEnableTemplateByModuleFlag(String moduleFlag) {
		List<SysExportModuleEntity> modules = sysExportModuleService
				.selectList(new EntityWrapper<SysExportModuleEntity>().eq("flag", moduleFlag));

		if (modules.size() == 0) {
			return null;
		}

		List<SysExportTemplateEntity> list = this.selectList(new EntityWrapper<SysExportTemplateEntity>()
				.eq("status", StatusEnum.NORMAL.getValue()).eq("export_module_id", modules.get(0).getId()));

		return list.size() > 0 ? list.get(0) : null;
	}

}

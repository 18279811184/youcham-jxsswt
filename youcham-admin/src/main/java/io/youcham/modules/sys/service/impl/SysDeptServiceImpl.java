/**
 * Copyright 2018 人人开源 http://www.youcham.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.youcham.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.youcham.common.annotation.DataFilter;
import io.youcham.common.constant.Variable;
import io.youcham.modules.sys.dao.SysDeptDao;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.service.SysDeptService;
import io.youcham.modules.sys.service.SysRoleDeptService;
import io.youcham.modules.sys.service.SysUserRoleService;
import io.youcham.modules.sys.shiro.ShiroUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sysDeptService")
public class SysDeptServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<SysDeptDao, SysDeptEntity>
		implements SysDeptService {
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;

	@Override
	//@DataFilter(subDept = true, user = false)
	public List<SysDeptEntity> queryList(Map<String, Object> params) {
		// add by xcg
		String level = (String) params.get("level");
		String status = (String) params.get("status");

		// 获取登录用户
		SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());

		String deptid = "";

		List<SysDeptEntity> deptList = this.selectList(new EntityWrapper<SysDeptEntity>()
				.in(StringUtils.isNotBlank(level), "level", level).eq(StringUtils.isNotBlank(status), "status", status)
				//add by xcg
				.in(StringUtils.isNotBlank(deptid), "DEPT_ID", deptid)
				// .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)
				// params.get(Constant.SQL_FILTER))
				.orderBy("order_num"));

		for (SysDeptEntity sysDeptEntity : deptList) {
			SysDeptEntity parentDeptEntity = this.selectById(sysDeptEntity.getParentId());
			if (parentDeptEntity != null) {
				sysDeptEntity.setParentName(parentDeptEntity.getName());
			}
		}
		return deptList;
	}

	@Override
	public List<String> queryDetpIdList(String parentId) {
		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<String> getSubDeptIdList(String deptId) {
		// 部门及子部门ID列表
		List<String> deptIdList = new ArrayList<>();

		// 获取子部门ID
		List<String> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<String> subIdList, List<String> deptIdList) {
		for (String deptId : subIdList) {
			List<String> list = queryDetpIdList(deptId);
			if (list.size() > 0) {
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}

	@Override
	public List<SysDeptEntity> queryDetpLevel() {

		return baseMapper.queryDetpLevel();
	}

	@Override
	public List<SysDeptEntity> queryDetp(Integer value) {
		// TODO Auto-generated method stub
		return baseMapper.queryDetp(value);
	}

	@Override
	public SysDeptEntity getBySC(String sysCode, String companyId) {
		SysDeptEntity sysDeptEntity = this
				.selectOne(new EntityWrapper<SysDeptEntity>().eq("sys_code", sysCode).eq("company_id", companyId));

		return sysDeptEntity;
	}

	@Override
	public String insertdept(SysDeptEntity sysdept) {
		int bl = baseMapper.insertdept(sysdept);
		String id = null;
		if (bl > 0) {
			id = sysdept.getDeptId();

		}

		return id;
	}

	@Override
	public boolean updatedept(SysDeptEntity sysdept) {
		boolean s = baseMapper.updatedept(sysdept);
		return s;
	}

	@Override
	public SysDeptEntity selectByIdzdy(String id) {
		// TODO Auto-generated method stub
		SysDeptEntity sysdept = new SysDeptEntity();
		sysdept = baseMapper.sysdept(id);

		return sysdept;
	}

	/**
	 * 获取有用权限的部门id
	 *
	 * @param hasSelf
	 * @param hasChild
	 * @return
	 */
	@Override
	public String getPermissionDeptIdsStr(Boolean hasSelf, Boolean hasChild) {


		SysUserEntity user = ShiroUtils.getUserEntity();

		//部门ID列表
		Set<String> deptIdList = new HashSet<>();

		//用户角色对应的部门ID列表
		List<String> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
		if (roleIdList.size() > 0) {
			List<String> userDeptIdList = sysRoleDeptService.queryDeptIdList(roleIdList.toArray(new String[roleIdList.size()]));
			deptIdList.addAll(userDeptIdList);
		}

		if (hasSelf) {
			deptIdList.add(user.getDeptId());
			//用户子部门ID列表
			if (hasChild) {
				List<String> subDeptIdList = this.getSubDeptIdList(user.getDeptId());
				deptIdList.addAll(subDeptIdList);
			}
		}

		StringBuilder sqlFilter = new StringBuilder();

		if (deptIdList.size() > 0) {
			sqlFilter.append("'").append(StringUtils.join(deptIdList, "','")).append("'");
		}

		return sqlFilter.toString();
	}

	/**
	 * 获取有用权限的部门id
	 *
	 * @param hasSelf
	 * @param hasChild
	 * @return
	 */
	@Override
	public List<String> getPermissionDeptIdsArr(Boolean hasSelf, Boolean hasChild) {
		SysUserEntity user = ShiroUtils.getUserEntity();

		//部门ID列表
		Set<String> deptIdList = new HashSet<>();

		//用户角色对应的部门ID列表
		List<String> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
		if (roleIdList.size() > 0) {
			List<String> userDeptIdList = sysRoleDeptService.queryDeptIdList(roleIdList.toArray(new String[roleIdList.size()]));
			deptIdList.addAll(userDeptIdList);
		}

		if (hasSelf) {
			deptIdList.add(user.getDeptId());
			//用户子部门ID列表
			if (hasChild) {
				List<String> subDeptIdList = this.getSubDeptIdList(user.getDeptId());
				deptIdList.addAll(subDeptIdList);
			}
		}

		return new ArrayList<>(deptIdList);
	}


}

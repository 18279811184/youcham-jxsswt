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


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.MapUtils;
import io.youcham.modules.sys.dao.SysUserRoleDao;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.entity.SysUserRoleEntity;
import io.youcham.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {
	@Override
	public boolean saveOrUpdate(String userId, List<String> roleIdList) {
		//先删除用户与角色关系
		this.deleteByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return false;
		}
		
		//保存用户与角色关系
		List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for(String roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			list.add(sysUserRoleEntity);
		}
		return this.insertBatch(list);
	}
	
	@Override
	public boolean saveUserRole(String userId, String roleId) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(roleId)){
			return false;
		}
		
		//保存用户与角色关系
		SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
		sysUserRoleEntity.setUserId(userId);
		sysUserRoleEntity.setRoleId(roleId);
		
		return this.insert(sysUserRoleEntity);
	}
	
	@Override
	public void deleteUserRole(String userId, String roleId) {
		// TODO Auto-generated method stub
		//保存用户与角色关系
//		SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
//		sysUserRoleEntity.setUserId(userId);
//		sysUserRoleEntity.setRoleId(roleId);
//		
//		EntityWrapper<SysUserRoleEntity> ew = new EntityWrapper<SysUserRoleEntity>();
//	    ew.setEntity(sysUserRoleEntity);
//	    this.delete(ew);
	    
		this.delete(new EntityWrapper<SysUserRoleEntity>()
				.eq("USER_ID", userId)
				.eq("ROLE_ID", roleId)
		);
	}
	
	@Override
	public void saveOrUpdate1(String roleId, List<String> userIdList) {
		//先删除用户与角色关系
		this.deleteByMap(new MapUtils().put("ROLE_ID", roleId));

		if(userIdList == null || userIdList.size() == 0){
			return ;
		}
		
		//保存用户与角色关系
		List<SysUserRoleEntity> list = new ArrayList<>(userIdList.size());
		if(userIdList!=null) {
			for(String userId : userIdList){
				SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
				sysUserRoleEntity.setUserId(userId);
				sysUserRoleEntity.setRoleId(roleId);

				list.add(sysUserRoleEntity);
			}
		}	
		this.insertBatch(list);
	}
	@Override
	public List<String> queryRoleIdList(String userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(String[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

	
}

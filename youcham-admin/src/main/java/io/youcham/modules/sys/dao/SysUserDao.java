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

package io.youcham.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.entity.SysUserLsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(String userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<String> queryAllMenuId(String userId);
	/**
	 * 根据用户名和姓名查询出这条数据的id
//	 * @param usernamne  用户名
	 * @param name		   用户姓名
	 * @return
	 */
	String queryUserByup(@Param("username")String username,@Param("name")String name);
	
	SysUserEntity queryUserByEntity(SysUserEntity sysUserEntity);
	/**
	 * 根据部门查询用户
	 * @param deptId
	 * @return
	 */
	List<SysUserEntity> queryUserByDept(String deptId);
	/**
	 * 查询需要导出的用户
//	 * @param deptId
	 * @return
	 */
	List<SysUserLsEntity> queryUserByIndex(Map<String,Object> par);
	
	List<Map<String, Object>> queryAbb(Map<String,Object> params);


	List<Map> queryDb2();

}

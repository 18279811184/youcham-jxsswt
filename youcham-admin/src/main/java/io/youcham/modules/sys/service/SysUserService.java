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

package io.youcham.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.entity.SysUserLsEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
	PageUtils queryUserPage(Map<String, Object> params);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<String> queryAllMenuId(String userId);
	
	/**
	 * 保存用户
	 */
	boolean save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	boolean update(SysUserEntity user);
	
	boolean updateUser(SysUserEntity user);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(String userId, String password, String newPassword);
	
	/**
	 * 根据用户名和姓名查询出这条数据的id
	 * @param usernamne  用户名
	 * @param name		   用户姓名
	 * @return
	 */
	String queryUserByup(String username,String name);
	SysUserEntity queryUserByEntity(SysUserEntity sysUserEntity);
	/**
	 * 根据部门查询用户
	 * @param deptId
	 * @return
	 */
	List<SysUserEntity> queryUserByDept(String deptId);
	
	SysUserEntity getUserByWxUserId(String FromUserName);

	List<SysUserLsEntity> queryPageList(Map<String, Object> params);
	 
	List<Map<String, Object>> queryAbb(Map<String, Object> params);

	/**
	 * 通过 openid获取用户
	 * @param openid
	 * @return
	 */
	SysUserEntity getUserByOpenid(String openid);

	/**
	 * 验证用户名密码
	 */
	SysUserEntity validateUser(String username,String passwrod);

	List<Map> queryDb2();
}

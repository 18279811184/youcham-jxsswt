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

package io.youcham.modules.sys.shiro;


import io.youcham.common.constant.Variable;
import io.youcham.common.utils.R;
import io.youcham.modules.api.UserCenterClient;
import io.youcham.modules.sys.dao.SysDeptDao;
import io.youcham.modules.sys.dao.SysMenuDao;
import io.youcham.modules.sys.dao.SysUserDao;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysMenuEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 认证
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 上午11:55:49
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private UserCenterClient usercenter;


    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
		String userId = user.getUserId();
		
		List<String> permsList;
		
		//系统管理员，拥有最高权限
		if(ShiroUtils.isSuperUser()){
			List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
			permsList = new ArrayList<>(menuList.size());
			for(SysMenuEntity menu : menuList){
				permsList.add(menu.getPerms());
			}
		}else{
			permsList = sysUserDao.queryAllPerms(userId);
		}


		//用户权限列表
		Set<String> permsSet = new HashSet<>();
		for(String perms : permsList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		
		//查询用户信息
		SysUserEntity user = new SysUserEntity();
		if(StringUtils.isNotEmpty(token.getUsername())){
			user.setUsername(token.getUsername());
			//user = sysUserDao.selectOne(user);
			//add by xcg
			System.out.println(ShiroUtils.getSession().toString());
			System.out.println("cs--"+ShiroUtils.getSession().getAttribute("token"));
			System.out.println("uid--"+ShiroUtils.getSession().getAttribute("uid"));
	//		R userR = usercenter.getUserInfo(ShiroUtils.getSession().getAttribute("token").toString(),ShiroUtils.getSession().getAttribute("uid").toString());
		    String tks = (String)ShiroUtils.getSession().getAttribute("token");
			if(StringUtils.isNotEmpty(tks)){
				try {
					R userR = usercenter.getUserInfouid((String)ShiroUtils.getSession().getAttribute("token").toString(),(String)ShiroUtils.getSession().getAttribute("uid").toString(),(String)ShiroUtils.getSession().getAttribute("systemFlag").toString());
					
					Object m =   userR.get("user");
					if(m!=null){
						JSONObject jsonObject=JSONObject.fromObject(m);
						user = (SysUserEntity) JSONObject.toBean(jsonObject, SysUserEntity.class);
						 
					}
				} catch (Exception e) {
					// TODO: handle exception
					user = sysUserDao.selectOne(user);
				}
		    }else{
		    	user = sysUserDao.selectOne(user);
		    }
			
			
			
			//end
		}else{
			user = null;
		}
		
		
		//账号不存在
		if(user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		
		// 保存登录用户session
		ShiroUtils.setSessionAttribute(Variable.LOGIN_USER.getValue(), user);
		// 保存登录用户部门session
		SysDeptEntity depart = sysDeptDao.selectById(user.getDeptId());
		ShiroUtils.setSessionAttribute(Variable.LOGIN_USER_DEPART.getValue(), depart);
		
		String relationUserId = (String) ShiroUtils.getSessionAttribute("relationUserId");
		if(StringUtils.isNotEmpty(relationUserId)){// 存在关联id既无需登录
			String pp = ShiroUtils.sha256(relationUserId, user.getSalt());
			return new SimpleAuthenticationInfo(user, pp, ByteSource.Util.bytes(user.getSalt()), getName());
		}
		
		//当token存在且有效时  不适用密码  直接token登录
		/*String pp = ShiroUtils.sha256("wxuserId:"+user.getWxuserId()+":"+ps.split(":")[2], user.getSalt());
		return new SimpleAuthenticationInfo(user, pp, ByteSource.Util.bytes(user.getSalt()), getName());*/
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
		
		String tokense =  (String) ShiroUtils.getSession().getAttribute("token");
		
		if(tokense!=null&&tokense!=""){
			//R userRcs = usercenter.getUserInfoPass(ShiroUtils.getSession().getAttribute("token").toString(),ShiroUtils.getSession().getAttribute("uid").toString());
			try {
				R userRcs = usercenter.getUserInfouid((String)ShiroUtils.getSession().getAttribute("token").toString(),(String)ShiroUtils.getSession().getAttribute("uid").toString(),(String)ShiroUtils.getSession().getAttribute("systemFlag").toString());

				Integer codemy =  (Integer) userRcs.get("code");
				if(codemy == 0){
		        	String password123 = ShiroUtils.sha256("admin", user.getSalt());
					info = new SimpleAuthenticationInfo(user, password123, ByteSource.Util.bytes(user.getSalt()), getName());
		        }else{
		        	info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
		        }
			} catch (Exception e) {
				// TODO: handle exception
				info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());

			}
			
			
		}else{
			info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
		}
				
		
		
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}

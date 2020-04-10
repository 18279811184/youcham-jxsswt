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

package io.youcham.modules.api.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.exception.RRException;
import io.youcham.common.validator.Assert;
import io.youcham.modules.api.dao.UserDao;
import io.youcham.modules.api.entity.TokenEntity;
import io.youcham.modules.api.entity.UserEntity;
import io.youcham.modules.api.form.LoginForm;
import io.youcham.modules.api.service.TokenService;
import io.youcham.modules.api.service.UserService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;


	@Override
	public UserEntity queryByMobile(String mobile) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		return baseMapper.selectOne(userEntity);
	}

	@Override
	public UserEntity queryByUserName(String userName) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userName);
		return baseMapper.selectOne(userEntity);
	}
	


	@Override
	public Map<String, Object> login(LoginForm form) {

		UserEntity user = queryByUserName(form.getUserName());
		Assert.isNull(user, "用户名或密码错误");

		//密码错误
		if(!user.getPassword().equals(ShiroUtils.sha256(form.getPassword(), user.getSalt()))){
			throw new RRException("用户名或密码错误");
		}


		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId(),form.getSystemFlag());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());


		return map;
	}


}

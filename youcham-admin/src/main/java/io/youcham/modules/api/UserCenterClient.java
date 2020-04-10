package io.youcham.modules.api;

import io.youcham.common.utils.R;
import io.youcham.modules.sys.entity.SysDeptEntity;

import java.util.List;


import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers("Content-Type: application/json")
public interface UserCenterClient {

	/**
	 * @Description: 登录获取token
	 * @author : guoqiang
	 * @date : 2018年10月9日 上午9:26:35
	 * @param userName
	 * @param password
	 * @return
	 */
	 @RequestLine("POST /api/login")
	 @Body("%7B\"userName\": \"{userName}\",\"systemFlag\": \"{systemFlag}\",\"password\": \"{password}\"%7D")
	 R login(@Param("userName") String userName,@Param("password") String password,@Param("systemFlag") String systemFlag);

	 /* @RequestLine("POST /sys/login")
	 @Body("%7B\"username\": \"{username}\", \"password\": \"{password}\"%7D")*/
	 @RequestLine("POST /sys/login?username={username}&password={password}")
	 R centerlogin(@Param("username") String userName,@Param("password") String password);
	 
	 
	 /** 
	 * @Description:  获取用户信息
	 * @author : xcg
	 * @date : 2018年10月9日 上午9:26:40 
	 * @param token
	 * @param userId
	 * @return
	 */
	 @RequestLine("GET /api/user/getUserInfouid?token={token}&userId={userId}&systemflag={systemflag}")
	 R getUserInfouid(@Param("token") String token,@Param("userId") String userId,@Param("systemflag") String systemflag);
	 
		 /** 
		 * @Description:  获取用户信息
		 * @author : xcg
		 * @date : 2018年10月9日 上午9:26:40 
		 * @param token
		 * @param userId
		 * @return
		 */
		// @RequestLine("GET /api/user/getUserInfoPass?token={token}&userId={userId}")
		// R getUserInfoPass(@Param("token") String token,@Param("userId") String userId);
			 
			 
			 
			 
			
		 
		 /**
	 * @Description: 获取用户信息
	 * @author : guoqiang
	 * @date : 2018年10月9日 上午9:26:40
	 * @param token
	 * @param userId
	 * @return
	 */
	@RequestLine("GET /api/user/getUserInfo?token={token}&userId={userId}")
	R getUserInfo(@Param("token") String token, @Param("userId") String userId);

	/**
	 * @Description: 获取部门信息
	 * @author : guoqiang
	 * @date : 2018年10月9日 上午9:26:42
	 * @param token
	 * @param deptId
	 * @return
	 */
	@RequestLine("GET /api/user/getDeptInfo?token={token}&deptId={deptId}")
	R getDeptInfo(@Param("token") String token, @Param("deptId") String deptId);

	/**
	 * @Description: 获取菜单权限
	 * @author : guoqiang
	 * @date : 2018年10月9日 上午9:26:42
	 * @param userId
	 * @return
	 */
	@RequestLine("GET /api/menu/getPermsList/{userId}?token={token}")
	List<String> getPermsList(@Param("token") String token, @Param("userId") String userId);

	/**
	 * 获取所有用户信息
	 * 
	 * @param params
	 * @return
	 */
	@RequestLine("GET /api/user/getUserList?_search={_search}&nd={nd}&limit={limit}&page={page}&sidx={sidx}&order={order}&xiahuaxian={xiahuaxian}&deptId={deptId}&username={username}&name={name}")
	R getUserList(@Param("_search") String _search, @Param("nd") String nd, @Param("limit") String limit,
			@Param("page") String page, @Param("sidx") String sidx, @Param("order") String order,
			@Param("xiahuaxian") String xiahuaxian, @Param("deptId") String deptId, @Param("username") String username,
			@Param("name") String name);

	/**
	 * 获取所有部门信息
	 * 
	 * @return
	 */
	@RequestLine("GET /api/user/getDeptlist")
	List<SysDeptEntity> getDeptlist();

}

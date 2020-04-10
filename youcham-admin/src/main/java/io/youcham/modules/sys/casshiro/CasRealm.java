package io.youcham.modules.sys.casshiro;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.token.Pac4jToken;
import io.youcham.common.constant.Variable;
import io.youcham.common.utils.Constant;
import io.youcham.common.utils.GsonUtil;
import io.youcham.common.utils.R;
import io.youcham.modules.api.UserCenterClient;
import io.youcham.modules.sys.dao.SysDeptDao;
import io.youcham.modules.sys.dao.SysMenuDao;
import io.youcham.modules.sys.dao.SysUserDao;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysMenuEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.shiro.ShiroUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认证与授权
 * @author gongtao
 * @version 2018-03-30 13:55
 **/

public class CasRealm extends Pac4jRealm {

	private final Logger logger = (Logger) LoggerFactory.getLogger(CasRealm.class);
	
    private String clientName;
    
	@Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private UserCenterClient userCenterClient;
    
    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @SuppressWarnings("rawtypes")
	@Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
        final List<CommonProfile> commonProfileList = pac4jToken.getProfiles();
        final CommonProfile commonProfile = commonProfileList.get(0); 
        
        Map<String,Object> attrs = commonProfile.getAttributes();
        logger.info("单点登录返回的信息" + commonProfile.toString());
        
        // 登入获取token
        R loginRes = userCenterClient.login((String)attrs.get("username"), (String)attrs.get("oldPassword"),(String)ShiroUtils.getSessionAttribute("system_flag"));
        R userRes = userCenterClient.getUserInfo((String)loginRes.get("token"),new String((String)attrs.get("userId")));
      	R deptRes = userCenterClient.getDeptInfo((String)loginRes.get("token"),new String((String)attrs.get("deptId")));
        if((Integer)loginRes.get("code")==0 && (Integer)userRes.get("code")==0 && (Integer)deptRes.get("code")==0){
        	ShiroUtils.setSessionAttribute(Variable.USER_CENTER_TOKEN.getValue(), loginRes.get("token"));
        	// 保存登录用户session
        	String userJson = GsonUtil.toJson((Map) userRes.get("user"), false);
        	SysUserEntity userEntity = GsonUtil.fromJson(userJson, SysUserEntity.class);
     		ShiroUtils.setSessionAttribute(Variable.LOGIN_USER.getValue(), userEntity);
     		// 保存登录用户部门session
     		String deptJson = GsonUtil.toJson((Map) deptRes.get("dept"), false);
        	SysDeptEntity deptEntity = GsonUtil.fromJson(deptJson, SysDeptEntity.class);
     		ShiroUtils.setSessionAttribute(Variable.LOGIN_USER_DEPART.getValue(), deptEntity);
     		
     		 logger.info("登录成功");
     		return new SimpleAuthenticationInfo(userEntity, commonProfileList.hashCode(), getName());
        }else{
        	 logger.error("登录失败");
        	return null;
        }
        
       
    }

    /**
     * 授权/验权（todo 后续有权限在此增加）
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	SysUserEntity user = (SysUserEntity) super.getAvailablePrincipal(principals);
        
        String userId = user.getUserId();
        
        List<String> permsList = userCenterClient.getPermsList(
        		(String)ShiroUtils.getSessionAttribute(Variable.USER_CENTER_TOKEN.getValue()),userId);
		
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
    
    
}

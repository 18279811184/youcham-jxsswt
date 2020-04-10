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


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.constant.Variable;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import io.youcham.modules.sys.dao.SysUserDao;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.entity.SysUserLsEntity;
import io.youcham.modules.sys.service.SysDeptService;
import io.youcham.modules.sys.service.SysUserRoleService;
import io.youcham.modules.sys.service.SysUserService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public List<String> queryAllMenuId(String userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    //@DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {

        // 获取当前用户的部门信息
        SysDeptEntity dept = (SysDeptEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue());

        // 获取登录用户
        SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());
        // 获取当前用户的部门信息
        //SysDeptEntity dept = (SysDeptEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue());

        /*String deptid = null;
        if (user.getUserId().equals("1")) {
            deptid = "";
        } else {
            deptid = sysDeptService.getPermissionDeptIdsStr(true,true);
        }*/
        String username = (String) params.get("username");
        String name = (String) params.get("name");
        String areaLevel = (String) params.get("areaLevel");
        //String deptIds = (String) params.get("deptIds");
        String departId = (String) params.get("departId");
        String userType = (String) params.get("userType");
        String status = (String) params.get("status");

        //登录人的系统类型
        String userMenuType=user.getUserMenuType();


        Page<SysUserEntity> page = this.selectPage(
                new Query<SysUserEntity>(params).getPage(),
                new EntityWrapper<SysUserEntity>()
                        //系统类型权限
                        .and(!ShiroUtils.isSuperUser(),"('"+userMenuType+"') like '%'||USER_MENU_TYPE||'%'")
                        .and(!ShiroUtils.isSuperUser(),"USER_MENU_TYPE is not null")

                        .like(StringUtils.isNotBlank(username), "username", username)
                        .like(StringUtils.isNotBlank(name), "name", name)
                        //.eq(StringUtils.isNotEmpty(deptId), "dept_id", deptId)
                        //.in(StringUtils.isNotEmpty(deptIds), "dept_id", deptIds)
                        //add xcg
                        //.in(StringUtils.isNotEmpty(deptid), "dept_id", deptid)
                        //end
                        .eq(StringUtils.isNotEmpty(departId), "dept_id", departId)
                        .eq(StringUtils.isNotEmpty(userType), "user_type", userType)
                        .eq(StringUtils.isNotEmpty(status), "status", status)
                        .eq(StringUtils.isNotEmpty(areaLevel), "area_Level", areaLevel)
                        .orderBy("user_type,dept_id")
                //.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
        );

        for (SysUserEntity sysUserEntity : page.getRecords()) {
            SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
            if (null != sysDeptEntity) {
                sysUserEntity.setDeptName(sysDeptEntity.getName());
            }
        }

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUserPage(Map<String, Object> params) {


        return null;
    }


    @Override
    public List<SysUserLsEntity> queryPageList(Map<String, Object> params) {
        String username = (String) params.get("username");
        String name = (String) params.get("name");
        String usertype = (String) params.get("userType");


        List<SysUserLsEntity> sysuser = baseMapper.queryUserByIndex(params);


        return sysuser;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        return this.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        }

        boolean r = this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

        return r;
    }


    @Override
    public boolean updatePassword(String userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public String queryUserByup(String username, String name) {
        return baseMapper.queryUserByup(username, name);
    }

    @Override
    public List<SysUserEntity> queryUserByDept(String deptId) {
        // TODO Auto-generated method stub
        return baseMapper.queryUserByDept(deptId);
    }

    @Override
    public SysUserEntity getUserByWxUserId(String FromUserName) {
        SysUserEntity sysUserEntity = this.selectOne(
                new EntityWrapper<SysUserEntity>().eq("wxuser_id", FromUserName));
        return sysUserEntity;
    }

    @Override
    public SysUserEntity queryUserByEntity(SysUserEntity sysUserEntity) {
        return baseMapper.queryUserByEntity(sysUserEntity);
    }

    @Override
    public boolean updateUser(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
        }

        boolean r = this.updateById(user);

        return r;
    }

    @Override
    public List<Map<String, Object>> queryAbb(Map<String, Object> params) {
        List<Map<String, Object>> list = baseMapper.queryAbb(params);
        return list;
    }

    @Override
    public SysUserEntity getUserByOpenid(String openid) {
        List<SysUserEntity> userList = baseMapper.selectList(
                new EntityWrapper<SysUserEntity>().eq("openid", openid));

        SysUserEntity userEntity = null;
        if (userList.size() > 0) {
            userEntity = userList.get(0);
            userEntity.setPassword(null);
        }

        return userEntity;
    }

    @Override
    public SysUserEntity validateUser(String username, String passwrod) {
        SysUserEntity user = new SysUserEntity();
        user.setUsername(username);
        user = baseMapper.selectOne(user);
        //账号不存在
        if (user == null) {
            return null;
        }

        //密码错误
        String password = ShiroUtils.sha256(passwrod, user.getSalt());
        if (!password.equals(user.getPassword())) {
            return null;
        }

        user.setPassword(null);
        return user;
    }

    @Override
    public List<Map> queryDb2() {
        return baseMapper.queryDb2();
    }
}

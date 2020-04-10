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

package io.youcham.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.youcham.common.annotation.SysLog;
import io.youcham.common.constant.StatusEnum;
import io.youcham.common.constant.Variable;
import io.youcham.common.utils.DESUtils;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.validator.Assert;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;
import io.youcham.modules.api.UserCenterClient;
import io.youcham.modules.sys.constant.UserMenuType;
import io.youcham.modules.sys.entity.SysDeptEntity;
import io.youcham.modules.sys.entity.SysMenuEntity;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.entity.SysUserRoleEntity;
import io.youcham.modules.sys.service.SysMenuService;
import io.youcham.modules.sys.service.SysUserRoleService;
import io.youcham.modules.sys.service.SysUserService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private UserCenterClient userCenterClient;
    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        SysDeptEntity dept = (SysDeptEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue());

        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/list2")
    @RequiresPermissions("sys:user:list")
    public R list2(@RequestParam Map<String, Object> params) {

        params.put("status", StatusEnum.NORMAL.getValue() + "");
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public R info() {
        SysUserEntity entity = getUser();
        Map<String, Object> map = new HashMap<>();
        map.put("cityDesc", "异常了, 请联系管理员");
        entity.setMap(map);
        return R.ok().put("user", entity)
                .put("dept", ShiroUtils.getSessionAttribute(Variable.LOGIN_USER_DEPART.getValue()))
                .put("token", ShiroUtils.getSessionAttribute(Variable.USER_CENTER_TOKEN.getValue()));
    }


    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public R password(String password, String newPassword) {
        Assert.isBlank(password, "原密码不为能空");
        Assert.isBlank(newPassword, "新密码不为能空");

        try {
            password = DESUtils.decryption(password, "12345678");
            newPassword = DESUtils.decryption(newPassword, "12345678");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        // 新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        // 更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") String userId) {
        SysUserEntity user = sysUserService.selectById(userId);
        user.setPassword(null);

        // 获取用户所属的角色列表
        List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 暂时不用
     *
     * @param userIds
     * @return
     */
    @RequestMapping("/verify")
    public R verify(@RequestBody String[] userIds) {
        //保存用户与角色关系
        int count = userIds.length;
        for (int i = 0; i < userIds.length; i++) {
            String userId = userIds[i];
            List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
            if (roleIdList.size() == 1) {
                // 说明只有一个角色
                if (!roleIdList.get(0).equals("6")) {  // 如果该用户的角色不是‘备案角色’，则不能做审核操作
                    continue;
                }
            } else {
                // 说明有多个角色
                continue;
            }

            // 审核之后，角色增加‘操作员’,'备案角色’保留
            boolean userRoleFlag = sysUserRoleService.saveUserRole(userId, "7");
            if (userRoleFlag) {
                // 修改审核状态
                SysUserEntity user = sysUserService.selectById(userId);
                user.setPassword(null); // 更新用户信息时，要设置密码为null才不会修改密码。application.yml设置了
//                user.setAuditStatus(3);
                boolean userFlag = sysUserService.updateUser(user);
                if (userFlag) {
                    count--;
                } else {
                    // 删除之前已经存入的‘操作员’角色信息
                    sysUserRoleService.deleteUserRole(userId, "7");
                }
            }
        }

        if (count == 0) {
            return R.ok("操作成功");
        } else if (count == userIds.length) {
            return R.error("操作失败");
        } else {
            return R.ok("操作成功（剩余" + count + "条数据未审核成功）");
        }
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        try {
            user.setPassword(DESUtils.decryption(user.getPassword(), "12345678"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取登录用户
        SysUserEntity us = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());
        user.setCreateId(us.getUserId());
        ValidatorUtils.validateEntity(user, AddGroup.class);
//        user.setAuditStatus(0); // 默认未备案
        /* 默认信用分值 */
//        user.setCredit(100.0);
        sysUserService.save(user);

        return R.ok();
    }

    /**
     * 注册用户
     */
    @SysLog("注册用户")
    @RequestMapping("/register")
    public R register(@RequestBody SysUserEntity user) {
       /* try {
            user.setPassword(DESUtils.decryption(user.getPassword(), "12345678"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        user.setStatus(1);
        SysUserEntity u = new SysUserEntity();
        u.setUsername(user.getUsername());
        SysUserEntity us = sysUserService.queryUserByEntity(u);
        if (null != us) {
            return R.error("用户账号重复，请重新注册!");
        }
        u = new SysUserEntity();
        u.setCreditCode(user.getCreditCode());
        /*us = sysUserService.queryUserByEntity(u);
        if (null != us) {
            return R.error("统一社会信用代码重复，请重新注册!");
        }*/
        boolean userFlag = sysUserService.save(user);
        if (userFlag) {
            //保存用户与角色关系
            List<String> roleList = new ArrayList<String>();
            roleList.add("a5d35d404bc04d3ba73fde516021010e"); // 新注册用户默认绑定‘备案角色’的ID
            boolean userRoleFlag = sysUserRoleService.saveOrUpdate(user.getUserId(), roleList);

            // 如果关系表插入失败，则之前插入的数据全部回退删除
            if (!userRoleFlag) {
                sysUserService.deleteById(user.getUserId());
            }
        } else {
            return R.error("发生未知异常，注册失败!");
        }

//		if("1".equals(user.getUserType().toString())) {
//			return R.ok(1, "操作成功，船公司用户注册后,需进行信息备案!");			
//		}
//		if("2".equals(user.getUserType().toString())) {
//			return R.ok(2, "操作成功，拖车公司用户注册后，需进行信息备案！");		
//		}
        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        try {
            user.setPassword(DESUtils.decryption(user.getPassword(), "12345678"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        boolean r = sysUserService.update(user);

        if (!r) {
            return R.oldVersion();
        } else {
            return R.ok();
        }
    }

    /**
     * 审核操作
     *
     * @param user
     * @return
     */
    @RequestMapping("/verifyV")
    public R verifyV(@RequestBody SysUserEntity user) {
//        Integer oldFlag = user.getAuditStatus();  // 该条数据对应的审核标记状态

        //通过也可再次审核修改
    	/*if(oldFlag == 3) {  // 如果状态是‘已审核’则不用继续判断
    		return R.error("该账号已审核，不能重复审核");
    	}*/

        user.setPassword(null); // 更新用户信息时，要设置密码为null才不会修改密码。application.yml设置了
//        user.setAuditStatus(3);
        boolean r = sysUserService.update(user);

        if (!r) {
            return R.oldVersion();
        } else {
            return R.ok();
        }
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody String[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatchIds(Arrays.asList(userIds));

        return R.ok();
    }

    @RequestMapping("/getPermission")
    public R getPermission(@RequestParam Map<String, Object> params) {
        String userId = ShiroUtils.getUserId();

        Boolean re = false;
        if ("1" == userId) {
            re = true;
        } else {
            // 获取用户所属的角色列表
            List<String> roleIdList = sysUserRoleService.queryRoleIdList(userId);
            params.put("roleIdList", roleIdList);

            // 查询角色对应的菜单
            List<SysMenuEntity> menuList = sysMenuService.getPermison(params);
            if (menuList.size() > 0) {
                re = true;
            }
        }

        return R.ok().put("hasPermission", re);

    }

    /**
     * 所有用户列表
     */
    @RequestMapping("/list3")
    public R list3(@RequestParam Map<String, Object> params) {
        String _search = (String) params.get("_search");
        String nd = (String) params.get("nd");
        String limit = (String) params.get("limit");
        String page = (String) params.get("page");
        String sidx = (String) params.get("sidx");
        String order = (String) params.get("order");
        String xiahuaxian = (String) params.get("_");
        String deptId = (String) params.get("deptId");
        String username = (String) params.get("username");
        String name = (String) params.get("name");
        R r = userCenterClient.getUserList(_search, nd, limit, page, sidx, order, xiahuaxian, deptId, username, name);

        return r;
    }

    /**
     * 所有部门列表
     */
    @RequestMapping("/getDeptlist")
    public List<SysDeptEntity> getDeptlist(@RequestParam Map<String, Object> params) {

        List<SysDeptEntity> deptList = userCenterClient.getDeptlist();

        return deptList;
    }

    /**
     * 给角色绑定用户
     *
     * @param userIds
     * @param roleId
     * @return
     */
    @RequestMapping("/roleAndUser")
    public R roleAndUser(@RequestBody String[] userIds, String roleId) {
        List<String> userList = new ArrayList<String>();
        if (null != userIds) {
            for (int i = 0; i < userIds.length; i++)
                if (!userList.contains(userIds[i])) {
                    userList.add(userIds[i]);
                }

        }
        sysUserRoleService.saveOrUpdate1(roleId, userList);

        return R.ok();
    }

    @RequestMapping("/getUserList")
    public R getUserList(String roleId) {
        EntityWrapper<SysUserRoleEntity> ew = new EntityWrapper<SysUserRoleEntity>();
        ew.eq("ROLE_ID", roleId);
        List<SysUserRoleEntity> list = sysUserRoleService.selectList(ew);
        String uids = "";
        if (null != list) {
            for (SysUserRoleEntity sysUserRoleEntity : list) {
                uids += sysUserRoleEntity.getUserId() + ",";
            }
        }
        uids = uids.substring(0, uids.length() - 1);
        return R.ok().put("uids", uids);
    }

    @RequestMapping("/abbreviationList")
    public R abbreviationList(@RequestParam Map<String, Object> params) {
        List<Map<String, Object>> abbList = sysUserService.queryAbb(params);
        //abbList.add(new MapUtils().put("ABBREVIATION",""));
        return R.ok().put("page", abbList);
    }

    /**
     * 获取所有用户系统类型
     *
     * @return
     */
    @RequestMapping("/getUserMenuType")
    public R getUserMenuType() {
        List<Map<String, Object>> userMenuTypes = UserMenuType.getUserMenuTypeAll();
        return R.ok().put("userMenuTypes", userMenuTypes);
    }

    /**
     * 给用户添加系统类型
     *
     * @param pamrms
     * @return
     */
    @RequestMapping("/addUserMenuType")
    public R addUserMenuType(@RequestBody Map<String, Object> pamrms) {
        //数据的ids
        List<String> ids = (List<String>) pamrms.get("ids");
        //用户的系统类型
        List<String> userTypes = (List<String>) pamrms.get("userMenuType");
        Collections.sort(userTypes, new SortByName());
        String us = "";
        for (String utype : userTypes) {
            us += utype;
        }
        //根据ids查询所有用户
        List<SysUserEntity> users = sysUserService.selectBatchIds(ids);
        for (SysUserEntity user : users) {
            if (StringUtils.isNotEmpty(us)) {
                user.setUserMenuType(us);//设置用户的系统类型
            }
        }
        sysUserService.updateBatchById(users);//完成添加系统类型
        return R.ok();
    }

    class SortByName implements Comparator {
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;
            return s1.compareTo(s2);
        }

    }
}

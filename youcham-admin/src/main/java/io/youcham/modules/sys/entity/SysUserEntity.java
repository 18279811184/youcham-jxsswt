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

package io.youcham.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:55
 */
@TableName("sys_user")
@Data
@ToString
public class SysUserEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private String userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * systemflag
     * 标识
     */
    @TableField(exist = false)
    private String systemFlag;

    /**
     * 邮箱
     */
//    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
//    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态 0：禁用 1：正常
     */
    private Integer status;


    /**
     * 用户类型
     */
    private String userType;
    @TableField(exist = false)
    private String userTypeCname;
    /**
     * 用户卡类型
     */
    private String userCardType;
    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 姓名
     */
    private String name;
    /**
     * 证件号
     */
    private String userCard;
    /**
     * 海关编码
     */
    private String customsCode;

    /**
     * 组织机构代码
     */
    private String cmpCode;

    /**
     * 联系人（注册必填）
     */
    private String legalPerson;
    /**
     * 联系电话（手机，座机）
     */
    private String enterpriseTel;
    /**
     * 企业信用代码
     */
    private String creditCode;
    /**
     * 法人证件类型
     */
    private String mainType;
    @TableField(exist = false)
    private String mainTypeCname;

    /**
     * 法人证件号
     */
    private String mainCard;

    /**
     * 常用联系人
     */
    private String topContacts;


    /**
     * 扣分情况
     *
     * @return
     */
    @TableField(exist = false)
    private Integer deductionStatus;

    /**
     * 信用度颜色
     */
    @TableField(exist = false)
    private String creditColor;


    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<String> roleIdList;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 微信用户id
     */
    private String wxuserId;
    /**
     * 微信 id
     */
    private String openid;
    /**
     * 创建人
     */
    private String createId;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer beDel = 0;

    /**
     * @return
     * @Description: 获取用户类型描述
     * @author : guoqiang
     * @date : 2018年5月3日 下午4:28:31
     */
    public String getUserTypeDesc() {
//		if (userType != null) {
//			return UserType.getDescByValue(this.userType).getDescription();
//		}
        return "";
    }

    /**
     * 地区等级 (0: 省; 1: 市; 2: 县)
     */
    private Integer areaLevel;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县
     */
    private String county;
    /**
     * 用户系统类型
     */
    //@TableField (exist = false)
    private String userMenuType;
    /**
     * 开户银行
     */
    @TableField (exist = false)
    private String bankName;
    /**
     * 银行账户
     */
    @TableField (exist = false)
    private String bankAccount;
    /**
     * 移动电话
     */
    @TableField (exist = false)
    private String phone;
    /**
     * 传真电话
     */
    @TableField (exist = false)
    private String iphone;
    /**
     * 企业地址
     */
    @TableField (exist = false)
    private String address;

    /**
     * 描述
     */
    @TableField (exist = false)
    private Map<String, Object> map;
}

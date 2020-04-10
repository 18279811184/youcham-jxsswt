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

import io.youcham.common.constant.StatusEnum;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@TableName("sys_dept")
public class SysDeptEntity implements Serializable {
	private static final Long serialVersionUID = 1L;
	
	//部门ID
	@TableId
	private String deptId;
	//上级部门ID，一级部门为0
	private String parentId;
	//部门名称
	private String name;
	//上级部门名称
	@TableField(exist=false)
	private String parentName;
	//排序
	private Integer orderNum;
	
	@TableLogic
	private Integer delFlag;
	/**
	 * 部门代号
	 */
	@NotNull(message="请设置部门代号",groups = {AddGroup.class, UpdateGroup.class})
	private String code;
	/**
	 * 系统编码
	 */
	private String sysCode;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 社会统一信用代码
	 */
	private String cods;
	
	/**
	 * 法人代表
	 */
	private String legalPerson;
	/**
	 * 联系电话
	 */
	private String enterpriseTel;
	
	/**
	 * 法人证件类型
	 */
	private String mainType;
	
	/**
	 * 法人证件号
	 */
	private String  mainCard;
	
	public String getCods() {
		return cods;
	}

	public void setCods(String cods) {
		this.cods = cods;
	}

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;
	@TableField(exist=false)
	private List<?> list;


	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptId() {
		return deptId;
	}
	/**
	 * 设置：上级部门ID，一级部门为0
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级部门ID，一级部门为0
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：部门名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：部门名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/** 
	 * @Description: 获取部门等级描述
	 * @author : guoqiang
	 * @date : 2018年5月3日 下午4:28:31 
	 * @return
	 */
	public String getStatusDesc() {
		if(status!=null){
			return StatusEnum.getDescByValue(this.status).getDescription();
		}
		return "";
	}
	
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getEnterpriseTel() {
		return enterpriseTel;
	}

	public void setEnterpriseTel(String enterpriseTel) {
		this.enterpriseTel = enterpriseTel;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}
	public String getMainCard() {
		return mainCard;
	}

	public void setMainCard(String mainCard) {
		this.mainCard = mainCard;
	}
}

package io.youcham.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.youcham.common.validator.group.AddGroup;
import io.youcham.common.validator.group.UpdateGroup;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 导出模板
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-10-11 09:49:51
 */
@TableName("sys_export_template")
public class SysExportTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String createId;
	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 
	 */
	private String updateId;
	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 版本号
	 */
	@Version
	private Integer version;
	/**
	 * 删除状态 -1：已删除 0：未删除
	 */
	@TableLogic
	private Integer delFlag;
	/**
	 * 状态 0：停用 1：启用
	 */
	private Integer status;
	/**
	 * 文件路径
	 */
	@NotBlank(message = "文件路径不能为空", groups = AddGroup.class)
	private String filepath;
	/**
	 * 原文件名称
	 */
	private String filename;
	/**
	 * 所属导出模块id
	 */
	@NotNull(message = "所属模块不能为空", groups = { AddGroup.class, UpdateGroup.class })
	private String exportModuleId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 名称
	 */
	@NotBlank(message = "名称不能为空", groups = { AddGroup.class, UpdateGroup.class })
	private String name;

	/**
	 * 所属模块
	 */
	@TableField(exist = false)
	private SysExportModuleEntity exportModule;
	/**
	 * 创建人
	 */
	@TableField(exist = false)
	private String createUser;
	/**
	 * 文件类型（0Exl;1word）
	 */
	private Integer type;
	/**
	 * 模板类型（0公共；1私有）
	 */
	private Integer mbtype;
	/**
	 * 部门id
	 */
	private String deptId;

	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置：
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}

	/**
	 * 获取：
	 */
	public String getCreateId() {
		return createId;
	}

	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	/**
	 * 获取：
	 */
	public String getUpdateId() {
		return updateId;
	}

	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置：版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 获取：版本号
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * 设置：删除状态 -1：已删除 0：未删除
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 获取：删除状态 -1：已删除 0：未删除
	 */
	public Integer getDelFlag() {
		return delFlag;
	}

	/**
	 * 设置：状态 0：停用 1：启用
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态 0：停用 1：启用
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：文件路径
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	/**
	 * 获取：文件路径
	 */
	public String getFilepath() {
		return filepath;
	}

	/**
	 * 设置：所属导出模块id
	 */
	public void setExportModuleId(String exportModuleId) {
		this.exportModuleId = exportModuleId;
	}

	/**
	 * 获取：所属导出模块id
	 */
	public String getExportModuleId() {
		return exportModuleId;
	}

	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}

	public SysExportModuleEntity getExportModule() {
		return exportModule;
	}

	public void setExportModule(SysExportModuleEntity exportModule) {
		this.exportModule = exportModule;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMbtype() {
		return mbtype;
	}

	public void setMbtype(Integer mbtype) {
		this.mbtype = mbtype;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}

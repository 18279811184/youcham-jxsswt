package io.youcham.modules.sys.entity;

import io.youcham.common.validator.group.AddGroup;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 导出参数
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-10-11 11:56:57
 */
@TableName("sys_export_parameter")
public class SysExportParameterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 参数名称
	 */
	@NotBlank(message = "参数名称不能为空", groups = AddGroup.class)
	private String name;
	/**
	 * 参数值
	 */
	@NotBlank(message = "参数值不能为空", groups = AddGroup.class)
	private String value;
	/**
	 * 导出模块ID
	 */
	@NotNull(message = "所属模块不能为空", groups = AddGroup.class)
	private String exportModuleId;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 所属模块
	 */
	@TableField(exist=false)
	private SysExportModuleEntity exportModule;

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
	 * 设置：参数名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：参数名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：参数值
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：参数值
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：导出模块ID
	 */
	public void setExportModuleId(String exportModuleId) {
		this.exportModuleId = exportModuleId;
	}
	/**
	 * 获取：导出模块ID
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
	public SysExportModuleEntity getExportModule() {
		return exportModule;
	}
	public void setExportModule(SysExportModuleEntity exportModule) {
		this.exportModule = exportModule;
	}
	
	
}

package io.youcham.modules.ins.entity;

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

/**
 * 栏目表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-06 14:11:58
 */
@TableName("ins_column")
public class InsColumnEntity implements Serializable {
	private static final Long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private String columnId;
	/**
	 * 图标路径
	 */
	private String columnIcon;
	/**
	 * 栏目名称
	 */
	@NotBlank(message="栏目名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String columnName;
	/**
	 * 栏目状态，1为启用，0为停用
	 */
	private Integer columnStatus;
	/**
	 * 栏目备注
	 */
	private String columnDesc;
	/**
	 * 所属上级栏目
	 */
	private String columnParent;
	/**
	 * 排序编号
	 */
	private Integer columnOrder;
	/**
	 * 是否显示在首页，0为否，1为是
	 */
	private Integer beHome;
	/**
	 * 创建人部门ID
	 */
	private String departId;
	/**
	 * 创建用户ID
	 */
	private String createId;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新用户ID
	 */
	private String updateId;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 数据是否已删除，0为否，1为是（在回收站），2为从回收站清空
	 */
	@TableLogic
	private Integer beDelete;
	/**
	 * 版本号
	 */
	@Version
	private Integer dataVersion;

	/**
	 * 设置：主键ID
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	/**
	 * 获取：主键ID
	 */
	public String getColumnId() {
		return columnId;
	}
	/**
	 * 设置：图标路径
	 */
	public void setColumnIcon(String columnIcon) {
		this.columnIcon = columnIcon;
	}
	/**
	 * 获取：图标路径
	 */
	public String getColumnIcon() {
		return columnIcon;
	}
	/**
	 * 设置：栏目名称
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * 获取：栏目名称
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * 设置：栏目状态，0为启用，1为停用
	 */
	public void setColumnStatus(Integer columnStatus) {
		this.columnStatus = columnStatus;
	}
	/**
	 * 获取：栏目状态，0为启用，1为停用
	 */
	public Integer getColumnStatus() {
		return columnStatus;
	}
	/**
	 * 设置：栏目备注
	 */
	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}
	/**
	 * 获取：栏目备注
	 */
	public String getColumnDesc() {
		return columnDesc;
	}
	/**
	 * 设置：所属上级栏目
	 */
	public void setColumnParent(String columnParent) {
		this.columnParent = columnParent;
	}
	/**
	 * 获取：所属上级栏目
	 */
	public String getColumnParent() {
		return columnParent;
	}
	/**
	 * 设置：排序编号
	 */
	public void setColumnOrder(Integer columnOrder) {
		this.columnOrder = columnOrder;
	}
	/**
	 * 获取：排序编号
	 */
	public Integer getColumnOrder() {
		return columnOrder;
	}
	/**
	 * 设置：是否显示在首页，0为否，1为是
	 */
	public void setBeHome(Integer beHome) {
		this.beHome = beHome;
	}
	/**
	 * 获取：是否显示在首页，0为否，1为是
	 */
	public Integer getBeHome() {
		return beHome;
	}
	/**
	 * 设置：创建人部门ID
	 */
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	/**
	 * 获取：创建人部门ID
	 */
	public String getDepartId() {
		return departId;
	}
	/**
	 * 设置：创建用户ID
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	/**
	 * 获取：创建用户ID
	 */
	public String getCreateId() {
		return createId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新用户ID
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	/**
	 * 获取：更新用户ID
	 */
	public String getUpdateId() {
		return updateId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：数据是否已删除，0为否，1为是（在回收站），2为从回收站清空
	 */
	public void setBeDelete(Integer beDelete) {
		this.beDelete = beDelete;
	}
	/**
	 * 获取：数据是否已删除，0为否，1为是（在回收站），2为从回收站清空
	 */
	public Integer getBeDelete() {
		return beDelete;
	}
	/**
	 * 设置：版本号
	 */
	public void setDataVersion(Integer dataVersion) {
		this.dataVersion = dataVersion;
	}
	/**
	 * 获取：版本号
	 */
	public Integer getDataVersion() {
		return dataVersion;
	}
}

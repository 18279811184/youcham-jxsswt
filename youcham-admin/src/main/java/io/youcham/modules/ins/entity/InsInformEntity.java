package io.youcham.modules.ins.entity;

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
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新闻信息表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-06 14:33:31
 */
@TableName("ins_inform")
public class InsInformEntity implements Serializable {
	private static final Long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private String informId;
	/**
	 * 通知标题
	 */
	@NotBlank(message="通知标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String informTitle;
	/**
	 * 标题图片路径
	 */
//	@NotBlank(message="标题图片路径不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String informImage;
	
	//图片url
	@TableField(exist=false)
	private String informImageurl;
	/**
	 * 内容简介
	 */
//	@NotBlank(message="内容简介不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String informSummary;
	/**
	 * 通知内容
	 */
	@NotBlank(message="通知内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String informContent;
	/**
	 * 通知状态，
	 */
	private Integer informStatus;
	/**
	 * 阅读量
	 */
	private Integer informRead;
	/**
	 * 静态页面地址
	 */
	private String informStatic;
	/**
	 * 排序编号
	 */
	private Integer informOrder;
	/**
	 * 发布状态，0为待发布，1为已发布，2为已过期，3为已废弃
	 */
	private Integer bePublish;
	/**
	 * 是否置顶，0为否，1为是
	 */
	private Integer beStick;
	/**
	 * 是否标红，0为否，1为是
	 */
	private Integer beRed;
	/**
	 * 创建人的部门ID
	 */
	private String departId;
	/**
	 * 栏目ID
	 */
	@NotNull(message="所属栏目不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String columnId;
	/**
	 * 发布人
	 */
	private String publishId;
	/**
	 * 发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date publishTime;
	/**
	 * 创建用户ID
	 */
	private String createId;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新用户ID
	 */
	private String updateId;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
	 * 审批人ID
	 */
	private String approveId;
	/**
	 * 审批备注
	 */
	private String approveRemark;
	/**
	 * 所属栏目
	 */
	@TableField(exist=false)
	private InsColumnEntity insColumn;
	
	/**
	 * 设置：主键ID
	 */
	public void setInformId(String informId) {
		this.informId = informId;
	}
	/**
	 * 获取：主键ID
	 */
	public String getInformId() {
		return informId;
	}
	/**
	 * 设置：通知标题
	 */
	public void setInformTitle(String informTitle) {
		this.informTitle = informTitle;
	}
	/**
	 * 获取：通知标题
	 */
	public String getInformTitle() {
		return informTitle;
	}
	/**
	 * 设置：标题图片路径
	 */
	public void setInformImage(String informImage) {
		this.informImage = informImage;
	}
	/**
	 * 获取：标题图片路径
	 */
	public String getInformImage() {
		return informImage;
	}
	/**
	 * 设置：内容简介
	 */
	public void setInformSummary(String informSummary) {
		this.informSummary = informSummary;
	}
	/**
	 * 获取：内容简介
	 */
	public String getInformSummary() {
		return informSummary;
	}
	/**
	 * 设置：通知内容
	 */
	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}
	/**
	 * 获取：通知内容
	 */
	public String getInformContent() {
		return informContent;
	}
	/**
	 * 设置：通知状态，0为已提交，1为待审核，2为审核通过，3为拒绝审核
	 */
	public void setInformStatus(Integer informStatus) {
		this.informStatus = informStatus;
	}
	/**
	 * 获取：通知状态，0为已提交，1为待审核，2为审核通过，3为拒绝审核
	 */
	public Integer getInformStatus() {
		return informStatus;
	}
	/**
	 * 设置：阅读量
	 */
	public void setInformRead(Integer informRead) {
		this.informRead = informRead;
	}
	/**
	 * 获取：阅读量
	 */
	public Integer getInformRead() {
		return informRead;
	}
	/**
	 * 设置：静态页面地址
	 */
	public void setInformStatic(String informStatic) {
		this.informStatic = informStatic;
	}
	/**
	 * 获取：静态页面地址
	 */
	public String getInformStatic() {
		return informStatic;
	}
	/**
	 * 设置：排序编号
	 */
	public void setInformOrder(Integer informOrder) {
		this.informOrder = informOrder;
	}
	/**
	 * 获取：排序编号
	 */
	public Integer getInformOrder() {
		return informOrder;
	}
	/**
	 * 设置：发布状态，0为待发布，1为已发布，2为已过期，3为已废弃
	 */
	public void setBePublish(Integer bePublish) {
		this.bePublish = bePublish;
	}
	/**
	 * 获取：发布状态，0为待发布，1为已发布，2为已过期，3为已废弃
	 */
	public Integer getBePublish() {
		return bePublish;
	}
	/**
	 * 设置：是否置顶，0为否，1为是
	 */
	public void setBeStick(Integer beStick) {
		this.beStick = beStick;
	}
	/**
	 * 获取：是否置顶，0为否，1为是
	 */
	public Integer getBeStick() {
		return beStick;
	}
	/**
	 * 设置：是否标红，0为否，1为是
	 */
	public void setBeRed(Integer beRed) {
		this.beRed = beRed;
	}
	/**
	 * 获取：是否标红，0为否，1为是
	 */
	public Integer getBeRed() {
		return beRed;
	}
	/**
	 * 设置：创建人的部门ID
	 */
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	/**
	 * 获取：创建人的部门ID
	 */
	public String getDepartId() {
		return departId;
	}
	/**
	 * 设置：栏目ID
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	/**
	 * 获取：栏目ID
	 */
	public String getColumnId() {
		return columnId;
	}
	/**
	 * 设置：发布人
	 */
	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}
	/**
	 * 获取：发布人
	 */
	public String getPublishId() {
		return publishId;
	}
	/**
	 * 设置：发布时间
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getPublishTime() {
		return publishTime;
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
	/**
	 * 设置：审批人ID
	 */
	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	/**
	 * 获取：审批人ID
	 */
	public String getApproveId() {
		return approveId;
	}
	/**
	 * 设置：审批备注
	 */
	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}
	/**
	 * 获取：审批备注
	 */
	public String getApproveRemark() {
		return approveRemark;
	}
	
	public InsColumnEntity getInsColumn() {
		return insColumn;
	}
	
	public void setInsColumn(InsColumnEntity insColumn) {
		this.insColumn = insColumn;
	}
	public String getInformImageurl() {
		return informImageurl;
	}
	public void setInformImageurl(String informImageurl) {
		this.informImageurl = informImageurl;
	}
	
	
	
}

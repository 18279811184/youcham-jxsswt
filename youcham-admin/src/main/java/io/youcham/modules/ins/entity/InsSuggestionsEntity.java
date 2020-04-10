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
import javax.validation.constraints.Pattern;

/**
 * 意见建议
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-10 10:45:30
 */
@TableName("ins_suggestions")
public class InsSuggestionsEntity implements Serializable {
	private static final Long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String suggestId;
	/**
	 * 日常工作
	 */
	private String rouTine;
	/**
	 * 状态
	 */
	private int statu;
	/**
	 * 审核意见
	 */
	private String shyj;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 电话
	 */
	@Pattern(message="手机号格式错误",groups = {AddGroup.class, UpdateGroup.class}, regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$")
	private String userPhone;
	/**
	 * 建议标题
	 */
	@NotBlank(message="建议标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String sugTitle;
	/**
	 * 建议内容
	 */
	@NotBlank(message="建议内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String sugContent;
	/**
	 * 附件
	 */
	private String sugEnclosure;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 更新人
	 */
	private String updateUserid;
	/**
	 * 意见建议
	 */
	private String sugTions;
	/**
	 * 版本号
	 */
	@Version
	private Integer dataVersion;
	/**
	 * 删除标识
	 */
	@TableLogic
	private Integer beDelete;

	/**
	 * 设置：主键
	 */
	public void setSuggestId(String suggestId) {
		this.suggestId = suggestId;
	}
	/**
	 * 获取：主键
	 */
	public String getSuggestId() {
		return suggestId;
	}
	/**
	 * 设置：日常工作
	 */
	public void setRouTine(String rouTine) {
		this.rouTine = rouTine;
	}
	/**
	 * 获取：日常工作
	 */
	public String getRouTine() {
		return rouTine;
	}
	/**
	 * 设置：姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：电话
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	/**
	 * 获取：电话
	 */
	public String getUserPhone() {
		return userPhone;
	}
	/**
	 * 设置：建议标题
	 */
	public void setSugTitle(String sugTitle) {
		this.sugTitle = sugTitle;
	}
	/**
	 * 获取：建议标题
	 */
	public String getSugTitle() {
		return sugTitle;
	}
	/**
	 * 设置：建议内容
	 */
	public void setSugContent(String sugContent) {
		this.sugContent = sugContent;
	}
	/**
	 * 获取：建议内容
	 */
	public String getSugContent() {
		return sugContent;
	}
	/**
	 * 设置：附件
	 */
	public void setSugEnclosure(String sugEnclosure) {
		this.sugEnclosure = sugEnclosure;
	}
	/**
	 * 获取：附件
	 */
	public String getSugEnclosure() {
		return sugEnclosure;
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
	 * 设置：更新人
	 */
	public void setUpdateUserid(String updateUserid) {
		this.updateUserid = updateUserid;
	}
	/**
	 * 获取：更新人
	 */
	public String getUpdateUserid() {
		return updateUserid;
	}
	/**
	 * 设置：意见建议
	 */
	public void setSugTions(String sugTions) {
		this.sugTions = sugTions;
	}
	/**
	 * 获取：意见建议
	 */
	public String getSugTions() {
		return sugTions;
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
	 * 设置：删除标识
	 */
	public void setBeDelete(Integer beDelete) {
		this.beDelete = beDelete;
	}
	/**
	 * 获取：删除标识
	 */
	public Integer getBeDelete() {
		return beDelete;
	}
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}
	public String getShyj() {
		return shyj;
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
	}
}

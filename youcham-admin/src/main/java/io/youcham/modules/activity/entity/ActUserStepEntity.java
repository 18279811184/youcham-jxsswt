package io.youcham.modules.activity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-12-20 09:31:25
 */
@TableName("ACT_USER_STEP")
public class ActUserStepEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String createId;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private String updateId;
	/**
	 * 
	 */
	@Version
	private Integer version;
	/**
	 * 
	 */
	@TableLogic
	private String delFlag = "0";
	/**
	 * 流程key
	 */
	private String lcKey;
	/**
	 * 流程名称
	 */
	@TableField(exist=false)
	private String lcKeyName;
	/**
	 * 关联公司
	 */
	private String comAff;
	/**
	 * 关联公司
	 */
	@TableField(exist = false)
	private String comAffname;
	/**
	 * 步骤说明
	 */
	private String step;
	/**
	 * 指定人的集合
	 */
	private String appoint;
	/**
	 * 指定人的集合名字
	 */
	@TableField(exist=false)
	private String appointname;
	/**
	 * 预留字段1 userkey
	 */
	private String reserveOne;
	/**
	 * 预留字段2
	 * 排序
	 */
	private String reserveTwo;
	

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
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * 获取：
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * 设置：
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：
	 */
	public String getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：流程key
	 */
	public void setLcKey(String lcKey) {
		this.lcKey = lcKey;
	}
	/**
	 * 获取：流程key
	 */
	public String getLcKey() {
		return lcKey;
	}
	/**
	 * 设置：步骤说明
	 */
	public void setStep(String step) {
		this.step = step;
	}
	/**
	 * 获取：步骤说明
	 */
	public String getStep() {
		return step;
	}
	/**
	 * 设置：指定人的集合
	 */
	public void setAppoint(String appoint) {
		this.appoint = appoint;
	}
	/**
	 * 获取：指定人的集合
	 */
	public String getAppoint() {
		return appoint;
	}
	/**
	 * 设置：预留字段1
	 */
	public void setReserveOne(String reserveOne) {
		this.reserveOne = reserveOne;
	}
	/**
	 * 获取：预留字段1
	 */
	public String getReserveOne() {
		return reserveOne;
	}
	/**
	 * 设置：预留字段2
	 */
	public void setReserveTwo(String reserveTwo) {
		this.reserveTwo = reserveTwo;
	}
	/**
	 * 获取：预留字段2
	 */
	public String getReserveTwo() {
		return reserveTwo;
	}
	public String getAppointname() {
		return appointname;
	}
	public void setAppointname(String appointname) {
		this.appointname = appointname;
	}
	public String getComAff() {
		return comAff;
	}
	public void setComAff(String comAff) {
		this.comAff = comAff;
	}
	public String getComAffname() {
		return comAffname;
	}
	public void setComAffname(String comAffname) {
		this.comAffname = comAffname;
	}
	public String getLcKeyName() {
		return lcKeyName;
	}
	public void setLcKeyName(String lcKeyName) {
		this.lcKeyName = lcKeyName;
	}
}

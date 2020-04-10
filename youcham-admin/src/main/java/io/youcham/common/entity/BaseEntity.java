package io.youcham.common.entity;

import java.io.Serializable;
import java.util.Date;

/** 
 * @description: 公共字段
 * @author : guoqiang
 * @date : 2018年5月3日 下午3:50:46 
 * @version : v1.0
 * @since :
 */
@SuppressWarnings("serial")
public class BaseEntity implements Serializable{
	
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 创建人
	 */
	private String createId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private String updateId;
	/**
	 * 修改时间
	 */
	private Date updateTime;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

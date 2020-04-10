package io.youcham.modules.commn.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-12-06 16:55:27
 */
@TableName("CUSTOMERCODE")
public class CustomercodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String code;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String dist;

	/**
	 * 设置：
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setDist(String dist) {
		this.dist = dist;
	}
	/**
	 * 获取：
	 */
	public String getDist() {
		return dist;
	}
}

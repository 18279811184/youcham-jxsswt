package io.youcham.modules.swzj.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 全国城市地区表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-05 18:15:08
 */
@Data
@TableName("SWZJ_COUNTY")
public class SwzjCountyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 代码
	 */
	private String code;
	/**
	 * 
	 */
	private Integer cityLevel;
	/**
	 * 
	 */
	private Integer orderNum;
	/**
	 * 一级父级
	 */
	private String firstLevelParent;
	/**
	 * 二级父级
	 */
	private String twoLevelParent;
	/**
	 * 是否进开发区（0否1是）
	 */
	private Integer ynDevelopmentZone;
	/**
	 * 开发区等级
	 */
	private Integer developmentZoneLevel;
	/**
	 * 是否关联区县
	 */
	private Integer ynRelation;
	/**
	 * 关联县区
	 */
	private String relationCounty;
	/**
	 * 全国统一城市代码
	 */
	private String countyCode;
	/**
	 * 创建人id
	 */
	private String createId;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 创建人id
	 */
	private String updateId;
	/**
	 * 创建人名称
	 */
	private String updateName;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 版本号
	 */
	@Version
	private Integer version;
	/**
	 * 逻辑删除字段 0：未删除 -1：已删除
	 */
	@TableLogic
	private Integer beDelete;

	@TableField(exist = false)
	private String firstLevelParentName;
	@TableField(exist = false)
	private String twoLevelParentName;
	@TableField(exist = false)
	private String relationCountyName;





}

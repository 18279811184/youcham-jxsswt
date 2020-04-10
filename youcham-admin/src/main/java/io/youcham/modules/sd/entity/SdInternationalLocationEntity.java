package io.youcham.modules.sd.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 全球地市表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-01 21:09:05
 */
@Data
@TableName("SD_INTERNATIONAL_LOCATION")
public class SdInternationalLocationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 父id/上级id
	 */
	private String pid;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 层级
	 */
	private Integer areaLevel;
	/**
	 * 中文名称
	 */
	private String name;
	/**
	 * 英文名称
	 */
	private String nameEn;
	/**
	 * 中文拼音
	 */
	private String namePinyin;
	/**
	 * 地区代码
	 */
	private String code;
	/**
	 * 状态值 0无效 1有效
	 */
	private Integer status;
	/**
	 * 百度.纬度
	 */
	private String lat;
	/**
	 * 百度.经度
	 */
	private String lng;
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
	@JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
	@JsonFormat (pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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


}

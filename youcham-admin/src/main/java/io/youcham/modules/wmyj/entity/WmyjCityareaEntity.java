package io.youcham.modules.wmyj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 市区代码表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-31 16:57:31
 */
@Data
@TableName("WMYJ_CITYAREA")
public class WmyjCityareaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 市区代码
	 */
	private String cityareacode;
	/**
	 * 城市名
	 */
	private String city;
	/**
	 * 地区名
	 */
	private String area;
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
	@JsonFormat (pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 修改人id
	 */
	private String updateId;
	/**
	 * 修改人名称
	 */
	private String updateName;
	/**
	 * 修改时间
	 */
	@JsonFormat (pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 版本号
	 */
	@Version
	private Integer version;
	/**
	 * 逻辑删除
	 */
	@TableLogic
	private Integer beDelete;


}

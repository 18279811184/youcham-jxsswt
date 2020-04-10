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
 * 企业代表码
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:28
 */
@Data
@TableName("WMYJ_TBL_INFOCODE")
public class WmyjTblInfocodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 企业代码
	 */
	private String enterpriseCode;
	/**
	 * 企业名称
	 */
	private String enterpriseName;
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
	 * 逻辑删除字段
	 */
	@TableLogic
	private Integer beDelete;
	/**
	 * 
	 */
	@TableId
	private String id;


}

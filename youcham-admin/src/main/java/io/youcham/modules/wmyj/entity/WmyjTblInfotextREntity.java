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
 * 出口信息问题汇总表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:29
 */
@Data
@TableName("WMYJ_TBL_INFOTEXT_R")
public class WmyjTblInfotextREntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 唯一标识
	 */
	@TableId
	private String id;
	/**
	 * 用户名
	 */
	private String userid;
	/**
	 * 企业代码
	 */
	private String enterpriseCode;
	/**
	 * 企业名称
	 */
	private String enterpriseName;
	/**
	 * 所属单位代码
	 */
	private String unitCode;
	/**
	 * 报出日期
	 */
	@JsonFormat (pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date dateIn;
	/**
	 * 存在的问题
	 */
	private String questions;
	/**
	 * 有关建议和意见
	 */
	private String suggest;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 联系电话
	 */
	private String telephone;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 审核是否通过
	 */
	private String showFlag;
	/**
	 * 是否纳入统计
	 */
	private String statisFlag;
	/**
	 * 审核用户
	 */
	private String statisUserid;


}

package io.youcham.modules.qrtz.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务执行历史记录
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-01-04 15:43:04
 */
@Data
@TableName("QRTZ_RUN_HISTORY")
public class QrtzRunHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 状态 0 失败 1 成功
	 */
	private Integer status;
	/**
	 * 执行数据条数
	 */
	private Integer dataNum;
	/**
	 * 任务描述
	 */
	private String remark;
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
	@JsonFormat (pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
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

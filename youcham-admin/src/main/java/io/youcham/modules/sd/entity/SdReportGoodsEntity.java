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
 * 申报物资清单
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
@Data
@TableName("SD_REPORT_GOODS")
public class SdReportGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 物资名称
	 */
	private String goodsName;
	/**
	 * 物资数量 （允许客户输入单位）
	 */
	private String goodsNum;
	/**
	 * 所属申报数据id
	 */
	private String reportDataId;
	/**
	 * 描述
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

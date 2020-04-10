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
 * 出口信息金额汇总表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-23 14:18:30
 */
@Data
@TableName("WMYJ_TBL_INFOAMOUNT_R")
public class WmyjTblInfoamountREntity implements Serializable {
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
	 * 逻辑删除
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
	 * 本月预计出口额
	 */
	private Integer monthAmount;
	/**
	 * 上年本月出口额
	 */
	private Integer yearAmount;
	/**
	 * 高新技术产品
	 */
	private Integer newtelAmount;
	/**
	 * 机电产品
	 */
	private Integer elecAmount;
	/**
	 * 农产品及其深加工产品
	 */
	private Integer farmAmount;
	/**
	 * 有色金属
	 */
	private Integer metalAmount;
	/**
	 * 纺织服装
	 */
	private Integer weaveAmount;
	/**
	 * 轻工产品
	 */
	private Integer lightinduAmount;
	/**
	 * 钢材和铁合金
	 */
	private Integer steelAmount;
	/**
	 * 医药化工
	 */
	private Integer medicineAmount;
	/**
	 * 建材
	 */
	private Integer materialsAmount;
	/**
	 * 其他产品
	 */
	private Integer otherAmount;
	/**
	 * 订单情况(增加)
	 */
	private Integer orderHigh;
	/**
	 * 订单情况(持平)
	 */
	private Integer orderMid;
	/**
	 * 订单情况(减少)
	 */
	private Integer orderLow;
	/**
	 * 价格走势(上涨)
	 */
	private Integer priceHigh;
	/**
	 * 价格走势(持平)
	 */
	private Integer priceMid;
	/**
	 * 价格走势(减少)
	 */
	private Integer priceLow;
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
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 订单情况
	 */
	private Integer orderFlag;
	/**
	 * 价格走势
	 */
	private Integer priceFlag;


}

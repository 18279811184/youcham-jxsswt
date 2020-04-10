package io.youcham.modules.wmyj.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 出口信息金额汇总表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-24 09:03:48
 */
@Data
@TableName("WMYJ_INFO")
public class WmyjInfoEntity implements Serializable {
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
	@JsonFormat (pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dateIn;
	/**
	 * 本月预计出口额
	 */
	private BigDecimal monthAmount;
	/**
	 * 上年本月出口额
	 */
	private BigDecimal yearAmount;
	/**
	 * 高新技术产品
	 */
	private BigDecimal newtelAmount;
	/**
	 * 机电产品
	 */
	private BigDecimal elecAmount;
	/**
	 * 农产品及其深加工产品
	 */
	private BigDecimal farmAmount;
	/**
	 * 有色金属
	 */
	private BigDecimal metalAmount;
	/**
	 * 纺织服装
	 */
	private BigDecimal weaveAmount;
	/**
	 * 轻工产品
	 */
	private BigDecimal lightinduAmount;
	/**
	 * 钢材和铁合金
	 */
	private BigDecimal steelAmount;
	/**
	 * 医药化工
	 */
	private BigDecimal medicineAmount;
	/**
	 * 建材
	 */
	private BigDecimal materialsAmount;
	/**
	 * 其他产品
	 */
	private BigDecimal otherAmount;
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
	 * 所属市代码
	 */
	private String cityCode;
	/**
	 * 所属县代码
	 */
	private String areaCode;


}

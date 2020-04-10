package io.youcham.modules.sd.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.youcham.common.utils.ListUtils;
import io.youcham.modules.sys.entity.SysDictEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 申报/提供信息
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
@Data
@TableName("SD_REPORT_DATA")
public class SdReportDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 申报类型 0:需求方 1:提供方
	 */
	private Integer reportType;
	/**
	 * 名称/公司名称
	 */
	private String name;
	/**
	 * 所在地（省）
	 */
	private String province;
	/**
	 * 所在地（市）
	 */
	private String city;
	/**
	 * 详细地址（门牌号）
	 */
	private String address;
	/**
	 * 客户类型(字典编码)
	 */
	private String customerType;
	/**
	 * 物资对接方式（字典编码集合 每个编码开头用@  例如 @001@002）
	 */
	private String acceptTypes;
	/**
	 * 是否需要物流 (0: 否; 1:是)
	 */
	private Integer needLogistics;
	/**
	 * 备注（最多100字）
	 */
	private String remark;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系人手机号
	 */
	private String contactsTel;
	/**
	 * 需求来源（字典编码）
	 */
	private String sourceType;
	/**
	 * 图片ids (英文逗号隔开)
	 */
	private String fileIds;
	/**
	 * 密码加密盐
	 */
	private String salt;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 定位经纬度
	 */
	private String latLng;
	/**
	 * 定位地址
	 */
	private String locationAddress;
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
	@JsonFormat (pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
	 * 国家
	 */
	private String country;
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

	/**
	 * 描述
	 */
	@TableField (exist = false)
	private Map mapDesc;

	@TableField(exist = false)
	private String customerTypeDesc;
	@TableField(exist = false)
	private String sourceTypeDesc;
	@TableField(exist = false)
	private Boolean moreinfo = false;

	@TableField(exist = false)
	private List<SysDictEntity> acceptTypesList;

	@TableField(exist = false)
	private List<SdReportGoodsEntity> goodsList;

	/**
	 * 图片
	 */
	@TableField(exist = false)
	private List<Map> fileIdsList;


	/**
	 * 申报类型描述
	 * @return
	 */
	public String getReportTypeDesc(){
		if(null == this.reportType){
			return "";
		}else if(this.reportType == 0){
			return "需求方";
		}else{
			return "提供方";
		}
	}

	/**
	 * 是否需要物流
	 * @return
	 */
	public String getNeedLogisticsDesc(){
		if(null == this.needLogistics){
			return "";
		}else if(this.needLogistics == 0){
			return "否";
		}else{
			return "是";
		}
	}

	/**
	 * 获取接收方式名称集合字符串
	 * @return
	 */
	public String getacceptTypesListDesc(){
		if(ListUtils.isNotEmpty(acceptTypesList)){
			return ListUtils.convertElementPropertyToString(acceptTypesList,"value",",");
		}

		return "";
	}




}

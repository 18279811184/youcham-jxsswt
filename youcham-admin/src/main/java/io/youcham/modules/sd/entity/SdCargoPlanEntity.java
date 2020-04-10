package io.youcham.modules.sd.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-04-01 14:52:06
 */
@Data
@TableName("SD_CARGO_PLAN")
public class SdCargoPlanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 计划开始时间
	 */
	@JsonFormat (pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date planStartDate;
	/**
	 * 计划结束时间
	 */
	@JsonFormat (pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date planEndDate;
	/**
	 * 运输方式
	 */
	private String transType;
	/**
	 * 港口名称
	 */
	private String portName;
	/**
	 * 运行线路-起点
	 */
	private String transLineOrigin;
	/**
	 * 运行线路-终点
	 */
	private String transLineDestination;
	/**
	 * 班期
	 */
	private String schedule;
	/**
	 * 运营单位
	 */
	private String operateUnit;
	/**
	 * 联系人
	 */
	private String contectPserson;
	/**
	 * 电话
	 */
	private String phoneNumber;
	/**
	 * 备注
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
	 * 更新人id
	 */
	private String updateId;
	/**
	 * 更新人名称
	 */
	private String updateName;
	/**
	 * 更新时间
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
	
	@TableField(exist = false)
	private String transTypeDesc;
	
	/**
	 * 运输类型描述
	 * @return
	 */
	public String getTransTypeDesc(){
		if("0".equals(this.transType)){
			return "水水联运";
		}else if("1".equals(this.transType)){
			return "铁海联运";
		}else if("2".equals(this.transType)){
			return "赣欧班列";
		}else if("3".equals(this.transType)){
			return "国际航空货运";
		}else {
			return "";
		}
	}


}

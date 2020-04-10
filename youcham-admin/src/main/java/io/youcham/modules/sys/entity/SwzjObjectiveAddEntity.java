package io.youcham.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 目标添加
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-04 15:31:37
 */
@Data
@ToString
@TableName("SWZJ_OBJECTIVE_ADD")
public class SwzjObjectiveAddEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 创建人
	 */
	private String createId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private String updateId;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 版本
	 */
	@Version
	private Integer version;
	/**
	 * 逻辑删除
	 */
	@TableLogic
	private Integer beDelete;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 修改人名称
	 */
	private String updateName;
	/**
	 * 年份
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy")
	private Integer year;
	/**
	 * 目标
	 */
	private BigDecimal targetMillion;
	/**
	 * 地市
	 */
	private String region;



	@TableField(exist = false)
	private Map<String,Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}

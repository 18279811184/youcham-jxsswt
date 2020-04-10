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
 * 
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-19 15:45:33
 */
@Data
@TableName("WMYJ_USER")
public class WmyjUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 
	 */
	private Integer rev;
	/**
	 * 
	 */
	private String first;
	/**
	 * 
	 */
	private String last;
	/**
	 * 
	 */
	private String email;
	/**
	 * 
	 */
	private String pwd;
	/**
	 * 
	 */
	private String pictureId;


}

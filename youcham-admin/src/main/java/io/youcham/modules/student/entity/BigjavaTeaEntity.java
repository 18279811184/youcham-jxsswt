package io.youcham.modules.student.entity;

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
 * @date 2020-04-09 12:15:31
 */
@Data
@TableName("bigjava_tea")
public class BigjavaTeaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 名称
	 */
	private String teaName;
	/**
	 * 手机号码
	 */
	private String teaPhone;
	/**
	 * 教职工号
	 */
	private Integer teaNumber;
	/**
	 * 学生id
	 */
	private String teaStus;


}

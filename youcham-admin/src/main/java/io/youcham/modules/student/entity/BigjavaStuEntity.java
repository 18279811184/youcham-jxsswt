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
@TableName("bigjava_stu")
public class BigjavaStuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 名字
	 */
	private String stuName;
	/**
	 * 性别
	 */
	private String stuSex;
	/**
	 * 邮箱
	 */
	private String stuEmali;
	/**
	 * 手机号
	 */
	private String stuPhone;
	/**
	 * 学号
	 */
	private Integer stuNumber;
	/**
	 * 所选课程
	 */
	private String stuCourse;
	/**
	 * 成绩
	 */
	private Integer stuCredit;

	/**
	 * 老师id
	 */
	private String stuTeaid;


}

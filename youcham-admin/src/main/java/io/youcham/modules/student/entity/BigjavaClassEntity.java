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
 * @date 2020-04-09 11:34:41
 */
@Data
@TableName("bigjava_class")
public class BigjavaClassEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 课程名称
	 */
	private String className;
	/**
	 * 课程性质/属性
	 */
	private String classNature;
	/**
	 * 课程学时
	 */
	private Integer classTime;
	/**
	 * 授课/在线学时
	 */
	private Integer classOnlieTime;
	/**
	 * 线下实验学时
	 */
	private Integer classOutlineTime;
	/**
	 * 属于某个学期
	 */
	private String classInsemester;
	/**
	 * 删除
	 */
	@TableLogic
	private Integer beDelete;
	/**
	 * 学分
	 */
	private Integer classCredit;


}

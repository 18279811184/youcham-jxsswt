package io.youcham.modules.ins.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.youcham.modules.sys.entity.SysUserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件表
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2018-05-18 09:56:24
 */
@Data
@TableName("sys_filetable")
public class SysFiletableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 文件id
	 */
	@TableId
	private String id;
	/**
	 * 文件序号
	 */
	private Long fileXh;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 文件备注
	 */
	private String fileRemark;
	/**
	 * 文件真实名称
	 */
	private String fileRealname;
	/**
	 * 创建人
	 */
	private String fileCreatid;
	/**
	 * 文件大小
	 */
	private String fileSize;
	/**
	 * 创建用户
	 */
	@TableField(exist=false)
	private SysUserEntity createUser;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date fileCreatedate;
	/**
	 * 删除标识
	 */
	@TableLogic
	private Integer beDelete;
	/**
	 * 版本号
	 */
	@Version
	private Integer version;
	/**
	 * 文件路径
	 */
	@Version
	private String fileUrl;
	
}

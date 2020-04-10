  
/**    
 * @Title: UploadMessage.java  
 * @Package cn.com.thinvent.redcross.volunteer.web.entity  
 * @Description: TODO  
 * @author liuxg  
 * @date 2017年8月22日  
 * @version V1.0    
 */  
    
package io.youcham.modules.ins.fileentity;

import java.io.Serializable;

/**  
 * @ClassName: UploadMessage  
 * @Description: TODO
 * @author liuxg  
 * @date 2017年8月22日  
 *    
 */
public class UploadMessage implements Serializable{
	/**
	 * 
	 */
	private static final Long serialVersionUID = 1L;
	//上传到服务器中的目录
	private String filePath;
	//上传到服务器中的文件的原始文件名
	private String oriFileName;
	//备份到服务其中的文件的文件名
	private String targetFileName;
	//文件的类型
	private String fileType;
	//文件的大小
	private String fileSize;
	//用于标识文件是否上传成功的状态码,200:表示文件上传成功,404:表示文件上传失败
	private String code;
	//用于描述文件上传成功或者失败的原因
	private String msg;
	public UploadMessage() {
		super();
	}
	public UploadMessage(String filePath, String oriFileName, String targetFileName, String fileType, String fileSize,
			String code, String msg) {
		this();
		this.filePath = filePath;
		this.oriFileName = oriFileName;
		this.targetFileName = targetFileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.code = code;
		this.msg = msg;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getOriFileName() {
		return oriFileName;
	}
	public void setOriFileName(String oriFileName) {
		this.oriFileName = oriFileName;
	}
	public String getTargetFileName() {
		return targetFileName;
	}
	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "UploadMessage [filePath=" + filePath + ", oriFileName=" + oriFileName + ", targetFileName="
				+ targetFileName + ", fileType=" + fileType + ", fileSize=" + fileSize + ", code=" + code + ", msg="
				+ msg + "]";
	}
	
}

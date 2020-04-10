package io.youcham.modules.ins.fileentity;
/**
 * 返回客户端信息
 * @author ChenGR
 *
 */
public class UploadMsg {
	private String success;
	private String msg;
	private String path;
	private String url;
	private String ori_name;
	private String md5_name;

	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOri_name() {
		return ori_name;
	}

	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}

	public String getMd5_name() {
		return md5_name;
	}

	public void setMd5_name(String md5_name) {
		this.md5_name = md5_name;
	}

}

  
/**    
 * @Title: FileUtil.java  
 * @Package cn.com.thinvent.redcross.volunteer.web.controller.web.util  
 * @Description: TODO  
 * @author liuxg  
 * @date 2017年8月22日  
 * @version V1.0    
 */  
    
package io.youcham.modules.ins.fileentity;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**  
 * @ClassName: FileUtil  
 * @Description: TODO
 * @author liuxg  
 * @date 2017年8月22日  
 *    
 */
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	public static UploadMessage uploadFile(MultipartFile multipartFile){
		UploadMessage uploadMessage = new UploadMessage();
		String oriFileName = multipartFile.getOriginalFilename();
		Long size = multipartFile.getSize();
		Long maxSize = Long.parseLong(FileConfigReader.getUploadFileMaxSize());
		if (maxSize<size) {
			uploadMessage.setCode("404");
			uploadMessage.setMsg("上传的文件过大,请压缩后再上传!");
			return uploadMessage;
		}
		String fileSize = null;
		if(size>(1024*1024)){
			Long newSize = size/(1024*1024);
			fileSize=newSize+"m";
		}else{
			Long newSize = size/(1024);
			fileSize=newSize+"k";
		}
		String targetFileName = createTargetFileName(oriFileName);
		String fileType = oriFileName.substring(oriFileName.lastIndexOf("."), oriFileName.length());
		File file = createFilePath(targetFileName);
		try {
			multipartFile.transferTo(file);
			uploadMessage.setCode("200");
			uploadMessage.setMsg("上传成功");
			uploadMessage.setFilePath(file.getAbsolutePath());
			uploadMessage.setFileSize(fileSize);
			uploadMessage.setOriFileName(oriFileName);
			uploadMessage.setTargetFileName(targetFileName);
			uploadMessage.setFileType(fileType);
		} catch (IllegalStateException e) {
			uploadMessage.setCode("404");
			uploadMessage.setMsg(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			uploadMessage.setCode("404");
			uploadMessage.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return uploadMessage;
	}
	 
	private static File createFilePath(String targetFileName) {
		String rootPath = FileConfigReader.getProperties("rootPath");
		String rootFile = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String filePath = rootPath+File.separator+rootFile;
		File file = new File(filePath, targetFileName);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		return file;
	}
	
	private static String createTargetFileName(String oriFileName){
		Random random = new Random();
		System.out.println("原始文件的名字 : "+oriFileName);
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			sBuffer.append(random.nextInt(10));
		}
		sBuffer.append("_").append(oriFileName);
		return sBuffer.toString();
	}
}

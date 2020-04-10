package io.youcham.common.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.youcham.modules.ins.fileentity.FileConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件导出功能
 * 文件下载功能
 * 导出为word文件
 * 
 * @author Administrator
 *
 */
public class DocUtil {
	private Configuration configure = null;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@SuppressWarnings("deprecation")
	public DocUtil() {
		configure = new Configuration();
		configure.setDefaultEncoding("utf-8");
	}

	/**
	 * 根据Doc模板生成word文件
	 * 
	 * @param dataMap
	 *            Map 需要填入模板的数据
	 * @param downloadType
	 *            需要形成的模板名称
	 * @param savePath
	 *            保存路径
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public String createDoc(Map<String, Object> dataMap, String downloadType, String savePath) {
		String st = "";
		try {
			// 加载需要装填的模板
			Template template = null;
			// 加载模板文件
			configure.setClassForTemplateLoading(this.getClass(), "/statics/template");
			// 设置对象包装器
			configure.setObjectWrapper(new DefaultObjectWrapper());
			// 设置异常处理器
			configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			// 定义Template对象,注意模板类型名字与downloadType要一致
			template = configure.getTemplate(downloadType + ".xml");
			// 输出文档
			File outFile = new File(savePath);
			if(!outFile.getParentFile().exists()){
				outFile.getParentFile().mkdirs();
			}
			
			FileOutputStream fos= new FileOutputStream(outFile);


			Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			template.process(dataMap, out);
			out.close();
			fos.close();
			
			st = outFile.getPath();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}

	public void createAndDown(Map<String, Object> dataMap, String downloadType, Writer out) {
		String st = "";
		try {
			// 加载需要装填的模板
			Template template = null;
			// 加载模板文件
			configure.setClassForTemplateLoading(this.getClass(), "/statics/template");
			// 设置对象包装器
			configure.setObjectWrapper(new DefaultObjectWrapper());
			// 设置异常处理器
			configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			// 定义Template对象,注意模板类型名字与downloadType要一致
			template = configure.getTemplate(downloadType + ".xml");

			template.process(dataMap, out);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据Doc模板生成word文件
	 * 
	 * @param dataMap
	 *            Map 需要填入模板的数据
	 * @param downloadType
	 *            模板路径
	 * @param savePath
	 *            保存路径
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public String createDocCustom(Map<String, Object> dataMap, String downloadType, String savePath) {
		String st = "";
		try {
			File file = new File(downloadType);
			
			// 加载需要装填的模板
			Template template = null;
			// 加载模板文件
			configure.setDirectoryForTemplateLoading(file.getParentFile());
			// 设置对象包装器
			configure.setObjectWrapper(new DefaultObjectWrapper());
			// 设置异常处理器
			configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			// 定义Template对象,注意模板类型名字与downloadType要一致
			template = configure.getTemplate(file.getName());
			// 输出文档
			File outFile = new File(savePath);
			if(!outFile.getParentFile().exists()){
				outFile.getParentFile().mkdirs();
			}
			
			FileOutputStream fos= new FileOutputStream(outFile);
			Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			template.process(dataMap, out);
			out.close();
			fos.close();
			
			st = outFile.getPath();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	
	/**
	 * 文件下载
	 * 
	 * @param url
	 *            文件所在路径，可用createDoc获取
	 * @param response
	 *            传一个response： HttpServletResponse response
	 */
	public boolean downLoad(String url, HttpServletResponse response) {
		boolean flag=false;
		
		File file = null;
		InputStream fis = null;
		OutputStream fos = null;
		
		try {
			// path是指欲下载的文件的路径。
			file = new File(url);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			fis = new BufferedInputStream(new FileInputStream(url));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition"
					, "attachment;filename=" + new String(filename.getBytes("GBK"),"ISO8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			fos = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			fos.write(buffer);
			
			flag=true;
		} catch (IOException ex) {
			flag=false;
			ex.printStackTrace();
		}finally {
			try {
				if (fos != null)
				fos.close();
				if (fis != null)
			    fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
          
            if (file != null)
            file.delete();
        }
		
		return flag;
	}

	/** 
	 * @Description: 在rootPath下创建当天文件夹 并返回系统产生唯一文件名 
	 * @author : guoqiang
	 * @date : 2018年10月12日 下午2:43:04 
	 * @param multFile
	 * @return
	 */
	public static R createDir(MultipartFile multFile,String rootPath){
		// 文件不得为空
		if(multFile.isEmpty()){
			return R.error("未上传文件");
		}
		
		// 比对文件大小
		long size = multFile.getSize();
		long maxSize = 0L;
		String ms = FileConfigReader.getUploadFileMaxSize();
		if(ms!=null){
			maxSize = Long.parseLong(ms);
		}
		if(size > maxSize){
			return R.error("上传的文件过大");
		}
		
		// 文件后缀
		String imagehz = multFile.getOriginalFilename().substring(multFile.getOriginalFilename().lastIndexOf("."));
		long timename = System.currentTimeMillis();
		
		//拼接四位随机数
		StringBuffer sbuf = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<4;i++){
			sbuf.append(random.nextInt(10));
		}
		
		// 四位随机数 + file + _ + 时间戳 + 文件后缀
		String name = "file" + timename + imagehz;
		String fileName = sbuf.append("_").append(name).toString();
		
		//创建当日文件夹 
//		String rootPath = FileConfigReader.getUploadDocTemplate();
		String path = rootPath+File.separator+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		File insertFile = new File(path,fileName);
		if(!insertFile.getParentFile().exists()){
			insertFile.getParentFile().mkdirs();
		}
		
		return  R.ok().put("filename", name).put("insertFile", insertFile);
	}
	
	/** 
	 * @Description: 创建文件夹 并返回系统产生唯一文件名 
	 * @author : guoqiang
	 * @date : 2018年10月12日 下午2:43:04 
	 * @param rootPath
	 * @return
	 */
	public static R createDir(String rootPath){
		long timename = System.currentTimeMillis();
		
		//拼接四位随机数
		StringBuffer sbuf = new StringBuffer();
		Random random = new Random();
		for(int i=0;i<4;i++){
			sbuf.append(random.nextInt(10));
		}
		
		// 四位随机数 + file + _ + 时间戳 + 文件后缀
		String name = "file" + timename;
		String fileName = sbuf.append("_").append(name).toString();
		
		//创建当日文件夹
//		String rootPath = FileConfigReader.getUploadDocTemplate();
		String path = rootPath+ sbuf;
		File insertFile = new File(path);
		if(!insertFile.exists()){
			insertFile.mkdirs();
		}
		
		return  R.ok().put("filename", fileName).put("filepath", path);
	}
	/**
	 * 压缩文件
	 *
	 * @param srcfile File[] 需要压缩的文件列表
	 * @param zipfile File 压缩后的文件
	 */
	public void zipFiles(List<File> srcfile, File zipfile) {
		try {
			byte[] buf = null;
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
			// Compress the files
			for (int i = 0; i < srcfile.size(); i++) {
				File file = srcfile.get(i);
				FileInputStream in = new FileInputStream(file);
				System.out.println("file : " + file);
				/* 取得流最大字节数 */
				buf = new byte[in.available()];
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(file.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				/* 写入 */
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
				file.delete();
				file.getParentFile().delete();
			}
			// Complete the ZIP file
			out.close();
		} catch (IOException e) {
			logger.error("压缩文件出错了 (；′⌒`)");
			e.printStackTrace();
		}
	}
}

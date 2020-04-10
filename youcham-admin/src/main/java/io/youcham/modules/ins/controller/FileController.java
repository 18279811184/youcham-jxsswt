
/**
 * @Title: FileController.java
 * @Package cn.com.thinvent.redcross.volunteer.web.controller
 * @Description: TODO
 * @author liuxg
 * @date 2017年8月22日
 * @version V1.0
 */

package io.youcham.modules.ins.controller;

import com.alibaba.fastjson.JSONObject;
import io.youcham.common.constant.Variable;
import io.youcham.common.utils.R;
import io.youcham.modules.ins.entity.SysFiletableEntity;
import io.youcham.modules.ins.fileentity.*;
import io.youcham.modules.ins.service.SysFiletableService;
import io.youcham.modules.sys.entity.SysUserEntity;
import io.youcham.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @ClassName: FileController
 * @Description: 这是一个专门用来处理上传文件和下载文件的控制器
 * @author xucg
 * @date 2017年8月22日
 *
 */
@RestController
@RequestMapping(value = "/fileAction")
public class FileController {
    @Autowired
    private SysFiletableService sysFiletableService;
	/*@Resource
	ICommonManageService commonService;*/

    @RequestMapping(value = "/uploadFile")
    public @ResponseBody
    UploadMessage getUploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
                                HttpServletRequest request) {
        UploadMessage uploadFile = FileUtil.uploadFile(file);
        return uploadFile;
    }


    @RequestMapping(value = "uploadFile2")
    @ResponseBody
    public void getUploadFile2(@RequestParam(value = "imgFile") MultipartFile filep,
                               HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        System.out.println("文件的上传的控制器........................................");
        //String name = filep.getOriginalFilename();
        //图片名称替换为时间轴
        String imagehz = filep.getOriginalFilename().substring(filep.getOriginalFilename().lastIndexOf("."));
        long timename = System.currentTimeMillis();
        String name = "file" + timename + imagehz;

        long size = filep.getSize();
        long maxSize = 0L;
        String ms = FileConfigReader.getUploadFileMaxSize();
        if (ms != null) {
            maxSize = Long.parseLong(ms);
            ms = null;
        }
        if (size > maxSize) {
            resultMap.put("error", "error");
            resultMap.put("url", "上传的文件过大!!!");
            //return resultMap.toString();
        }
        /**
         * 开始将这个文件或者图片上传的服务器中的某个磁盘中
         */
        //String rootPath = CommConstants.OS_WIN_C_PATH_STR + CommConstants.OS_WIN_PATH_STR;
        String rootPath = FileConfigReader.getUploadFilePath();
        //判断当前系统是window还是linux
       /* if (System.getProperty(CommConstants.OS_FLAG_STR).toLowerCase().startsWith(CommConstants.OS_WIN_STR)) {
            //获取系统视图资源
            FileSystemView sys = FileSystemView.getFileSystemView();
            //获取计算机驱动资源
            File[] filesQ = File.listRoots();
            File cFile = new File(CommConstants.OS_WIN_C_PATH_STR);
            for(File t:filesQ){
                if(t != null && !t.equals(cFile)){
                    if(sys.getSystemTypeDescription(t).equals(CommConstants.OS_WIN_PATH_VALUE_STR)){//是否为本地磁盘
                    	//rootPath = rootPath;
                        break;
                    }
                }
            }
        } else {
        	rootPath = CommConstants.OS_NOT_WIN_PATH_STR;
        }*/


        String path = rootPath + File.separator
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        StringBuffer sbuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sbuf.append(random.nextInt(10));
        }
        random = null;
        String fileName = sbuf.append("_").append(name).toString();

        File insertFile = new File(path, fileName);
        //
        if (!insertFile.getParentFile().exists()) {
            insertFile.getParentFile().mkdirs();
        }
        try {
            filep.transferTo(insertFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resultMap.put("error", "error");
            resultMap.put("url", "上传文件出错!!!");
            //return resultMap.toString();
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", "error");
            resultMap.put("url", "上传文件出错!!!");
            //return resultMap.toString();
        }

        String geturl = URLEncoder.encode(insertFile.getAbsolutePath(), "utf-8");
        System.out.println("获取到的路径---" + request.getContextPath() + "/fileAction/loadImg?cateogry=" + geturl);
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("url", request.getContextPath() + "/fileAction/loadImg?cateogry=" + geturl);
        out.println(obj.toString());


    }

    //webupload  上传图片
    @RequestMapping(value = "webuploadfile")
    @ResponseBody
    public void webuploadfile(@RequestParam(value = "file") MultipartFile filep,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {
        //备注
        String mark = request.getParameter("mark");
        System.out.println("备注----" + mark);
        Map<String, String> resultMap = new HashMap<>();
        System.out.println("文件的上传的控制器........................................");
        //String name = filep.getOriginalFilename();
        System.out.println(filep.getOriginalFilename() + "文件名");
        //图片名称替换为时间轴
        String imagehz = filep.getOriginalFilename().substring(filep.getOriginalFilename().lastIndexOf("."));
        long timename = System.currentTimeMillis();
        String name = "file" + timename + imagehz;

        long size = filep.getSize();
        long maxSize = 0L;
        String ms = FileConfigReader.getUploadFileMaxSize();
        if (ms != null) {
            maxSize = Long.parseLong(ms);
            ms = null;
        }
        if (size > maxSize) {
            resultMap.put("error", "error");
            resultMap.put("url", "上传的文件过大!!!");
            response.setStatus(404);
            PrintWriter out = response.getWriter();
            JSONObject obj = new JSONObject();
            obj.put("error", 500);
            obj.put("message", "上传失败");
            out.println(obj.toString());
            return;
            //return resultMap.toString();
        }
        /**
         * 开始将这个文件或者图片上传的服务器中的某个磁盘中
         */
        //String rootPath = CommConstants.OS_WIN_C_PATH_STR + CommConstants.OS_WIN_PATH_STR;
        String rootPath = FileConfigReader.getUploadFilePath();
        //判断当前系统是window还是linux
       /* if (System.getProperty(CommConstants.OS_FLAG_STR).toLowerCase().startsWith(CommConstants.OS_WIN_STR)) {
            //获取系统视图资源
            FileSystemView sys = FileSystemView.getFileSystemView();
            //获取计算机驱动资源
            File[] filesQ = File.listRoots();
            File cFile = new File(CommConstants.OS_WIN_C_PATH_STR);
            for(File t:filesQ){
                if(t != null && !t.equals(cFile)){
                    if(sys.getSystemTypeDescription(t).equals(CommConstants.OS_WIN_PATH_VALUE_STR)){//是否为本地磁盘
                    	//rootPath = rootPath;
                        break;
                    }
                }
            }
        } else {
        	rootPath = CommConstants.OS_NOT_WIN_PATH_STR;
        }*/


        String path = rootPath + File.separator
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        StringBuffer sbuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            sbuf.append(random.nextInt(10));
        }
        random = null;
        String fileName = sbuf.append("_").append(name).toString();

        File insertFile = new File(path, fileName);
        if (!insertFile.getParentFile().exists()) {
            insertFile.getParentFile().mkdirs();
        }
        try {
            filep.transferTo(insertFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            resultMap.put("error", "error");
            resultMap.put("url", "上传失败");
            System.out.println("上传失败");
            response.setStatus(404);
            PrintWriter out = response.getWriter();
            JSONObject obj = new JSONObject();
            obj.put("error", 500);
            obj.put("message", "上传失败");
            out.println(obj.toString());
            return;
            //return resultMap.toString();
        } catch (IOException e) {
            e.printStackTrace();
            resultMap.put("error", "error");
            resultMap.put("url", "上传失败");
            System.out.println("上传失败");
            response.setStatus(404);

            PrintWriter out = response.getWriter();
            JSONObject obj = new JSONObject();
            obj.put("error", 500);
            obj.put("message", "上传失败");
            out.println(obj.toString());
            return;
            //return resultMap.toString();
        }
        SysUserEntity user = (SysUserEntity) ShiroUtils.getSessionAttribute(Variable.LOGIN_USER.getValue());

        String geturl = URLEncoder.encode(insertFile.getAbsolutePath(), "utf-8");
        System.out.println("获取到的路径---" + request.getContextPath() + "/fileAction/loadImg?cateogry=" + geturl);
        //上传成功执行 新增文件表操作
        SysFiletableEntity sysFiletable = new SysFiletableEntity();
        sysFiletable.setFileName(fileName);
        sysFiletable.setFileRemark("");
        //文件类型
        sysFiletable.setFileType(imagehz);
//        sysFiletable.setFileCreatid(ShiroUtils.getUserId());
        sysFiletable.setFileUrl(geturl);
        sysFiletable.setFileRemark(mark);
        // 自动获取 创建时间
        sysFiletable.setFileCreatedate(new Date());
        //放文件真实名称
        sysFiletable.setFileRealname(filep.getOriginalFilename());
        sysFiletable.setFileSize(String.valueOf(size));
        System.out.println("文件大小 : " + size);
        String gid = this.save(sysFiletable);
        if (gid != null) {
            //获取到文件id
            System.out.println("获取到文件的id--" + gid);
        }

        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        obj.put("error", 0);
        obj.put("gid", gid);
        obj.put("message", "上传文件成功");
        obj.put("url", request.getContextPath() + "/fileAction/loadImg?cateogry=" + geturl);
        obj.put("fileurl", geturl);
        out.println(obj.toString());


    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ins:sysfiletable:save")
    public String save(@RequestBody SysFiletableEntity sysFiletable) {

        sysFiletableService.insert(sysFiletable);

        return sysFiletable.getId();
    }


    @RequestMapping(value = "loadImg")
    @ResponseBody
    //读取图片0-----
    public void loadImg(HttpServletResponse response, String cateogry) throws IOException {
        System.out.println("读取图片----");
        if (io.netty.util.internal.StringUtil.isNullOrEmpty(cateogry)) {
            cateogry = "default.png";
        }
        cateogry = URLDecoder.decode(cateogry);
        File file = new File(cateogry);
        // FileConfigReader.getProperties("rootPath")+File.separator+filePath;*/
	        /*File file = new File(PropertyUtils.getPropertyValueByKey(
					"configure.properties", "headImgPath")+"/"+cateogry);*/

        if (!file.exists()) {
            cateogry = this.getClass().getClassLoader().getResource("statics").getPath() + "/images/default.png";
            file = new File(cateogry);
        }
        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        inputStream.read(data);
        inputStream.close();

        response.setContentType("image/png");

        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }


    private String createRootPath() {
        String path = CommConstants.OS_WIN_C_PATH_STR + CommConstants.OS_WIN_PATH_STR;
        //判断当前系统是window还是linux
        /*if (System.getProperty(CommConstants.OS_FLAG_STR).toLowerCase().startsWith(CommConstants.OS_WIN_STR)) {
            //获取系统视图资源
            FileSystemView sys = FileSystemView.getFileSystemView();
            //获取计算机驱动资源
            File[] filesQ = File.listRoots();
            File cFile = new File(CommConstants.OS_WIN_C_PATH_STR);
            for(File t:filesQ){
                if(t != null && !t.equals(cFile)){
                    if(sys.getSystemTypeDescription(t).equals(CommConstants.OS_WIN_PATH_VALUE_STR)){//是否为本地磁盘
                        path = t + CommConstants.OS_WIN_PATH_STR;
                        break;
                    }
                }
            }
        } else {
            path = CommConstants.OS_NOT_WIN_PATH_STR;
        }*/
        return path;
    }


    /**
     * 根据二进制表的Id来获取这个这个二进制表中的图片或者文件,以IO流的形式将这个图片或者文件写回去
     * @param resp
     * @param req
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "downloadFile")
    public void downloadFile(HttpServletResponse resp, HttpServletRequest req)
            throws ServletException, IOException {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        req.setCharacterEncoding("utf-8");

        String bytearrayId = req.getParameter("bytearrayId");

        SysFiletableEntity sysFiletable = null;

        try {
            sysFiletable = sysFiletableService.selectById(bytearrayId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "";
        String name = "";
        if (sysFiletable == null) {
            name = "default.png";
            url = this.getClass().getClassLoader().getResource("statics").getPath() + "/images/default.png";
        } else {
            url = sysFiletable.getFileUrl();
            if (sysFiletable.getFileRealname() != null) {
                name = sysFiletable.getFileRealname();
            } else {
                name = sysFiletable.getFileName();
            }

        }
        url = URLDecoder.decode(url, "utf-8");
        System.out.println("文件或者图片在服务器磁盘中的地址 : " + url );
        try {
            if (name != null && !name.equals("")) {
                // 文件路径
                File file = new File(url);

                if (file.exists()) {
//                    resp.setContentType("text/html;charset=utf-8");
                    String fileType = name.substring(name.indexOf(".") + 1, name.length());
/*                    if ("jpg".equals(fileType) ||
                            "jpeg".equals(fileType)) {
                        resp.setContentType("image/*");
                    } else {
                        resp.setContentType("application/octet-stream");
                    }*/
                    resp.setContentType("application/octet-stream");
                    // 适配多浏览器
                    resp.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("GBK"), "ISO8859-1"));
                    resp.setHeader("Content-Length", String.valueOf(file.length()));
                    // 设置缓存
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                    outputStream = new BufferedOutputStream(resp.getOutputStream());
                    byte[] buff = new byte[(int) file.length()];
                    int bytesRead;
                    while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))) {
                        outputStream.write(buff, 0, bytesRead);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    /**
     * @Description: 加载附件
     * @author : 郭强 guoqiang@qq.com
     * @date : 2017年11月22日 下午2:24:41 
     * @param response
     * @throws IOException
     */
    @RequestMapping("loadFile")
    public void loadFile(HttpServletResponse response, String bytearrayId) throws IOException {

        SysFiletableEntity sysFiletable = sysFiletableService.selectById(bytearrayId);

        String url = "";
        String name = "";
        if (sysFiletable == null) {
            name = "default.png";
            url = this.getClass().getClassLoader().getResource("statics").getPath() + "/images/default.png";
        } else {
            url = sysFiletable.getFileUrl();
            if (sysFiletable.getFileRealname() != null) {
                name = sysFiletable.getFileRealname();
            } else {
                name = sysFiletable.getFileName();
            }
        }

        url = URLDecoder.decode(url, "utf-8");
        File file = new File(url);
        if (!file.exists()) {
            name = "default.png";
            url = this.getClass().getClassLoader().getResource("statics").getPath() + "/images/default.png";
            file = new File(url);
        }

        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        inputStream.read(data);
        inputStream.close();

        if (sysFiletable != null) {
            if (sysFiletable.getFileRealname() != null ) {
                name = sysFiletable.getFileRealname();
            } else if (sysFiletable.getFileName() != null){
                name = sysFiletable.getFileName();
            }
        }

        String fileType = name.substring(name.indexOf(".") + 1, name.length());
//        if("jpg".equals(fileType)||
//				"jpeg".equals(fileType)){
//        	response.setContentType("image/*");  
//        	response.setContentType("image/png");
//		}else{
//			response.setContentType("application/octet-stream"); 
//		}
//        response.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("GBK"),"ISO8859-1"));   
//        response.setHeader("Content-Length", String.valueOf(file.length()));
        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
    }

    /**
     * 查询文件信息
     * @param fileId
     * @return
     */
    @RequestMapping ("selectFileDetails")
    public R getFileDetails (String fileId) {
        SysFiletableEntity entity = sysFiletableService.selectById(fileId);
        return R.ok().put("data",entity);
    }


    @RequestMapping(value = "showImage")
    public void showImage(HttpServletResponse resp, HttpServletRequest req)
            throws ServletException, IOException {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        req.setCharacterEncoding("utf-8");
        String filePath = req.getParameter("filePath");
        String fileName = req.getParameter("fileName");
        System.out.println("文件保存在服务器磁盘中的地址 : " + filePath + "文件保存在服务器磁盘中的名字  : " + fileName);
        try {
            if (filePath != null && !filePath.equals("") &&
                    fileName != null && !fileName.equals("")) {
                // 文件路径
                String path = FileConfigReader.getProperties("rootPath") + File.separator + filePath;
                File file = new File(path, fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    resp.setContentType("text/html;charset=utf-8");
                    String fileType = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
                    System.out.println(fileType);
                    if ("jpg".equals(fileType) ||
                            "jpeg".equals(fileType)) {
                        resp.setContentType("image/*");
                    } else {
                        resp.setContentType("application/octet-stream;");
                    }
                    // 适配多浏览器
                    resp.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
                    resp.setHeader("Content-Length", String.valueOf(file.length()));
                    // 设置缓存
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                    outputStream = new BufferedOutputStream(resp.getOutputStream());
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))) {
                        outputStream.write(buff, 0, bytesRead);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


    @RequestMapping(value = "/upload")
    @ResponseBody
    public UploadMsg upload(@RequestParam(value = "file", required = false) MultipartFile file,
                            HttpServletRequest request, ModelMap model) {
        UploadMsg um = new UploadMsg();
        try {
            System.out.println("---------------- upload img begin ---------------------");
            String imgName = request.getParameter("imgName");
            // 文件是否为空
			/*if (file.getSize() == 0 || file.isEmpty()) {
				throw new UploadException(UploadError.FILE_EMPTY);
			}*/
            // 文件是否过大
			/*if (file.getSize() > ConfigReader.getUpload_img_maxsize()) {
				throw new UploadException(UploadError.FILE_TOBIG);
			}*/
            Date getd = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNowStr = sdf.format(getd);
            String path = "D:/testFile";
            File nf = new File(path);
            if (!nf.exists()) {
                nf.mkdirs();//cj
            }
            String datepath = path + "/" + dateNowStr;
            File nfdate = new File(datepath);
            if (!nfdate.exists()) {
                nfdate.mkdirs();//cj
            }

            String ori_fileName = file.getOriginalFilename();
            String md5_fileName = "mdmdmdmd";
			/*logger.info("path:" + path);
			logger.info("ori_fileName:" + ori_fileName);
			logger.info("md5_filename:" + ori_fileName);*/
            File targetFile = new File(datepath, ori_fileName);
            if (!targetFile.exists()) {
                file.transferTo(targetFile);
                // 是否是图片
                if (!isImage(targetFile)) {
                    targetFile.delete();
                    /*throw new UploadException(UploadError.FILE_ERROR);*/
                }
            }
            um.setSuccess("true");
            um.setMsg(" upload success ");
            um.setOri_name(ori_fileName);
            um.setMd5_name(md5_fileName);
            um.setPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            um.setSuccess("false");
            um.setMsg(" upload false " + e);
        }
        System.out.println("---------------- upload img end ---------------------");
        return um;
    }

    //读取图片
	 /* public void loadImg(HttpServletResponse response, String cateogry) throws IOException {

	        if(io.netty.util.internal.StringUtil.isNullOrEmpty(cateogry)) {
	            cateogry = "default.png";
	        }

	        File file = new File(PropertyUtils.getPropertyValueByKey(
					"configure.properties", "headImgPath")+"/"+cateogry);

	        FileInputStream inputStream = new FileInputStream(file);
	        byte[] data = new byte[(int)file.length()];
	        inputStream.read(data);
	        inputStream.close();

	        response.setContentType("image/png");

	        OutputStream stream = response.getOutputStream();
	        stream.write(data);
	        stream.flush();
	        stream.close();
	    }
*/


    public JSONObject getConfig() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imageActionName", "uploadimage"); // 执行上传图片的action名称
        jsonObject.put("imageAllowFiles", new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"}); // 允许上传的图片类型
        jsonObject.put("imageFieldName", "upfile"); // 提交的图片表单名称
        jsonObject.put("imageMaxSize", "2048000"); // 上传大小限制，单位B
        jsonObject.put("imageCompressEnable", true); // 是否压缩图片,默认是true
        jsonObject.put("imageCompressBorder", 1600); // 图片压缩最长边限制
        jsonObject.put("imageInsertAlign", "none"); // 插入的图片浮动方式
        jsonObject.put("imageUrlPrefix", "http://xinrui.com/image/"); // 图片访问路径前缀
        jsonObject.put("imagePathFormat", "/{yyyy}{mm}{dd}/{time}{rand:6}"); // 上传保存路径,可以自定义保存路径和文件名格式
        return jsonObject;
    }


    /**
     * 判断文件是否是图片
     * @param file
     * @return
     * @throws IOException
     */
    private static boolean isImage(File file) throws IOException {
        BufferedImage bi = ImageIO.read(file);
        if (bi == null) {
            return false;
        }
        return true;
    }

    /**
     * 下载word模板
     * @param resp
     * @param req
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "downword")
    public void downword(HttpServletResponse resp, HttpServletRequest req)
            throws ServletException, IOException {
        //下载模板选择
        String type = req.getParameter("type");
        String url = "";
        if (("xfj").equals(type)) {
            //信访件
            url = "/docmodule/Scvisit.docx";
        } else if (("xssl").equals(type)) {
            //线索受理
            url = "/docmodule/Scvisitxs.docx";
        } else if (("1").equals(type)) {
            //线索受理
            url = "/docmodule/1.接受信访举报统计表.doc";
        } else if (("2").equals(type)) {
            //线索受理
            url = "/docmodule/2.反映问题线索处置情况统计表.doc";
        } else if (("3").equals(type)) {
            //线索受理
            url = "/docmodule/3.立案案件统计表.doc";
        } else if (("4").equals(type)) {
            //线索受理
            url = "/docmodule/4.处分人员统计表.doc";
        } else if (("5").equals(type)) {
            //线索受理
            url = "/docmodule/5.复查复议复审复核情况统计表.doc";
        } else if (("14").equals(type)) {
            //线索受理
            url = "/docmodule/14.下属各单位审查调查情况分析表.doc";
        } else if (("15").equals(type)) {
            //线索受理
            url = "/docmodule/15.下属各单位审查调查情况一览表.doc";
        } else if (("21").equals(type)) {
            //线索受理
            url = "/docmodule/21.下属各单位实践“四种形态”情况统计表.doc";
        } else if (("1561").equals(type)) {
            //线索受理
            url = "/docmodule/2015年6月1日至2018年7月1日立案 - 副本.xls";
        } else if (("djb").equals(type)) {
            //线索受理
            url = "/docmodule/登记表.doc";
        }


        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        req.setCharacterEncoding("utf-8");

        //String bytearrayId = req.getParameter("bytearrayId");

        // SysFiletableEntity sysFiletable = new SysFiletableEntity();

        try {
            //	sysFiletable = sysFiletableService.selectById(bytearrayId);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //

        String name = "Scvisit.docx";
		/*if(sysFiletable == null){
			
		}else{
		 url = sysFiletable.getFileUrl();
		 name = sysFiletable.getFileName();
		}*/
        url = URLDecoder.decode(url, "utf-8");
        System.out.println("文件或者图片在服务器磁盘中的地址 : " + url + "文件或者图片在服务器磁盘中的名字  : " + name);
        try {
            if (name != null && !name.equals("")) {
                // 文件路径
//				File file = new File(this.getClass().getClassLoader().getResource("statics").getPath()+url);
                File file = new File(ResourceUtils.getURL("classpath:").getPath() + "statics" + url);
                System.out.println("文件路径--" + ResourceUtils.getURL("classpath:").getPath() + "statics");
                System.out.println(file.getAbsolutePath());
                name = file.getName();
                if (!file.getParentFile().exists()) {
                    //	file.getParentFile().mkdirs();
                }
                if (file.exists()) {
                    resp.setContentType("text/html;charset=utf-8");
                    String fileType = name.substring(name.indexOf(".") + 1, name.length());
                    System.out.println(fileType);
                    if ("jpg".equals(fileType) ||
                            "jpeg".equals(fileType)) {
                        resp.setContentType("image/*");
                    } else {
                        resp.setContentType("application/octet-stream");
                    }
                    // 适配多浏览器
                    resp.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("GBK"), "ISO8859-1"));
                    resp.setHeader("Content-Length", String.valueOf(file.length()));
                    // 设置缓存
                    inputStream = new BufferedInputStream(new FileInputStream(file));
                    outputStream = new BufferedOutputStream(resp.getOutputStream());
                    byte[] buff = new byte[2048];
                    int bytesRead;
                    while (-1 != (bytesRead = inputStream.read(buff, 0, buff.length))) {
                        outputStream.write(buff, 0, bytesRead);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


    //预览
    @RequestMapping(value = "testdown")
    public void parseDocx2Html(HttpServletResponse response, HttpServletRequest req) throws Throwable {
        String bytearrayId = req.getParameter("bytearrayId");

        SysFiletableEntity sysFiletable = new SysFiletableEntity();

        try {
            sysFiletable = sysFiletableService.selectById(bytearrayId);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        String url = "";
        String name = "";
        if (sysFiletable == null) {
            name = "default.png";
            url = this.getClass().getClassLoader().getResource("statics").getPath() + "/images/default.png";
        } else {
            url = sysFiletable.getFileUrl();
            if (sysFiletable.getFileRealname() != null) {
                name = sysFiletable.getFileRealname();
            } else {
                name = sysFiletable.getFileName();
            }

        }
        url = URLDecoder.decode(url, "utf-8");
        System.out.println("文件或者图片在服务器磁盘中的地址 : " + url + "文件或者图片在服务器磁盘中的名字  : " + name);
        File file = new File(url);

        if (!file.exists()) {
            name = "default.png";
            url = this.getClass().getClassLoader().getResource("statics").getPath() + "/images/default.png";
            file = new File(url);
        }

        int lastIndex = url.lastIndexOf(".");
        String hz = url.substring(lastIndex, url.length());
        if ((".doc").equals(hz)) {
            System.out.println("2003-------");
			 /*String filepath = "F:\\";
	          String fileName = "xxxxxxx.doc";
	          String htmlName = "xxxxxxx.html";*/
            //final String file = filepath + fileName;
            InputStream input = new FileInputStream(file);
            HWPFDocument wordDocument = new HWPFDocument(input);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

            //解析word文档
            wordToHtmlConverter.processDocument(wordDocument);
            Document htmlDocument = wordToHtmlConverter.getDocument();
            //输出位置
            String scurl = this.getClass().getResource("/").getPath() + "statics/word/";
	        /* File htmlFile = new File(scurl + htmlName);
	         System.out.println("完整路径---"+scurl + htmlName);*/
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();


            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer serializer = factory.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");

            serializer.transform(domSource, streamResult);

            outStream.close();
            String content = new String(outStream.toByteArray());
            System.out.println(content);
            OutputStream stream = response.getOutputStream();
            stream.write(outStream.toByteArray());

        } else if ((".docx").equals(hz)) {
            // 1) 加载word文档生成 XWPFDocument对象
            InputStream in = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(in);
            String filepath = "C:/test/";
            // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
            File imageFolderFile = new File(filepath);
            XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
            options.setExtractor(new FileImageExtractor(imageFolderFile));
            options.setIgnoreStylesIfUnused(false);
            options.setFragment(true);

            // 3) 将 XWPFDocument转换成XHTML
            // OutputStream out = new FileOutputStream(new File(filepath + htmlName));
            // XHTMLConverter.getInstance().convert(document, out, options);

            //也可以使用字符数组流获取解析的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, baos, options);
            String content = baos.toString();
            System.out.println(content);
            baos.close();
            OutputStream stream = response.getOutputStream();
            stream.write(baos.toByteArray());


        } else {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            inputStream.read(data);
            inputStream.close();


            if (sysFiletable.getFileRealname() != null) {
                name = sysFiletable.getFileRealname();
            } else {
                name = sysFiletable.getFileName();
            }
            String fileType = name.substring(name.indexOf(".") + 1, name.length());
            OutputStream stream = response.getOutputStream();
            stream.write(data);
            stream.flush();
            stream.close();


        }


    }


    /*
    @RequestMapping(value="testdown2017")
    public static void parseToHtml() throws IOException {
        File f = new File("F:/xxxxx.docx");
        if (!f.exists()) {
            System.out.println("Sorry File does not Exists!");
        } else {
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {

                // 1) 加载XWPFDocument及文件
                InputStream in = new FileInputStream(f);
                XWPFDocument document = new XWPFDocument(in);

                // 2) 实例化XHTML内容(这里将会把图片等文件放到生成的"word/media"目录)
                File imageFolderFile = new File("f:/opt");
                XHTMLOptions options = XHTMLOptions.create().URIResolver(
                        new FileURIResolver(imageFolderFile));
                options.setExtractor(new FileImageExtractor(imageFolderFile));
                //options.setIgnoreStylesIfUnused(false);
                //options.setFragment(true);

                // 3) 将XWPFDocument转成XHTML并生成文件
                OutputStream out = new FileOutputStream(new File(
                        "F:/result.html"));
                XHTMLConverter.getInstance().convert(document, out, null);
            } else {
                System.out.println("Enter only MS Office 2007+ files");
            }
        }
    }*/
    //预览
    @RequestMapping(value = "testct")
    public void tesct(String[] type, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //下载模板选择
        //String type = req.getParameter("type");
        // String []path ={"D:\\youchamgx\\gxjj-v1.0\\youcham-admin\\target\\classes\\statics\\docmodule\\Scvisit.docx","D:\\youchamgx\\gxjj-v1.0\\youcham-admin\\target\\classes\\statics\\docmodule\\21.下属各单位实践“四种形态”情况统计表.doc"};
        List<String> path = new ArrayList<>();
        for (int i = 0; i < type.length; i++) {
            String url = "";

            if (("xfj").equals(type[i])) {
                //信访件
                url = "/docmodule/Scvisit.docx";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("xssl").equals(type[i])) {
                //线索受理
                url = "/docmodule/Scvisitxs.docx";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("1").equals(type[i])) {
                //线索受理
                url = "/docmodule/1.接受信访举报统计表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("2").equals(type[i])) {
                //线索受理
                url = "/docmodule/2.反映问题线索处置情况统计表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("3").equals(type[i])) {
                //线索受理
                url = "/docmodule/3.立案案件统计表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("4").equals(type[i])) {
                //线索受理
                url = "/docmodule/4.处分人员统计表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("5").equals(type[i])) {
                //线索受理
                url = "/docmodule/5.复查复议复审复核情况统计表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("14").equals(type[i])) {
                //线索受理
                url = "/docmodule/14.各单位审查调查情况分析表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("15").equals(type[i])) {
                //线索受理
                url = "/docmodule/15.各单位审查调查情况一览表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("21").equals(type[i])) {
                //线索受理
                url = "/docmodule/21.各单位实践“四种形态”情况统计表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("1561").equals(type[i])) {
                //线索受理
                url = "/docmodule/2015年6月1日至2018年7月1日立案.xls";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            } else if (("djb").equals(type[i])) {
                //线索受理
                url = "/docmodule/登记表.doc";
                url = URLDecoder.decode(url, "utf-8");
                path.add(url);
            }
        }

        //需要压缩的文件--包括文件地址和文件名
        String ns = this.getClass().getClassLoader().getResource("statics").getPath();
        String d1 = this.getClass().getClassLoader().getResource("statics").getPath() + "/docmodule/登记表.doc";
        // File file = new File(this.getClass().getClassLoader().getResource("statics").getPath()+url);

        // 要生成的压缩文件地址和文件名称
        String cp = FileConfigReader.getProperties("downFile");
        String desPath = cp + "DownLoad.zip";
        File zipFile = new File(desPath);
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        try {
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < path.size(); i++) {
                File file = new File(ns + path.get(i));
                //将需要压缩的文件格式化为输入流
                zipSource = new FileInputStream(file);
                //压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
                ZipEntry zipEntry = new ZipEntry(file.getName());
                //定位该压缩条目位置，开始写入文件到压缩包中

                zipStream.putNextEntry(zipEntry);

                //输入缓冲流
                bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                int read = 0;
                //创建读写缓冲区
                byte[] buf = new byte[1024 * 10];
                while ((read = bufferStream.read(buf, 0, 1024 * 10)) != -1) {
                    zipStream.write(buf, 0, read);
                }


                // 适配多浏览器
                //resp.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("GBK"),"ISO8859-1"));
                //	resp.setHeader("Content-Length", String.valueOf(file.length()));
                // 设置缓存
                //BufferedInputStream inputStream= new BufferedInputStream(new FileInputStream(zipStream));
                BufferedOutputStream outputStream = new BufferedOutputStream(resp.getOutputStream());
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bufferStream.read(buff, 0, buff.length))) {
                    outputStream.write(buff, 0, bytesRead);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bufferStream) bufferStream.close();
                if (null != zipStream) zipStream.close();
                if (null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        resp.setContentType("application/x-download");//告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        String sz = "downzip.zip";
        resp.setHeader("Content-Disposition", "attachment;filename=\"" + sz + "\"");//下载文件的名称
        ServletOutputStream servletOutputStream = resp.getOutputStream();
        DataOutputStream temps = new DataOutputStream(
                servletOutputStream);

        DataInputStream in = new DataInputStream(
                new FileInputStream(desPath));//浏览器下载文件的路径
        byte[] b = new byte[2048];
        File reportZip = new File(desPath);//之后用来删除临时压缩文件
        try {
            while ((in.read(b)) != -1) {
                temps.write(b);
            }
            temps.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (temps != null) temps.close();
            if (in != null) in.close();
            if (reportZip != null) reportZip.delete();//删除服务器本地产生的临时压缩文件
            servletOutputStream.close();
        }


    }


    /**
     * @Description:
     * @author : guoqiang
     * @date : 2018年10月12日 下午5:40:35
     * @param response
     * @param request
     * @param filepath
     */
    @RequestMapping("/downloadFileByPath")
    public void downloadFileByPath(HttpServletResponse response, HttpServletRequest request, String filepath) {
        if (StringUtils.isNotEmpty(filepath)) {
            // 文件路径
            File file = new File(filepath);
            if (!file.exists()) {
                filepath = this.getClass().getClassLoader().getResource("statics").getPath() + "/images/default.png";
                file = new File(filepath);
                System.out.println("未找到文件");
            }
            System.out.println("文件路径--" + filepath);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}

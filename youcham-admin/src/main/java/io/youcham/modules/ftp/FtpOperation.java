package io.youcham.modules.ftp;

import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * ftp 工具类
 * @author guoqiang
 * @email rd_guoqiang@139.com
 * @description
 * @date 2019/6/15
 */
@Component
public class FtpOperation {

    public static final int imageCutSize=300;
    private static final Logger log= LoggerFactory.getLogger(FtpOperation.class);

    @Value("${ftp.username}")
    private String userName;

    @Value("${ftp.password}")
    private String passWord;

    @Value("${ftp.host}")
    private String ip;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.filepath}")
    private String CURRENT_DIR;     // 文件存放的目录

    public static final String DIRSPLIT="/";

    public String getCURRENT_DIR() {
        return CURRENT_DIR;
    }


    public void setCURRENT_DIR(String cURRENTDIR) {
        CURRENT_DIR = cURRENTDIR;
    }

    // 下载的文件目录
    private String DOWNLOAD_DIR;

    // ftp客户端
    private FTPClient ftpClient = new FTPClient();

    /**
     *
     * 功能：上传文件附件到文件服务器
     * @param buffIn:上传文件流
     * @param fileName：保存文件名称
     * @param needDelete：是否同时删除
     * @return
     * @throws IOException
     */
    public boolean uploadToFtp(InputStream buffIn, String fileName, boolean needDelete)
            throws FTPConnectionClosedException, IOException,Exception {
        boolean returnValue = false;
        // 上传文件
        try {

            // 建立连接
            connectToServer();
            // 设置传输二进制文件
            setFileType(FTP.BINARY_FILE_TYPE);
            int reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply))
            {
                ftpClient.disconnect();
                throw new IOException("failed to connect to the FTP Server:"+ip);
            }
            ftpClient.enterLocalPassiveMode();

            ftpClient.changeWorkingDirectory(CURRENT_DIR);




            File uplfile = new File(fileName);
            boolean ss4 = existDirectory("file_upload");
            boolean ss5 = existDirectory("file_upload/2019/8");


            if(!existDirectory(uplfile.getParentFile().getPath())){
                String ss = uplfile.getParentFile().getPath()
                        .replace("\\","/");

               boolean sss= this.createDirecrotys(ss);
            }

            // 上传文件到ftp
            ftpClient.changeWorkingDirectory(CURRENT_DIR);

            // 上传文件到ftp
            returnValue = ftpClient.storeFile(fileName.replace("\\","/"), buffIn);

            if(needDelete){
                ftpClient.deleteFile(fileName);
            }

            // 输出操作结果信息
            if (returnValue) {
                log.info("uploadToFtp INFO: upload file  to ftp : succeed!");
            } else {
                log.info("uploadToFtp INFO: upload file  to ftp : failed!");
            }
            buffIn.close();
            // 关闭连接
            closeConnect();
        } catch (FTPConnectionClosedException e) {
            log.error("ftp连接被关闭！", e);
            throw e;
        } catch (Exception e) {
            returnValue = false;
            log.error("ERR : upload file  to ftp : failed! ", e);
            throw e;
        } finally {
            try {
                if (buffIn != null) {
                    buffIn.close();
                }
            } catch (Exception e) {
                log.error("ftp关闭输入流时失败！", e);
            }
            if (ftpClient.isConnected()) {
                closeConnect();
            }
        }
        return returnValue;
    }


    /**
     *
     * 功能：根据文件名称，下载文件流
     * @param filename
     * @return
     * @throws IOException
     */
    public InputStream  downloadFile(String filename)
            throws IOException {
        InputStream in=null;
        try {

            // 建立连接
            connectToServer();

            // 设置传输二进制文件
            setFileType(FTP.BINARY_FILE_TYPE);
            int reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply))
            {
                ftpClient.disconnect();
                throw new IOException("failed to connect to the FTP Server:"+ip);
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(CURRENT_DIR);

            // ftp文件获取文件
            in=ftpClient.retrieveFileStream(filename);


        } catch (FTPConnectionClosedException e) {
            log.error("ftp连接被关闭！", e);
            throw e;
        } catch (Exception e) {
            log.error("ERR : upload file "+ filename+ " from ftp : failed!", e);
        }
        return in;
    }


    /**
     * 文件下载
     * @param remoteFileName ftp上的文件名
     * @param localFileName 本地文件名
     */
    public void download(String remoteFileName,String localFileName){


        OutputStream outputStream = null;

        try {
            // 建立连接
            connectToServer();

            if (ftpClient == null){
                return ;
            }

            ftpClient.changeWorkingDirectory(CURRENT_DIR);
            FTPFile[] ftpFiles = ftpClient.listFiles(CURRENT_DIR);
            Boolean flag = false;
            //遍历当前目录下的文件，判断是否存在待下载的文件
            for (FTPFile ftpFile:ftpFiles){
                if (ftpFile.getName().equals(remoteFileName)){
                    flag = true;
                    break;
                }
            }

            if (!flag){
                log.error("directory：{}下没有 {}",CURRENT_DIR,remoteFileName);
                return ;
            }

            outputStream = new FileOutputStream("C:/"+localFileName);//创建文件输出流

            Boolean isSuccess = ftpClient.retrieveFile(remoteFileName,outputStream); //下载文件
            if (!isSuccess){
                log.error("download file 【{}】 fail",remoteFileName);
            }

            log.info("download file success");
            ftpClient.logout();
        } catch (IOException e) {
            log.error("download file 【{}】 fail ------->>>{}",remoteFileName,e.getCause());
        }catch (Exception e) {
            log.error("ERR : upload file  from ftp : failed!", e);
        }finally {
            if (ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("disconnect fail ------->>>{}",e.getCause());
                }
            }

            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close fail ------->>>{}",e.getCause());
                }
            }
        }
    }



    /**
     *
     * 功能：根据文件名称，下载文件流
     * @param filename
     * @return
     * @throws IOException
     */
    public InputStream  downloadFileStream(String filename){
        InputStream inputStream1 = null;
		URLConnection urlConnection = null;
		URL url = null;
		try {
//			url = new URL("ftp://administrator:sobigguo@192.168.0.102/"+filename);

            url = new URL("ftp://"+userName+":"+URLEncoder.encode(passWord)+"@"+ip+"/"+filename.replace("\\","/"));
			urlConnection = url.openConnection();
			inputStream1 = urlConnection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream1;
    }




    /**
     * 转码[GBK -> ISO-8859-1] 不同的平台需要不同的转码
     *
     * @param obj
     * @return
     */
//    private String gbkToIso8859(Object obj) {
//        try {
//            if (obj == null)
//                return "";
//            else
//                return new String(obj.toString().getBytes("GBK"), "iso-8859-1");
//        } catch (Exception e) {
//            return "";
//        }
//    }

    /**
     * 设置传输文件的类型[文本文件或者二进制文件]
     *
     * @param fileType
     *            --BINARY_FILE_TYPE、ASCII_FILE_TYPE
     */
    private void setFileType(int fileType) {
        try {
            ftpClient.setFileType(fileType);
        } catch (Exception e) {
            log.error("ftp设置传输文件的类型时失败！", e);
        }
    }

    /**
     *
     * 功能：关闭连接
     */
    public void closeConnect() {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
            log.error("ftp连接关闭失败！", e);
        }
    }

    /**
     * 连接到ftp服务器
     */
    private void connectToServer() throws FTPConnectionClosedException,Exception {
        if (!ftpClient.isConnected()) {
            int reply;
            try {
                ftpClient=new FTPClient();
                ftpClient.connect(ip,port);
                ftpClient.login(userName,passWord);
                reply = ftpClient.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                    log.info("connectToServer FTP server refused connection.");
                }

            }catch(FTPConnectionClosedException ex){
                log.error("服务器:IP："+ip+"没有连接数！there are too many connected users,please try later", ex);
                throw ex;
            }catch (Exception e) {
                log.error("登录ftp服务器【"+ip+"】失败", e);
                throw e;
            }
        }
    }
    // Check the path is exist; exist return true, else false.
    public boolean existDirectory(String path) throws IOException {
        boolean flag = false;
        ftpClient.enterLocalPassiveMode();
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory()
                    && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    /**
     * 创建FTP文件夹目录
     * @param pathName
     * @return
     * @throws IOException
     */
    public boolean createDirectory(String pathName) throws IOException {
        boolean isSuccess=false;
        try{
            isSuccess=ftpClient.makeDirectory(pathName);
        }catch(Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }


    public boolean createDirecrotys(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {

                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existDirectory(path)) {
                    if (createDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        success = false;
                        log.debug("创建目录[" + subDirectory + "]失败");
                        ftpClient.changeWorkingDirectory(subDirectory);
                    }
                } else {
                    ftpClient.changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }




    /**
     * 带点的
     * @param fileName
     * @return
     */
    public static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }
    /**
     * 不带点
     * @param fileName
     * @return
     */
    public static String getNoPointExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos+1);
    }
    /**
     *
     * 功能：根据当前时间获取文件目录
     * @return String
     */
    public static String getDateDir(Date dateParam){
        Calendar cal = Calendar.getInstance();
        if(null!=dateParam){
            cal.setTime(dateParam);
        }
        int currentYear = cal.get(Calendar.YEAR);
        int currentMouth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH) ;
        //int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        //return currentYear+FtpOperation.DIRSPLIT+currentMouth+FtpOperation.DIRSPLIT+currentDay+FtpOperation.DIRSPLIT+currentHour;
        return currentYear+File.separator+currentMouth+File.separator+currentDay;
    }

    /**
     *
     * 功能：根据当前时间获取文件目录加名称
     * @return String
     */
    public static String getDateDir(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int currentYear = cal.get(Calendar.YEAR);
        int currentMouth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH) ;


        return currentYear+File.separator+currentMouth+File.separator+currentDay;
    }

    /**
     *
     * 功能：根据当前时间获取文件目录加名称+文件名称
     * @return String
     */
    public static String getDateDirAndFileName(String fileName){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int currentYear = cal.get(Calendar.YEAR);
        int currentMouth = cal.get(Calendar.MONTH) + 1;
        int currentDay = cal.get(Calendar.DAY_OF_MONTH) ;

        // 文件后缀
        String imagehz = fileName.substring(fileName.lastIndexOf("."));
        long timename = System.currentTimeMillis();

        //拼接四位随机数
        StringBuffer sbuf = new StringBuffer();
        Random random = new Random();
        for(int i=0;i<4;i++){
            sbuf.append(random.nextInt(10));
        }

        // 四位随机数 + file + _ + 时间戳 + 文件后缀
        String newFileName = "uploadfile_" + sbuf.toString() + timename + imagehz;


        return "file_upload"+File.separator
                +currentYear+File.separator+currentMouth+File.separator+currentDay
                +File.separator+newFileName;
    }


    public static void main(String[] args){
        FtpOperation ftpOperation = new FtpOperation();


        // 上传

        // 从前台传过来的文件输入流
        MultipartFile multipartFile = null;
        InputStream inputStream = null;

        // 生成唯一文件名  避免重复覆盖  该路径存到数据库中 下载时使用
        String newFileName = getDateDirAndFileName(multipartFile.getOriginalFilename());

        try {

           Boolean res =  ftpOperation.uploadToFtp(multipartFile.getInputStream(),newFileName,false);

           if(res){
               //上传成功
           }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //下载 或者 预览

        //输入文件路径获取到输出流
        InputStream inputStream1 = ftpOperation.downloadFileStream(newFileName);

        // 也可直接调用FileContrll中的预览接口

        //fileAction/ftpLoad?cateogry= newFileName


    }

}

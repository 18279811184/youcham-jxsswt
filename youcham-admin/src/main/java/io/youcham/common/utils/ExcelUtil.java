package io.youcham.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * @description: excel工具类
 * @author : guoqiang
 * @date : 2018年11月7日 上午10:47:05 
 * @version : v1.0
 * @since :
 */
public class ExcelUtil {
	
    /** 
     * 1.创建 workbook 03
     * @return 
     */  
    public HSSFWorkbook getHSSFWorkbook(){  
        return new HSSFWorkbook();  
    } 
    
    /** 
     * 2.创建 sheet 
     * @param hssfWorkbook 
     * @param sheetName sheet 名称 
     * @return 
     */  
    public HSSFSheet getHSSFSheet(HSSFWorkbook hssfWorkbook, String sheetName){  
        return hssfWorkbook.createSheet(sheetName);  
    }  
  
    /** 
     * 3.写入表头信息 
     * @param hssfWorkbook 
     * @param hssfSheet 
     * @param headInfoList List<Map<String, Object>> 
     *  key: title    列标题 
     *  columnWidth   列宽 
     *  dataKey       列对应的 dataList item key 
     */  
    @SuppressWarnings("static-access")
	public void writeHeader(HSSFWorkbook hssfWorkbook,HSSFSheet hssfSheet ,List<Map<String, Object>> headInfoList){  
        HSSFCellStyle cs = hssfWorkbook.createCellStyle();  
        HSSFFont font = hssfWorkbook.createFont();  
        font.setFontHeightInPoints((short)12);  
        font.setBoldweight(font.BOLDWEIGHT_BOLD);  
        cs.setFont(font);  
        cs.setAlignment(cs.ALIGN_CENTER);  
  
        HSSFRow r = hssfSheet.createRow(0);  
        r.setHeight((short) 380);  
        HSSFCell c = null;  
        Map<String, Object> headInfo = null;  
        //处理excel表头  
        for(int i=0, len = headInfoList.size(); i < len; i++){  
            headInfo = headInfoList.get(i);  
            c = r.createCell(i);  
            c.setCellValue(headInfo.get("title").toString());  
            c.setCellStyle(cs);  
            if(headInfo.containsKey("columnWidth")){  
                hssfSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));  
            }  
        }  
    }  
  
    /** 
     * 4.写入内容部分 
     * @param hssfWorkbook 
     * @param hssfSheet 
     * @param startIndex 从1开始，多次调用需要加上前一次的dataList.size() 
     * @param headInfoList List<Map<String, Object>> 
     *  key: title    列标题 
     *  columnWidth   列宽 
     *  dataKey       列对应的 dataList item key 
     * @param dataList 
     */  
    public void writeContent(HSSFWorkbook hssfWorkbook,HSSFSheet hssfSheet ,int startIndex,  
    		List<Map<String, Object>> headInfoList, List<Map<String, Object>> dataList){
        HSSFCellStyle cs = hssfWorkbook.createCellStyle();
        cs.setAlignment(cs.ALIGN_CENTER);

        Map<String, Object> headInfo = null;
        HSSFRow r = null;  
        HSSFCell c = null;  
        //处理数据  
        Map<String, Object> dataItem = null;  
        Object v = null;  
        for (int i=0, rownum = startIndex, len = (startIndex + dataList.size()); rownum < len; i++,rownum++){  
            r = hssfSheet.createRow(rownum);  
            r.setHeightInPoints(16);  
            dataItem = dataList.get(i);  
            for(int j=0, jlen = headInfoList.size(); j < jlen; j++){  
                headInfo = headInfoList.get(j);  
                c = r.createCell(j);
                c.setCellStyle(cs);
                v = dataItem.get(headInfo.get("dataKey").toString());
  
                if (v instanceof String) {  
                    c.setCellValue((String)v);  
                }else if (v instanceof Boolean) {  
                    c.setCellValue((Boolean)v);  
                }else if (v instanceof Calendar) {  
                    c.setCellValue((Calendar)v);  
                }else if (v instanceof Double) {  
                    c.setCellValue((Double)v);  
                }else if (v instanceof Integer  
                        || v instanceof String  
                        || v instanceof Short  
                        || v instanceof Float) {  
                    c.setCellValue(Double.parseDouble(v.toString()));  
                }else if (v instanceof HSSFRichTextString) {  
                    c.setCellValue((HSSFRichTextString)v);  
                }else {  
                    c.setCellValue(v.toString());  
                }  
            }  
        }  
    }  
  
    public void write2FilePath(HSSFWorkbook hssfWorkbook, String filePath) throws IOException{  
        FileOutputStream fileOut = null;  
        try{  
            fileOut = new FileOutputStream(filePath);  
            hssfWorkbook.write(fileOut);  
        }finally{  
            if(fileOut != null){  
                fileOut.close();  
            }  
        }  
    }  
  
	
    /** 
     * 1.创建 workbook 07
     * @return 
     */  
    public XSSFWorkbook getXSSFWorkbook(){  
        return new XSSFWorkbook();  
    } 
    
    /** 
     * 2.创建 sheet 
     * @param XSSFWorkbook 
     * @param sheetName sheet 名称 
     * @return 
     */  
    public XSSFSheet getXSSFSheet(XSSFWorkbook XSSFWorkbook, String sheetName){  
        return XSSFWorkbook.createSheet(sheetName);  
    }

    public void writeTitle(XSSFWorkbook XSSFWorkbook,XSSFSheet XSSFSheet,String title,int startIndex,int regionLastCol){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        XSSFFont font = XSSFWorkbook.createFont();
        font.setFontHeightInPoints((short)20);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(cs.ALIGN_CENTER);

        XSSFRow r = XSSFSheet.createRow(startIndex);
        r.setHeight((short) 680);

        XSSFCell c = null;
        Map<String, Object> headInfo = null;
        c = r.createCell(0);
        c.setCellValue(title);
        c.setCellStyle(cs);

        CellRangeAddress region = new CellRangeAddress(0,0,0,regionLastCol);
        XSSFSheet.addMergedRegion(region);

    }


    /** 
     * 3.写入表头信息 
     * @param XSSFWorkbook 
     * @param XSSFSheet 
     * @param headInfoList List<Map<String, Object>> 
     *  key: title    列标题 
     *  columnWidth   列宽 
     *  dataKey       列对应的 dataList item key 
     */  
    @SuppressWarnings("static-access")
	public void writeHeader(XSSFWorkbook XSSFWorkbook,XSSFSheet XSSFSheet ,List<Map<String, Object>> headInfoList){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();  
        XSSFFont font = XSSFWorkbook.createFont();  
        font.setFontHeightInPoints((short)12);  
        font.setBoldweight(font.BOLDWEIGHT_BOLD);  
        cs.setFont(font);  
        cs.setAlignment(cs.ALIGN_CENTER);
        cs.setVerticalAlignment(cs.VERTICAL_CENTER);

        setDefaultBorderStyle(cs);

        XSSFRow r = XSSFSheet.createRow(0);  
        r.setHeight((short) 480);
        XSSFCell c = null;  
        Map<String, Object> headInfo = null;  
        //处理excel表头  
        for(int i=0, len = headInfoList.size(); i < len; i++){  
            headInfo = headInfoList.get(i);  
            c = r.createCell(i);  
            c.setCellValue(headInfo.get("title").toString());  
            c.setCellStyle(cs);  
            if(headInfo.containsKey("columnWidth")){  
                XSSFSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));  
            }  
        }  
    }

    public void writeHeader(XSSFWorkbook XSSFWorkbook,XSSFSheet XSSFSheet ,List<Map<String, Object>> headInfoList,int startIndex){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        XSSFFont font = XSSFWorkbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(cs.ALIGN_CENTER);
        cs.setVerticalAlignment(cs.VERTICAL_CENTER);
        setDefaultBorderStyle(cs);

        XSSFRow r = XSSFSheet.createRow(startIndex);
        r.setHeight((short) 480);
        XSSFCell c = null;
        Map<String, Object> headInfo = null;
        //处理excel表头
        for(int i=0, len = headInfoList.size(); i < len; i++){
            headInfo = headInfoList.get(i);
            c = r.createCell(i);
            c.setCellValue(headInfo.get("title").toString());
            c.setCellStyle(cs);
            if(headInfo.containsKey("columnWidth")){
                XSSFSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));
            }
        }
    }
    public void writeHeader(XSSFWorkbook XSSFWorkbook, XSSFSheet XSSFSheet , List<Map<String, Object>> headInfoList,
                            int startIndex, int currCount, HttpServletRequest request){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        XSSFFont font = XSSFWorkbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(cs.ALIGN_CENTER);
        cs.setVerticalAlignment(cs.VERTICAL_CENTER);
        setDefaultBorderStyle(cs);

        XSSFRow r = XSSFSheet.createRow(startIndex);
        r.setHeight((short) 480);
        XSSFCell c = null;
        Map<String, Object> headInfo = null;
        //处理excel表头
        for(int i=0, len = headInfoList.size(); i < len; i++){
            currCount++;
            headInfo = headInfoList.get(i);
            c = r.createCell(i);
            c.setCellValue(headInfo.get("title").toString());
            c.setCellStyle(cs);
            if(headInfo.containsKey("columnWidth")){
                XSSFSheet.setColumnWidth(i, (short)(((Integer)headInfo.get("columnWidth") * 8) / ((double) 1 / 20)));
            }
            request.getSession().setAttribute("curCount", currCount);
        }
    }
    /** 
     * 4.写入内容部分 
     * @param XSSFWorkbook 
     * @param XSSFSheet 
     * @param startIndex 从1开始，多次调用需要加上前一次的dataList.size() 
     * @param headInfoList List<Map<String, Object>> 
     *  key: title    列标题 
     *  columnWidth   列宽 
     *  dataKey       列对应的 dataList item key 
     * @param dataList 
     */  
    public void writeContent(XSSFWorkbook XSSFWorkbook,XSSFSheet XSSFSheet ,int startIndex,
    		List<Map<String, Object>> headInfoList, List<Map<String, Object>> dataList){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        cs.setAlignment(cs.ALIGN_CENTER);
        setDefaultBorderStyle(cs);

        Map<String, Object> headInfo = null;  
        XSSFRow r = null;  
        XSSFCell c = null;  
        //处理数据  
        Map<String, Object> dataItem = null;  
        Object v = null;  
        Object vBefore = null;
        for (int i=0, rownum = startIndex, len = (startIndex + dataList.size()); rownum < len; i++,rownum++){  
            r = XSSFSheet.createRow(rownum);  
            r.setHeightInPoints(16);  
            dataItem = dataList.get(i);  
            for(int j=0, jlen = headInfoList.size(); j < jlen; j++){  
                headInfo = headInfoList.get(j);  
                c = r.createCell(j);  
                c.setCellStyle(cs);

                // 如果列为null值传入空字符串
                vBefore = dataItem.get(headInfo.get("dataKey"));
                if(null == vBefore){
                	 c.setCellValue(""); 
                	 continue;
                }
                
                v = dataItem.get(headInfo.get("dataKey").toString());  
  
                if (v instanceof String) {  
                    c.setCellValue((String)v);  
                }else if (v instanceof Boolean) {  
                    c.setCellValue((Boolean)v);  
                }else if (v instanceof Calendar) {  
                    c.setCellValue((Calendar)v);  
                }else if (v instanceof Double) {  
                    c.setCellValue((Double)v);  
                }else if (v instanceof Integer  
                        || v instanceof String  
                        || v instanceof Short  
                        || v instanceof Float) {  
                    c.setCellValue(Double.parseDouble(v.toString()));  
                }else if (v instanceof XSSFRichTextString) {  
                    c.setCellValue((XSSFRichTextString)v);  
                }else {  
                    c.setCellValue(v.toString());  
                }  
            }  
        }  
    }
    public void writeContent(XSSFWorkbook XSSFWorkbook,XSSFSheet XSSFSheet ,int startIndex,
                             List<Map<String, Object>> headInfoList, List<Map<String, Object>> dataList,
                             int currCount, HttpServletRequest request){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        cs.setAlignment(cs.ALIGN_CENTER);
        setDefaultBorderStyle(cs);

        Map<String, Object> headInfo = null;
        XSSFRow r = null;
        XSSFCell c = null;
        //处理数据
        Map<String, Object> dataItem = null;
        Object v = null;
        Object vBefore = null;
        for (int i=0, rownum = startIndex, len = (startIndex + dataList.size()); rownum < len; i++,rownum++){
            currCount ++;
            r = XSSFSheet.createRow(rownum);
            r.setHeightInPoints(16);
            dataItem = dataList.get(i);
            for(int j=0, jlen = headInfoList.size(); j < jlen; j++){
//                currCount ++;
                headInfo = headInfoList.get(j);
                c = r.createCell(j);
                c.setCellStyle(cs);

                // 如果列为null值传入空字符串
                vBefore = dataItem.get(headInfo.get("dataKey"));
                if(null == vBefore){
                    c.setCellValue("");
                    continue;
                }

                v = dataItem.get(headInfo.get("dataKey").toString());

                if (v instanceof String) {
                    c.setCellValue((String)v);
                }else if (v instanceof Boolean) {
                    c.setCellValue((Boolean)v);
                }else if (v instanceof Calendar) {
                    c.setCellValue((Calendar)v);
                }else if (v instanceof Double) {
                    c.setCellValue((Double)v);
                }else if (v instanceof Integer
                        || v instanceof String
                        || v instanceof Short
                        || v instanceof Float) {
                    c.setCellValue(Double.parseDouble(v.toString()));
                }else if (v instanceof XSSFRichTextString) {
                    c.setCellValue((XSSFRichTextString)v);
                }else {
                    c.setCellValue(v.toString());
                }
//                request.getSession().setAttribute("curCount", currCount);
            }
            request.getSession().setAttribute("curCount", currCount);
        }
    }
    public void write2FilePath(XSSFWorkbook XSSFWorkbook, String filePath) throws IOException{  
        FileOutputStream fileOut = null;  
        try{  
            fileOut = new FileOutputStream(filePath);  
            XSSFWorkbook.write(fileOut);  
        }finally{  
            if(fileOut != null){  
                fileOut.close();  
            }  
        }  
    }
  
    /** 
     * 导出excel 
     * code example: 
         List<Map<String, Object>> headInfoList = new ArrayList<Map<String,Object>>(); 
         Map<String, Object> itemMap = new HashMap<String, Object>(); 
         itemMap.put("title", "序号1"); 
         itemMap.put("columnWidth", 25); 
         itemMap.put("dataKey", "XH1"); 
         headInfoList.add(itemMap); 
 
         itemMap = new HashMap<String, Object>(); 
         itemMap.put("title", "序号2"); 
         itemMap.put("columnWidth", 50); 
         itemMap.put("dataKey", "XH2"); 
         headInfoList.add(itemMap); 
 
         itemMap = new HashMap<String, Object>(); 
         itemMap.put("title", "序号3"); 
         itemMap.put("columnWidth", 25); 
         itemMap.put("dataKey", "XH3"); 
         headInfoList.add(itemMap); 
 
         List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>(); 
         Map<String, Object> dataItem = null; 
         for(int i=0; i < 100; i++){ 
         dataItem = new HashMap<String, Object>(); 
         dataItem.put("XH1", "data" + i); 
         dataItem.put("XH2", 88888888f); 
         dataItem.put("XH3", "脉兜V5.."); 
         dataList.add(dataItem); 
         } 
         ExcelUtil.exportExcel2FilePath("test sheet 1","F:\\temp\\customer2.xls", headInfoList, dataList); 
 
     * @param sheetName   sheet名称 
     * @param filePath   文件存储路径， 如：f:/a.xls 
     * @param headInfoList List<Map<String, Object>> 
     *   key: title    列标题 
     *   columnWidth   列宽 
     *   dataKey       列对应的 dataList item key 
     * @param dataList  List<Map<String, Object>> 导出的数据 
     * @throws java.io.IOException 
     */  
    public static void exportExcel2FilePath2003(String sheetName, String filePath,  
                                   List<Map<String, Object>> headInfoList,  
                                   List<Map<String, Object>> dataList) throws IOException {  
        ExcelUtil excelUtil = new ExcelUtil();  
        //1.创建 Workbook  
        HSSFWorkbook hssfWorkbook = excelUtil.getHSSFWorkbook();  
        //2.创建 Sheet  
        HSSFSheet hssfSheet = excelUtil.getHSSFSheet(hssfWorkbook, sheetName);  
        //3.写入 head  
        excelUtil.writeHeader(hssfWorkbook, hssfSheet, headInfoList);  
        //4.写入内容  
        excelUtil.writeContent(hssfWorkbook, hssfSheet, 1, headInfoList, dataList);  
        //5.保存文件到filePath中  
        excelUtil.write2FilePath(hssfWorkbook, filePath);  
    }  
	  
    public static void exportExcel2FilePath2007(String sheetName, String filePath,  
            List<Map<String, Object>> headInfoList,  
            List<Map<String, Object>> dataList) throws IOException {  
		ExcelUtil excelUtil = new ExcelUtil();  
		//1.创建 Workbook  
		XSSFWorkbook hssfWorkbook= excelUtil.getXSSFWorkbook();  
		//2.创建 Sheet  
		XSSFSheet hssfSheet = excelUtil.getXSSFSheet(hssfWorkbook, sheetName);  
		//3.写入 head  
		excelUtil.writeHeader(hssfWorkbook, hssfSheet, headInfoList);  
		//4.写入内容  
		excelUtil.writeContent(hssfWorkbook, hssfSheet, 1, headInfoList, dataList);  
		//5.保存文件到filePath中  
		excelUtil.write2FilePath(hssfWorkbook, filePath);  
	}

    /**
     *
     * @param headName
     * @param sheetName
     * @param filePath
     * @param headInfoList
     * @param dataList
     * @throws IOException
     */
    public static void exportExcel2FilePath2007(String headName,String sheetName, String filePath,
                                                List<Map<String, Object>> headInfoList,
                                                List<Map<String, Object>> dataList) throws IOException {
        ExcelUtil excelUtil = new ExcelUtil();
        //1.创建 Workbook
        XSSFWorkbook hssfWorkbook= excelUtil.getXSSFWorkbook();
        //2.创建 Sheet
        XSSFSheet hssfSheet = excelUtil.getXSSFSheet(hssfWorkbook, sheetName);
        //3.写入 head
        excelUtil.writeHeader(hssfWorkbook, hssfSheet, headInfoList,2);
        //4.写入内容
        excelUtil.writeContent(hssfWorkbook, hssfSheet, 2, headInfoList, dataList);
        //5.保存文件到filePath中
        excelUtil.write2FilePath(hssfWorkbook, filePath);
    }

    /**
     * 默认列样式
     * @param XSSFWorkbook
     * @return
     */
    public XSSFCellStyle getHeadDefaultStyle(XSSFWorkbook XSSFWorkbook){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        XSSFFont font = XSSFWorkbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(cs.ALIGN_CENTER);
        cs.setVerticalAlignment(cs.VERTICAL_CENTER);
        setDefaultBorderStyle(cs);

        return cs;
    }

    /**
     * 默认列样式
     * @param XSSFWorkbook
     * @return
     */
    public XSSFCellStyle getHeadStyleNoBorder(XSSFWorkbook XSSFWorkbook){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        XSSFFont font = XSSFWorkbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(cs.ALIGN_CENTER);
        cs.setVerticalAlignment(cs.VERTICAL_CENTER);

        return cs;
    }

    /**
     * 默认列样式
     * @param XSSFWorkbook
     * @return
     */
    public XSSFCellStyle getContentDefaultStyle(XSSFWorkbook XSSFWorkbook){
        XSSFCellStyle cs = XSSFWorkbook.createCellStyle();
        XSSFFont font = XSSFWorkbook.createFont();
//        font.setFontHeightInPoints((short)14);
        cs.setFont(font);
        cs.setAlignment(cs.ALIGN_CENTER);

        return cs;
    }

    /**
     * 默认列样式
     * @param cs
     * @return
     */
    public void setDefaultBorderStyle(XSSFCellStyle cs){
        cs.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
        cs.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
        cs.setBorderLeft(CellStyle.BORDER_THIN);  // 左边边框
        cs.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边边框颜色
        cs.setBorderRight(CellStyle.BORDER_THIN); // 右边边框
        cs.setRightBorderColor(IndexedColors.BLACK.getIndex());  // 右边边框颜色
        cs.setBorderTop(CellStyle.BORDER_THIN); // 上边边框
        cs.setTopBorderColor(IndexedColors.BLACK.getIndex());  // 上边边框颜色
    }

    /**
     * 导出Excel  可直接调用
     */
    public Map<String, Object> exportExcel(String excelName, List<String> columnList) {
        /* 标题 */
        // 创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet(excelName);

        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        row1.setHeightInPoints(30);
        // 创建标题的单元格样式style2以及字体样式headerFont1
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
        headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
        headerFont1.setFontName("微软雅黑"); // 设置字体类型
        headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
        style2.setFont(headerFont1); // 为标题样式设置字体样式
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        // 设置单元格内容
        cell.setCellValue(excelName);
        cell.setCellStyle(style2); // 设置标题样式
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnList.size() - 1));


        // 在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        row2.setHeightInPoints(20);// 设置表头高度
        HSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);// 设置自动换行
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
        headerFont.setFontName("微软雅黑"); // 设置字体类型
        headerFont.setFontHeightInPoints((short) 12); // 设置字体大小
        style.setFont(headerFont); // 为标题样式设置字体样式
        for (int i = 0; i < columnList.size(); i++) {
            HSSFCell cell1 = row2.createCell(i);
            cell1.setCellValue(columnList.get(i));
            cell1.setCellStyle(style);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sheet", sheet);
        map.put("wb", wb);
        return map;
    }

}

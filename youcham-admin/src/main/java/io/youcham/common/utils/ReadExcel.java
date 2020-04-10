package io.youcham.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ReadExcel {
	// 总行数
	private int totalRows = 0;
	// 总条数
	private int totalCells = 0;
	// 错误信息接收器
	private String errorMsg;

	// 构造方法
	public ReadExcel() {
	}

	// 获取总行数
	public int getTotalRows() {
		return totalRows;
	}

	// 获取总列数
	public int getTotalCells() {
		return totalCells;
	}

	// 获取错误信息
	public String getErrorInfo() {
		return errorMsg;
	}

	/**
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath) {
		if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))) {
			errorMsg = "文件名不是excel格式";
			return false;
		}
		return true;
	}

	/**
	 * 读EXCEL文件，获取客户信息集合
	 * 
	 *
	 */
	public List<Map<String, Object>> getExcelInfo(String fileName, MultipartFile Mfile) {

		// 初始化客户信息的集合
		List<Map<String, Object>> customerList = new ArrayList<>();
		// 初始化输入流
		InputStream is = null;
		try {
			// 验证文件名是否合格
			if (!validateExcel(fileName)) {
				return null;
			}
			// 根据文件名判断文件是2003版本还是2007版本
			boolean isExcel2003 = true;
			if (WDWUtil.isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			// 根据新建的文件实例化输入流
			is = Mfile.getInputStream();
			// 根据excel里面的内容读取客户信息
			customerList = getExcelInfo(is, isExcel2003);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return customerList;
	}

	/*
	外贸预警导入方法
	 */
	public List<Map<String, Object>> getExcelInfo_wmyjByLhf(String fileName, MultipartFile Mfile) {

		// 初始化外贸预警信息的集合
		List<Map<String, Object>> customerList = new ArrayList<>();
		// 初始化输入流
		InputStream is = null;
		try {
			// 验证文件名是否合格
			if (!validateExcel(fileName)) {
				return null;
			}
			// 根据文件名判断文件是2003版本还是2007版本
			boolean isExcel2003 = true;
			if (WDWUtil.isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			// 根据新建的文件实例化输入流
			is = Mfile.getInputStream();
			// 根据excel里面的内容读取客户信息
			customerList = getExcelInfo_wmyjByLhf(is, isExcel2003);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return customerList;
	}
	/**
	 * 根据excel里面的内容读取客户信息
	 * 
	 * @param is
	 *            输入流
	 * @param isExcel2003
	 *            excel是2003还是2007版本
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, Object>> getExcelInfo(InputStream is, boolean isExcel2003) {
		List<Map<String, Object>> customerList = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			// 当excel是2003时
			if (isExcel2003) {
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时
				wb = new XSSFWorkbook(is);
			}
			// 读取Excel里面客户的信息
			customerList = readExcelValue_WmyjByLhf(wb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	public List<Map<String, Object>> getExcelInfo_wmyjByLhf(InputStream is, boolean isExcel2003) {
		List<Map<String, Object>> customerList = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			// 当excel是2003时
			if (isExcel2003) {
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时
				wb = new XSSFWorkbook(is);
			}
			// 读取Excel里面客户的信息
			customerList = readExcelValue_WmyjByLhf(wb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerList;
	}
	/**
	 * 读取Excel里面外贸预警导入的信息
	 *
	 * @param wb
	 * @return
	 */
	private List<Map<String, Object>> readExcelValue_WmyjByLhf(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);

		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();

		// 得到Excel的列数(前提是有行数)
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		List<Map<String, Object>> customerList = new ArrayList<>();
		Map<String, Object> map;
		// 循环Excel行数,从第二行开始。标题不入库
		//wmyj导入是从第三行开始
		int titleNumber = 2;

		for (int r = titleNumber; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null)
				continue;
			map = new HashMap<String, Object>();

			// 循环Excel的列
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				//打印出各列的类型来
				//System.out.println("第"+c+"列"+":对应的单元格类型为：" + cell.getCellType() + ":对应的数据格式为：" + cell.getCellStyle().getDataFormat());

				if (null != cell) {
					String cellValue = getCellValue_wmyjByLhf(cell);
					map.put("cell" + c, cellValue);
				}
			}
			// 添加客户
			customerList.add(map);
		}
		return customerList;
	}



	/**
	 * 读取Excel里面客户的信息
	 * 
	 * @param wb
	 * @return
	 */
	private List<Map<String, Object>> readExcelValue(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);

		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();

		// 得到Excel的列数(前提是有行数)
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		List<Map<String, Object>> customerList = new ArrayList<>();
		Map<String, Object> map;
		// 循环Excel行数,从第二行开始。标题不入库
		//wmyj导入是从第三行开始
		int titleNumber = 2;

		for (int r = titleNumber; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null)
				continue;
			map = new HashMap<String, Object>();

			// 循环Excel的列
			for (int c = 0; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				//打印出各列的类型来
				//System.out.println("第"+c+"列"+":对应的单元格类型为：" + cell.getCellType() + ":对应的数据格式为：" + cell.getCellStyle().getDataFormat());

				if (null != cell) {
					String cellValue = getCellValue(cell);
					map.put("cell" + c, cellValue);
				}
			}
			// 添加客户
			customerList.add(map);
		}
		return customerList;
	}

	/**
	 * 把从excel中所有读出来的数据都转换为String类型
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: // 数字
				// short s = cell.getCellStyle().getDataFormat();
				if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
					SimpleDateFormat sdf = null;
					// 验证short值
					if (cell.getCellStyle().getDataFormat() == 14) {
						sdf = new SimpleDateFormat("yyyy/MM/dd");
					} else if (cell.getCellStyle().getDataFormat() == 21) {
						sdf = new SimpleDateFormat("HH:mm:ss");
					} else if (cell.getCellStyle().getDataFormat() == 22) {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					} else if (cell.getCellStyle().getDataFormat() == 176) {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					} else if (cell.getCellStyle().getDataFormat() == 177) {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					}else if (cell.getCellStyle().getDataFormat() == 178) {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					}else if (cell.getCellStyle().getDataFormat() == 179) {
						sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					}else {
						throw new RuntimeException("日期格式错误!!!"+cell.getCellStyle().getDataFormat());
					}
					Date date = cell.getDateCellValue();
					cellValue = sdf.format(date);
				} else if (cell.getCellStyle().getDataFormat() == 0) {// 处理数值格式
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = String.valueOf(cell.getRichStringCellValue().getString());
				}
				break;
			case Cell.CELL_TYPE_STRING: // 字符串
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN: // Boolean
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK: // 空值
				cellValue = null;
				break;
			case Cell.CELL_TYPE_ERROR: // 故障
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
		}
		return cellValue;
	}
	/**
	 * 把从excel中所有读出来的数据都转换为String类型
	 * @param cell
	 * @return
	 */
	public static String getCellValue_wmyjByLhf(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			// short s = cell.getCellStyle().getDataFormat();
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				// 验证short值
				if(cell.getCellStyle().getDataFormat() == 176) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}else {
					throw new RuntimeException("日期格式错误!!!"+cell.getCellStyle().getDataFormat());
				}
				Date date = cell.getDateCellValue();
				cellValue = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 0 || cell.getCellStyle().getDataFormat() == 177) {// 处理数值格式
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = String.valueOf(cell.getRichStringCellValue().getString());
			}
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = null;
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}

}

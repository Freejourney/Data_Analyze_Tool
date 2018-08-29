package com.example.admin.data_analysis_tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelUtils {
//
//	public List<Student> loadScoreInfo(String xlsPath) throws IOException{
//	    List temp = new ArrayList();
//	FileInputStream fileIn = new FileInputStream(xlsPath);
//	//根据指定的文件输入流导入Excel从而产生Workbook对象
//	Workbook wb0 = new HSSFWorkbook(fileIn);
//	//获取Excel文档中的第一个表单
//	Sheet sht0 = (Sheet) wb0.getSheetAt(0);
//	//对Sheet中的每一行进行迭代
//	        for (Row r : sht0) {
//	        //如果当前行的行号（从0开始）未达到2（第三行）则从新循环
//	if(r.getRowNum()<1){
//	continue;
//	}
//	//创建实体类
//	Student info=new Student();
//	//取出当前行第1个单元格数据，并封装在info实体stuName属性上
//	info.setStuName(r.getCell(0).getStringCellValue());
//	info.setClassName(r.getCell(1).getStringCellValue());
//	info.setRscore(r.getCell(2).getNumericCellValue());
//	            info.setLscore(r.getCell(3).getNumericCellValue());
//	temp.add(info);
//	        }
//	        fileIn.close();    
//	        return temp;    
//	    }
//	
	
//	private Logger logger = LoggerFactory.getLogger(ReadExcelUtils.class);
	private Workbook wb;
	private org.apache.poi.ss.usermodel.Sheet sheet;
	private Row row;
 
	public ReadExcelUtils(String filepath) {
		if(filepath==null){
			return;
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
			if(".xls".equals(ext)){
				wb = new HSSFWorkbook(is);
			}else if(".xlsx".equals(ext)){
				wb = new XSSFWorkbook(is);
			}else{
				wb=null;
			}
		} catch (FileNotFoundException e) {
//			logger.error("FileNotFoundException", e);
			e.printStackTrace();
		} catch (IOException e) {
//			logger.error("IOException", e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 * @author zengwendong
	 */
	public String[] readExcelTitle() throws Exception{
		if(wb==null){
			throw new Exception("Workbook对象为空！");
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = row.getCell(i).getCellFormula();
		}
		return title;
	}
 
	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 * @author zengwendong
	 */
	public Map<Integer, Map<Integer,Object>> readExcelContent() throws Exception{
		if(wb==null){
			throw new Exception("Workbook对象为空！");
		}
		Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();
		
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			Map<Integer,Object> cellValue = new HashMap<Integer, Object>();
			while (j < colNum) {
				Object obj = getCellFormatValue(row.getCell(j));
				cellValue.put(j, obj);
				j++;
			}
			content.put(i, cellValue);
		}
		return content;
	}
 
	/**
	 * 
	 * 根据Cell类型设置数据
	 * 
	 * @param cell
	 * @return
	 * @author zengwendong
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					// data格式是带时分秒的：2013-7-10 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// data格式是不带带时分秒的：2013-7-10
					Date date = cell.getDateCellValue();
					cellvalue = date;
				} else {// 如果是纯数字
 
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:// 默认的Cell值
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}
 

	public static void main(String[] args) {
//		try {
//		HSSFWorkbook workbook=new HSSFWorkbook();
//		FileOutputStream out=new FileOutputStream("课程表.xls");
//		workbook.write(out);
//		out.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			String filepath = "test.xls";
			ReadExcelUtils excelReader = new ReadExcelUtils(filepath);
			// 对读取Excel表格标题测试
//			String[] title = excelReader.readExcelTitle();
//			System.out.println("获得Excel表格的标题:");
//			for (String s : title) {
//				System.out.print(s + " ");
//			}
			
			// 对读取Excel表格内容测试
			Map<Integer, Map<Integer,Object>> map = excelReader.readExcelContent();
			System.out.println("获得Excel表格的内容:");
			for (int i = 1; i <= map.size(); i++) {
//				System.out.println(map.get(i));
//				System.out.println(map.get(i).size());
				for (int j = 1; j < map.get(i).size(); j++) {
					try {
						int grade = Integer.parseInt((String) map.get(i).get(j));
						if (grade > 90) {
							System.out.print(j+"A ");
						} else if (grade > 80) {
							System.out.print(j+"B ");
						} else if (grade > 70) {
							System.out.print(j+"C ");
						} else if (grade > 60) {
							System.out.print(j+"D ");
						} else {
							System.out.print(j+"E ");
						}
//					System.out.println(grade);
					} catch (Exception e) {
						// TODO: handle exception
//						e.printStackTrace();
					}
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}

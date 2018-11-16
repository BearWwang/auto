package com.Dayu.wx.www;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class excelOperation {
	   public Date date=new Date();
	   Testr test1=new Testr();
	   String testCaseName = "";
		
	public  void writeRow(String[] inputFile) {
		try {
			Testr.testmethod();
			String result2= test1.str2;
			//#测试日志存放路径
			//path2=C\:\\testReport\\testResult\\
			String excelPath=result2+String.valueOf(dateUtil.getYear(date))+"-"+String.valueOf(dateUtil.getMonth(date))+"-"+String.valueOf(dateUtil.getDay(date))+"-"+String.valueOf(dateUtil.getHour(date))+"-"+String.valueOf(dateUtil.getMinute(date))+testCaseName+".xlsx";	
			File file = new File(excelPath);
			InputStream fis = new FileInputStream(file);
			OPCPackage opcPackage = OPCPackage.open(fis);
			Workbook wb = new XSSFWorkbook(opcPackage);
			Sheet sheet = wb.getSheetAt(0);
			sheet.setColumnWidth(1, 30 * 256);//讲第二列宽度设为30个字符宽度
			sheet.setColumnWidth(5, 100 * 256);//讲第6列宽度设为100个字符宽度
			Row row = (Row) sheet.getRow(0);
			FileOutputStream out = new FileOutputStream(excelPath);
			row = sheet.createRow((sheet.getLastRowNum() + 1));
			int rowNum = sheet.getLastRowNum();
			for (int i = 0; i < inputFile.length; i++) {
				if (i == 0) {
					Cell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
					cell.setCellValue(rowNum);
				}
				else {
					Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
				//	cell.setHyperlink((XSSFHyperlink) cell.getHyperlink());
					cell.setCellValue(inputFile[i]);//完成写入操作
				}
				}
			out.flush();
			wb.write(out);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void writeResult(String stepName, String resultMessage) {
		try {
			String[] input = new String[]{ "",stepName,null,null,"",null};
			if(resultMessage.length()!=0 && (!resultMessage.equals("条件不成立该步骤不执行")))
			{
				input[4] = "failure";
				input[5] = resultMessage; 
			}
			else
			{
				input[4] = "finished";//
				input[5] = resultMessage; 
			}
			writeRow(input);//完成写入操作
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public  void writeCheckResult(String stepName, String resultMessage, String chkRes, String actRes, String expRes)
	{
		try {
			String[] input = new String[]{ "",stepName,actRes,expRes,chkRes,null};
			if(resultMessage.length()!=0)
			{
				
				input[5] = resultMessage; 
			}
			writeRow(input);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CellStyle getStyle(Workbook workbook) {
//		
 	CellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置单元格字体
		org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont(); // 字体
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(HSSFColor.RED.index);
		headerFont.setFontName("宋体");
		style.setFont(headerFont);
		style.setWrapText(true);

		// 设置单元格边框及颜色
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setWrapText(true);
		return style;
	}

	public void setCaseName(String CaseName) 
	{
		testCaseName = CaseName;
	}
}
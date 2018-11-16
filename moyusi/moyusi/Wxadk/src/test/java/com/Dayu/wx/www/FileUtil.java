package com.Dayu.wx.www;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
//该类用于创建目录,文件和截图
public class FileUtil {
  
	public static String filePath;
	//创建目录
	public static boolean createDir(String destDirName){
		File dir=new File(destDirName);
		if(dir.exists())
		{
		//	System.out.println("创建目录"+destDirName+"失败，目标目录已经存在");
			return false;
		}
		if(dir.mkdirs())
		{
			System.out .println("创建目录"+destDirName+"成功");
			return true;
			}
			else
			{
				System.out .println("创建目录"+destDirName+"失败");
				return false;
			}
		}
	
	
	//创建文件
	public static boolean createFile(String destFileName){
		File file=new File(destFileName);
		if (file.exists()){
			System.out.println("创建单个文件"+destFileName+"失败，目标文件已存在！");
			return false;			
		}
		if (destFileName.endsWith(File.separator)){
			System.out.println("创建单个文件"+destFileName+"失败，目标文件不能为目录！");
			return false;			
		}
		//判断目标文件所在目录是否存在
		if(!file.getParentFile().exists()){
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if(!file.getParentFile().mkdirs()){
				System.out.println("创建目标文件所在目录失败！");
				return false;
			}
	}
		//创建目标文件
		try{
			if(file.createNewFile()){
				System.out.println("创建单个文件"+destFileName+"成功！");
				return true;
			}else{
				System.out.println("创建单个文件"+destFileName+"失败！");
				return false;
			}
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("创建单个文件"+destFileName+"失败！"+e.getMessage());
			return false;
		}
	}
	
	
	public static void takeTakesScreenshot(WebDriver driver) throws FileNotFoundException, IOException 
	{
		Date date=new Date();
		Testr test1=new Testr();
		test1.testmethod();
	    String result3= test1.str5;
		//创建日期名称的文件夹
	//	String picDir="F:\\测试报告\\截图日志"+String.valueOf(dateUtil.getYear(date))+"-"+String.valueOf(dateUtil.getMonth(date))+"-"+String.valueOf(dateUtil.getDay(date));
		String picDir=result3+"截图日志"+String.valueOf(dateUtil.getYear(date))+"-"+String.valueOf(dateUtil.getMonth(date))+"-"+String.valueOf(dateUtil.getDay(date));
		FileUtil.createDir(picDir);
		
	   //时间名称的截图文件
	   filePath=picDir+"\\"+String.valueOf(dateUtil.getHour(date))+"-"+String.valueOf(dateUtil.getMinute(date))+"-"+String.valueOf(dateUtil.getSecond(date))+".png";
	   
	   File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	   try {
		FileUtils.copyFile(srcFile, new File(filePath));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
//	public void newExcel(WebDriver driver)
//	{
//		Date date=new Date();
//		//创建日期名称的excel文件
//		String fileName="F:\\测试日志"+String.valueOf(dateUtil.getYear(date))+"-"+String.valueOf(dateUtil.getMonth(date))+"-"+String.valueOf(dateUtil.getDay(date));
//		FileUtil.createFile(fileName);
//	}
}
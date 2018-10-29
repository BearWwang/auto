package com.Dayu.www;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class allCaseTest {
  private static WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();
  private int totalCaseNumbers;
  private List<Object[]> records = new ArrayList<Object[]>();
  public  static String testCaseResult="";
  static Testr test1=new Testr();//读取环境配置信息：test.preperites,
  //static DesiredCapabilities capabilities;
  File allTest, singleTest;
 // private static Logger logger = Logger.getLogger(allCaseTest.class);  
@BeforeMethod
public void setUp() throws Exception {
	           Testr.testmethod();
	           // selenium 
	         System.setProperty("webdriver.chrome.driver",Testr.str6);	    
	         ChromeOptions  option = new ChromeOptions();
	        option.addArguments("disable-infobars");
              driver = new ChromeDriver(option);
              // 操作浏览器大小, 最大化
       //  driver.manage().window().maximize();
              //自定义浏览器尺寸
              driver.manage().window().setSize(new Dimension(1280,800));
              driver.get(Testr.str7); 
	     }  
@Test
public  void test2() throws FileNotFoundException, IOException, InterruptedException{
    String result3= Testr.str3;
    String result1= Testr.str1;
   allTest = new File(result3+"WEB测试用例集.xlsx");   
	if(!allTest.exists()){
		System.out.println("测试用例集文件不存在");
		return;
	}
	allCaseProcess allCP = new allCaseProcess();
	allCP.writeallHeader();//生成总体测试报告的标题行
	records = allCP.getallTests(allTest);//读取所有测试用例，返回所有用例的数据信息	
	totalCaseNumbers = records.size();
	for (int i=0; i<totalCaseNumbers; i++)
	{
	    Object[] value = records.get(i);
	    String testCaseName = (String) value[1];//用例名称
	    String whetherExec = (String) value[2];//要不要执行该测试用例
	    whetherExec.trim();
	    if (whetherExec.equals("y") ||  whetherExec.equals("Y"))
	    {
			singleTest = new File(result1+testCaseName+".xlsx");   
			System.out.println(result1+testCaseName+".xlsx");
			if(!singleTest.exists()){
				System.out.println("文件"+testCaseName+"不存在");
				return;			}		
			singleCaseProcess sCP = new singleCaseProcess();
			System.out.print("测试用例：【"+testCaseName+"】  文件读取成功!  --------"+"  准备开始测试!\n");
			Reporter.log("测试用例：【"+testCaseName+"】  文件读取成功!  --------"+"  准备开始测试!\n");			
		    sCP.processHandle(singleTest,driver,testCaseName);//执行第i个测试用例,以及该用例的测试报告的书写
			String execResult = sCP.getCaseExecResult();//获取第i个测试用例的执行结果
			allCP.writeAllResult(execResult, i);//在总体测试报告里边，写入第i个测试用例的执行结果
	    }
	}
	System.out.print("All test finished.\n");
}
  @AfterMethod
public void tearDown() throws Exception {
   driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      Assert.fail(verificationErrorString);
    }
  }
}

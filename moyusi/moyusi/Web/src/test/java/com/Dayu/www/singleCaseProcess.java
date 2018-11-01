package com.Dayu.www;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


/*
* 获取Excel文件的内容,使用Workbook方式来读取excel，Excel文件的行号和列号都是从0开始的
*/
public class singleCaseProcess {

	public static boolean a=true;
	static int width;
    static int height;
	boolean stepExec = true;
	public int caseSequence, maxWaitTime =30;
	String resultMessage = new String(""); //非检查点的步骤，写入测试报告时，保存错误信息
	String checkResult = new String(""); //检查点步骤，写入测试报告时，保存检查结果
	String actualValue = new String("");; //检查点步骤，实际值
	String expectedValue = new String("");; //检查点步骤，期望值
	String caseExecResult = "Pass";
	String phoneNumber, cardNumber, idNumber, readedText, testDataType,totalText;
	int regNewPhoneNumber = 0;
	int cunt =0;
	int  Rand = 0 ;

	excelOperation excel = new excelOperation();
	Testr test1=new Testr();
	testData TD = new testData();
    testGesture TG =new testGesture();
	allCaseProcess no = new allCaseProcess();
	private List<WebElement> bot;
    static int  ac;
    static int  ab;
public void setCaseSequence(String caseName)
{
	TD.setNowNumber(caseName);//设置“测试数据相关.xlsx”中的行序号	
}
@SuppressWarnings("deprecation")
public void processHandle(File file ,WebDriver driver, String testCaseName) throws IOException{
		try{	
			writeHeaderSingleCase(testCaseName);//写入单个测试用例执行报告的标题行
			excel.setCaseName(testCaseName);//给excel变量传入用例名称，方便在写测试报告时将用例名称作为报告名称的一部分
			TD.getColFromDataFiles(); //将数据文件中的所有列名与列号的对应关系读入哈希表中
			//appType= Testr.str12; //从配置文件test.properties获取app的类型
			InputStream inputStream = new FileInputStream(file);
			Workbook workbook = null;			 
			try {
				workbook = new XSSFWorkbook(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}//解析xlsx格式
			Sheet   sheet=workbook.getSheetAt(0);//第一个工作表
			int a = sheet.getLastRowNum();
			int b = sheet.getFirstRowNum();
			int	rowCount=a-b;
		
			for(int i=1;i<= rowCount; i++)
			{   
				resultMessage = "";
				checkResult = "";
				actualValue = "";
				expectedValue = "";				
				Row row = sheet.getRow(i);//获取行对象				
				String[] value = new String[]{"","","","","","",""};
				for(int j=0; j<7; j++)
				{
					if(row.getCell(j)==null) continue;
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);				
					value[j]=row.getCell(j).getStringCellValue().trim();//操作类型
				}
				stepExec = warpingFunctions.getIfCaseExec(driver, value[0], value[3],stepExec); //判断该步骤是否需要执行。
				if(stepExec == false) //如果不需要执行，直接打印该步骤的日志
				{
					resultMessage = "条件不成立该步骤不执行";
					excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
				}
				else //否则清空错误日志，用例步骤顺序执行
				{
					resultMessage = "";		
					SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
					Rand = new  Random().nextInt(99999999); 
					switch (value[0]){					
					case "F12":
						try
						{   
							Robot rbt = new Robot();							
							rbt.keyPress(KeyEvent.VK_F12);
				            rbt.keyRelease(KeyEvent.VK_F12);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;
					case "F5":
						try
						{  Robot rbt = new Robot();							
							rbt.keyPress(KeyEvent.VK_F5);
				            rbt.keyRelease(KeyEvent.VK_F5);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;
					case "组合键":
						try
						{     Robot rbt = new Robot();
						      //按下
				              rbt.keyPress(KeyEvent.VK_CONTROL);
				              rbt.keyPress(KeyEvent.VK_SHIFT);
				              rbt.keyPress(KeyEvent.VK_M);
				              //松开
				              rbt.keyRelease(KeyEvent.VK_CONTROL);
				              rbt.keyRelease(KeyEvent.VK_SHIFT);
				              rbt.keyRelease(KeyEvent.VK_M);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;				
					case "滑动屏幕":
						try{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							TG.swipeTo(value[1],driver);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;
					case "Web_返回":
						try{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							//返回上个页面
							driver.navigate().back();
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;
					case "滚动查找点击元素"://只能第一页查找，不能翻页查找元素
						try{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							driver.findElement(By.id(value[1]));
						
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;
						
					case "向下滑动查找元素_id"://可以翻页查找元素
						try{
							new WebDriverWait(driver, maxWaitTime);		
							testGesture.swipeToelement(value[1],driver,value[3],bot);
							System.out.println(value[3]);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;
					case "滑动查找元素_id"://可以翻页查找元素,可以进行不同方向的滑动，包括上下左右						
						try{
							new WebDriverWait(driver, maxWaitTime);
							}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;
//					case "支付截图":
//						try {
//							FileUtil.takeTakesScreenshot(driver);
//							resultMessage = FileUtil.filePath;
//							caseExecResult="fail";
//						}catch(Exception e)
//						{
//							resultMessage=e.getMessage();
//							caseExecResult="failure";
//						}
//						excel.writeResult(value[4], resultMessage);
//						break;
						case "id支付截图":
							try
							{
								WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);// 最多等待时间由maxWaitTime指定								
								if (value[2].equals(""))
								{
									wait.until(ExpectedConditions.elementToBeClickable(By.id(value[1])));								
									driver.findElement(By.id(value[1].toString())).click();
									Thread.sleep(300);										
									FileUtil.takeTakesScreenshot(driver);
								}
								else
								{   // 等待寻找页面元素
									wait.until(ExpectedConditions.elementToBeClickable(By.id(value[1])));
									//遍历相同元素
								bot = driver.findElements(By.id(value[1]));
									//根据表格定义的下标操作相应元素
									bot.get(Integer.parseInt(value[2])).click();									
							}								
							}
							catch(Exception e)
							{
								resultMessage=e.getMessage();							
								//investorLogin.invesTag="fail";
								caseExecResult="failure";
							}
												excel.writeResult(value[4], resultMessage);
							break;
					case "点击_id":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);// 最多等待时间由maxWaitTime指定								
							if (value[2].equals(""))
							{
								wait.until(ExpectedConditions.elementToBeClickable(By.id(value[1])));						
					            driver.findElement(By.id(value[1])).click();
							}
							else
							{   // 等待寻找页面元素
								wait.until(ExpectedConditions.elementToBeClickable(By.id(value[1])));
								//遍历相同元素
								List<WebElement> bot = driver.findElements(By.id(value[1]));
								//根据表格定义的下标操作相应元素
								bot.get(Integer.parseInt(value[2])).click();									
						}
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();							
							//investorLogin.invesTag="fail";
							caseExecResult="failure";
						}
			           excel.writeResult(value[4], resultMessage);
						break;
					case "点击_id(text)":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);// 最多等待时间由maxWaitTime指定							
								wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
								List<WebElement> bot = driver.findElements(By.id(value[1]));	
								for (int z= 0; z < bot.size(); z++) {								
									 if (bot.get(z).getText().contains(value[6])) {
										// System.out.println(bot.get(z).getAttribute("text")+"~~~~~~~~~~~~~~~~~~~~~~~~~~");
									bot.get(z).click();		
									break;			
						}
							}
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();							
							caseExecResult="failure";
						}
					excel.writeResult(value[4], resultMessage);
						break;
					case "点击_text":
						try
						{     
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定	
							if(value[2].equals("")){
							List<WebElement>	 bot = driver.findElements(By.id(value[1]));//driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"消费\")");	
							for (int j = 0; j <bot.size() ; j++) {		
					           if (bot.get(j).getAttribute("text").contains(value[6])) {
					        	  // System.out.println(bot.get(j).getAttribute("text")+"~~~~~~~~~~~~~~~~~~~~~~~~~~");
					        	   bot.get(j).click();	     	   
							}		
					           }}else {
					        	   List<WebElement>	 bot = driver.findElements(By.id(value[1]));//driver.findElementsByAndroidUIAutomator("new UiSelector().text(\"消费\")");	
									for (int j = 0; j < value.length; j++) {		
							           if (bot.get(Integer.parseInt(value[2])).getAttribute("text").contains(value[6])) {
							        	//   System.out.println(bot.get(Integer.parseInt(value[2])).getAttribute("text")+"~~~~~~~~~~~~~~~~~~~~~~~~~~");
							        	   bot.get(Integer.parseInt(value[2])).click();							        	   
					           }
					        	   }
									}	
							}							
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;	
					case "定位":
						try
						{
							width = driver.manage().window().getSize().width;
							height = driver.manage().window().getSize().height;
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);// 最多等待时间由maxWaitTime指定								
							if (value[2].equals(""))
							{       
								ac =(int)(width* Double.parseDouble(value[1]));
					              ab=(int)(height* Double.parseDouble(value[3]));							   					
							}						
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();		
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "点击_xpath":
						try
						{									
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);// 最多等待时间由maxWaitTime指定
							if (value[2].equals(""))
							{	
								wait.until(ExpectedConditions.elementToBeClickable((By.xpath(value[1]))));
								driver.findElement(By.xpath(value[1])).click();
							}
							else
							{
								wait.until(ExpectedConditions.elementToBeClickable((By.xpath(value[1]))));
								List<WebElement> bot = driver.findElements(By.xpath(value[1]));
									bot.get(Integer.parseInt(value[2])).click();			
								}	}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;					
					case "点击_className":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);// 最多等待时间由maxWaitTime指定
							if(value[2].equals("")){
								wait.until(ExpectedConditions.elementToBeClickable(By.className(value[1])));
								driver.findElement(By.className(value[1])).click();
							}
							else{
								wait.until(ExpectedConditions.elementToBeClickable((By.className(value[1]))));
								List<WebElement> 	bot = driver.findElements(By.className(value[1]));
								bot.get(Integer.parseInt(value[2])).click();
							}
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
						
					case "点击_name":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							if(value[2].equals("")){
								wait.until(ExpectedConditions.elementToBeClickable(By.name(value[1])));
								driver.findElement(By.name(value[1])).click();
							}
							else{
								wait.until(ExpectedConditions.elementToBeClickable(By.name(value[1])));
								List<WebElement>	bot = driver.findElements(By.name(value[1]));
								bot.get(Integer.parseInt(value[2])).click();
							}
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;	
					case "点击_文本":
						try
						{				
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);
							wait.until(ExpectedConditions.elementToBeClickable(By.linkText(value[1])));
							driver.findElement(By.linkText(value[1])).click();							
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;						
					case"清空输入框":
						try
						{
							 Robot rbt = new Robot();
						      //按下
				              rbt.keyPress(KeyEvent.VK_CONTROL);				         
				              rbt.keyPress(KeyEvent.VK_A);
				              //松开
				              rbt.keyRelease(KeyEvent.VK_CONTROL);				        
				              rbt.keyRelease(KeyEvent.VK_A);
				              //操作删除键
				              rbt.keyPress(KeyEvent.VK_BACK_SPACE);	
				              rbt.keyRelease(KeyEvent.VK_BACK_SPACE);				              	
							
						}catch (Exception e) {
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case"xpath_clear":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value[1])));			 
							if(value[2].equals("")){
								driver.findElement(By.xpath(value[1])).clear();
								
							}
							else{
								wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value[1])));
								List<WebElement> bot = driver.findElements(By.xpath(value[1]));	
								bot.get(Integer.parseInt(value[2])).clear();
							}	
							
						}catch (Exception e) {
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
						
					case "上传图片":
						try
						{
							Runtime.getRuntime().exec(value[3]);						
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "输入_xpaths手机号":
						try
						{ 
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value[1])));
							if(value[2].equals("")){
								driver.findElement(By.xpath(value[1])).clear();
								driver.findElement(By.xpath(value[1])).sendKeys(value[3]+Rand);
							}
							else{
								wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value[1])));
								List<WebElement> bot = driver.findElements(By.xpath(value[1]));	
								bot.get(Integer.parseInt(value[2])).sendKeys(value[3]+Rand);
							}
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
						
					case "输入_xpath":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value[1])));
							if(value[2].equals("")){
								driver.findElement(By.xpath(value[1])).clear();
								driver.findElement(By.xpath(value[1])).sendKeys(value[3]);
							}
							else{
								wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value[1])));
								List<WebElement> bot = driver.findElements(By.xpath(value[1]));	
								bot.get(Integer.parseInt(value[2])).sendKeys(value[3]);
							}
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
						
					case "输入_id":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							if(value[2].equals("")){
								//先清空，再输入
								driver.findElement(By.id(value[1])).clear();
								driver.findElement(By.id(value[1])).sendKeys(value[3]);						
							}
							else{
								List<WebElement> 	bot = driver.findElements(By.id(value[1]));
								bot.get(Integer.parseInt(value[2])).clear();
								bot.get(Integer.parseInt(value[2])).sendKeys(value[3]);
							}							
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "输入_classname门店":
						//新增门店名称+当前时间”时分秒“
						try
						{  							
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定				
						if(value[2].equals("")){
							wait.until(ExpectedConditions.elementToBeClickable(By.className(value[1])));
							driver.findElement(By.className(value[1])).clear();
							driver.findElement(By.className(value[1])).sendKeys(value[3]+"-"+dateFormat.format(new Date()));
						}
						else{
							wait.until(ExpectedConditions.elementToBeClickable((By.className(value[1]))));
							List<WebElement> 	bot = driver.findElements(By.className(value[1]));
							bot.get(Integer.parseInt(value[2])).clear();
							bot.get(Integer.parseInt(value[2])).sendKeys(value[3]+"-"+dateFormat.format(new Date()));
						}}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "输入_classname名称":
						//新增门店名称+当前时间”时分秒“
						try
						{  							
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定				
						if(value[2].equals("")){
							wait.until(ExpectedConditions.elementToBeClickable(By.className(value[1])));
							driver.findElement(By.className(value[1])).clear();
							driver.findElement(By.className(value[1])).sendKeys(value[3]+"-"+dateFormat.format(new Date()));
						}
						else{
							wait.until(ExpectedConditions.elementToBeClickable((By.className(value[1]))));
							List<WebElement> 	bot = driver.findElements(By.className(value[1]));
							bot.get(Integer.parseInt(value[2])).clear();
							bot.get(Integer.parseInt(value[2])).sendKeys(value[3]+"-"+dateFormat.format(new Date()));
						}}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "输入_classname":
						try
						{  
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定				
						if(value[2].equals("")){
							wait.until(ExpectedConditions.elementToBeClickable(By.className(value[1])));
							driver.findElement(By.className(value[1])).clear();
							driver.findElement(By.className(value[1])).sendKeys(value[3]);
						}
						else{
							wait.until(ExpectedConditions.elementToBeClickable((By.className(value[1]))));
							List<WebElement> 	bot = driver.findElements(By.className(value[1]));
							bot.get(Integer.parseInt(value[2])).clear();
							bot.get(Integer.parseInt(value[2])).sendKeys(value[3]);
						}}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "输入_classname时间":
						try
						{ 	
							//excel 时间格式化+计算机时间元年 
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定	
							//获取时间原点 1900年
							Calendar calendar = new GregorianCalendar(1900,0,-1);  
							Date d = calendar.getTime();
							//原点 + 预计经历天数
							Date dd = DateUtils.addDays(d,Integer.valueOf(value[3]));  	
							//时间格式化
							SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");																	
						if(value[2].equals("")){
							wait.until(ExpectedConditions.elementToBeClickable(By.className(value[1])));
							driver.findElement(By.className(value[1])).clear();							
							driver.findElement(By.className(value[1])).sendKeys(dateFormat1.format(dd));
						}
						else{
							wait.until(ExpectedConditions.elementToBeClickable((By.className(value[1]))));
							List<WebElement> 	bot = driver.findElements(By.className(value[1]));
							bot.get(Integer.parseInt(value[2])).clear();							
							bot.get(Integer.parseInt(value[2])).sendKeys(dateFormat1.format(dd));
						}}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "输入_classname随机数":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定				
							
							if(value[2].equals("")){
							wait.until(ExpectedConditions.elementToBeClickable(By.className(value[1])));
							driver.findElement(By.className(value[1])).clear();							
							driver.findElement(By.className(value[1])).sendKeys(value[3]+Rand);
						}
						else{
							wait.until(ExpectedConditions.elementToBeClickable((By.className(value[1]))));
							List<WebElement> 	bot = driver.findElements(By.className(value[1]));
							bot.get(Integer.parseInt(value[2])).clear();
							bot.get(Integer.parseInt(value[2])).sendKeys(value[3]+Rand);
						}}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					case "输入_文本":     
						try
						{							
							String tmpReadedText;
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							tmpReadedText = TD.getTestData(value[3]);//从测试数据文件中读取要检查的文本，作为期望值。
							driver.findElement(By.id(value[1])).sendKeys(tmpReadedText);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;							
					case "回退":
						try
						{  
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1]))).click();		      													
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;    
					case "读取信息_id":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							if(value[2].equals("")){
								readedText = driver.findElement(By.id(value[1])).getText(); //要获取的页面元素的文本内容
							}
							else{
								bot = driver.findElements(By.id(value[1]));
								readedText = bot.get(Integer.parseInt(value[2])).getText();
							}
							
							testDataType = value[3]; //要获取的文本内容的类型，value[3]中的值应该与“测试相关数据.xlsx"文档中的标题行的信息相匹配;
							TD.setTestData(testDataType, readedText);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;	
					case "读取信息_xpath":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value[1])));
							if(value[2].equals("")){
								readedText = driver.findElement(By.xpath(value[1])).getAttribute("name"); //要获取的页面元素的content-desc里的内容
							}
							else{
								List<WebElement> bot = driver.findElements(By.id(value[1]));
								readedText = bot.get(Integer.parseInt(value[2])).getAttribute("name");
							}
							
							testDataType = value[3]; //要获取的文本内容的类型，value[3]中的值应该与“测试相关数据.xlsx"文档中的标题行的信息相匹配;
							TD.setTestData(testDataType, readedText);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;	
					case "读取局部信息_id":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							if(value[2].equals("")){
								readedText = driver.findElement(By.id(value[1])).getText(); //要获取的页面元素的文本内容
							}
							else{
								bot = driver.findElements(By.id(value[1]));
								readedText = bot.get(Integer.parseInt(value[2])).getText();
							}
							
							testDataType = value[3]; //要获取的文本内容的类型，value[3]中的值应该与“测试相关数据.xlsx"文档中的标题行的信息相匹配;
							String filteredText = warpingFunctions.getFiltedText(readedText, value[5]);
							TD.setTestData(testDataType, filteredText);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;	
					case "读取局部信息_xpath":
						try
						{
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value[1])));
							if(value[2].equals("")){
								readedText = driver.findElement(By.xpath(value[1])).getAttribute("name"); //要获取的页面元素的content-desc里的内容
							}
							else{
								List<WebElement> bot = driver.findElements(By.id(value[1]));
								readedText = bot.get(Integer.parseInt(value[2])).getAttribute("name"); //要获取的页面元素的content-desc里的内容
							}
							
							testDataType = value[3]; //要获取的文本内容的类型，value[3]中的值应该与“测试相关数据.xlsx"文档中的标题行的信息相匹配;
							String filteredText = warpingFunctions.getFiltedText(readedText, value[5]);
							TD.setTestData(testDataType, filteredText);
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;	
					case "检查点_xpath":
						try
						{ 
							driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(value[1])));
						actualValue = driver.findElement(By.xpath(value[1])).getText();
						 expectedValue  = value[5];
						checkResult=warpingFunctions.verifyTest(actualValue,expectedValue);
						if(checkResult=="fail")
							{
								FileUtil.takeTakesScreenshot(driver);
								resultMessage = FileUtil.filePath;
								caseExecResult="fail";
							 }
						 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
					case "检查点_url":
						try 
						{       
							driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							actualValue = driver.getCurrentUrl();
							 expectedValue  = value[5];
							checkResult=warpingFunctions.verifyTest(actualValue,expectedValue);
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
								 }						
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;	
					case "检查点_class":
						try
						{
						WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
						wait.until(ExpectedConditions.presenceOfElementLocated(By.className(value[1])));
						 actualValue = driver.findElement(By.className(value[1])).getAttribute("className");
						 expectedValue  = value[5];
						checkResult=warpingFunctions.verifyTest(actualValue,expectedValue);
						if(checkResult=="fail")
							{
								FileUtil.takeTakesScreenshot(driver);
								resultMessage = FileUtil.filePath;
								caseExecResult="fail";
							 }
						 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;				
					case "检查点_id":
						try
						{  // 新改技术
							Thread.sleep(2000);
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
						wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
						if(value[2].equals("")){	
						//	System.out.println(driver.findElementById(value[1]).getAttribute("resourceId") +"text");
							 actualValue = driver.findElement(By.id(value[1])).getAttribute("resourceId");
						}
						else{ 
							List<WebElement> 	bot = driver.findElements(By.id(value[1]));						
							actualValue = bot.get(Integer.parseInt(value[2])).getAttribute("resourceId");
						}	 
						expectedValue  = value[5];
						checkResult=warpingFunctions.verifyTest(actualValue,expectedValue);
						if(checkResult=="fail")
							{
								FileUtil.takeTakesScreenshot(driver);
								resultMessage = FileUtil.filePath;
								caseExecResult="fail";
							 }
						 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;	
					case "检查点_id不存在":
						try
						{
							String checkedText;
							String pageSourceString;
							driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							pageSourceString=driver.getPageSource();  //获取页面pagesource
							if(value[1].equals("")){
								checkedText = driver.findElement(By.id(value[3])).getAttribute("resourceId"); //如果未传入取值类型，需要在value[3]中直接传入要检查的文本，否则，从测试数据文件中读取要检查的文本，作为期望值。
							}
							else{
								checkedText = TD.getTestData(value[1]);
							}							
							actualValue = checkedText;
							expectedValue = value[3]; //默认情况下，假定校验成功，在期望结果与实际结果一样													
							checkResult=warpingFunctions.verifyContainTest(pageSourceString,checkedText,"n");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = "";
								 }
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
							actualValue = "";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
						case "检查点_idchecked":
							try
							{  // 新改技术			
								driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							if(value[2].equals("")){	
							 actualValue = driver.findElement(By.id(value[1])).getAttribute("checked");
							}
							else{
								bot = driver.findElements(By.id(value[1]));							
								actualValue = bot.get(Integer.parseInt(value[2])).getAttribute("checked");
							}		 
							expectedValue  = value[5];
							checkResult=warpingFunctions.verifyContainTest(actualValue,expectedValue,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
								 }
							 }
							catch(Exception e)
							{
								resultMessage=e.getMessage();
								checkResult = "failure";
								caseExecResult="failure";
							}
							excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
							break;
						case "滚动条操作":
							try  {
								((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");  
							}
							catch(Exception e)
							{
								resultMessage=e.getMessage();
								checkResult = "failure";
								caseExecResult="failure";
							}
							excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
							break;
						case "检查点_classchecked":
							try
							{  // 新改技术
								driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.className(value[1])));
							if(value[2].equals("")){	
								 actualValue = driver.findElement(By.className(value[1])).getAttribute("aria-checked");
							}
							else{
								bot = driver.findElements(By.className(value[1]));							
								actualValue = bot.get(Integer.parseInt(value[2])).getAttribute("aria-checked");
								System.out.println(bot.get(Integer.parseInt(value[2])).getAttribute("text") +"checked页面元素");
							}		 
							expectedValue  = value[5];							
							checkResult=warpingFunctions.verifyContainTest(actualValue,expectedValue,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
								 }
							 }
							catch(Exception e)
							{
								resultMessage=e.getMessage();
								checkResult = "failure";
								caseExecResult="failure";
							}
							excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
							break;
						case "检查点_enabled":
							try
							{  // 新改技术			
								driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							if(value[2].equals("")){	
								 actualValue = driver.findElement(By.id(value[1])).getAttribute("enabled");
							}
							else{
								bot = driver.findElements(By.id(value[1]));							
								actualValue = bot.get(Integer.parseInt(value[2])).getAttribute("enabled");
							}		 
							expectedValue  = value[5];
							checkResult=warpingFunctions.verifyContainTest(actualValue,expectedValue,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
								 }
							 }
							catch(Exception e)
							{
								resultMessage=e.getMessage();
								checkResult = "failure";
								caseExecResult="failure";
							}
							excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
							break;
						case "检查点_selected":
							try
							{  // 新改技术			
								driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
							if(value[2].equals("")){	
								 actualValue = driver.findElement(By.id(value[1])).getAttribute("selected");
							}
							else{
								bot = driver.findElements(By.id(value[1]));							
								actualValue = bot.get(Integer.parseInt(value[2])).getAttribute("selected");
							}		 
							expectedValue  = value[5];
							checkResult=warpingFunctions.verifyContainTest(actualValue,expectedValue,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
								 }
							 }
							catch(Exception e)
							{
								resultMessage=e.getMessage();
								checkResult = "failure";
								caseExecResult="failure";
							}
							excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
							break;
						case "检查点_style":
							try
							{  // 新改技术			
							if(value[2].equals("")){	
								 actualValue = driver.findElement(By.className(value[1])).getAttribute("style");
							}
							else{
								bot = driver.findElements(By.className(value[1]));			
								actualValue = bot.get(Integer.parseInt(value[2])).getAttribute("style");
							}		 
							expectedValue  = value[5];
							checkResult=warpingFunctions.verifyContainTest(actualValue,expectedValue,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
								 }
							 }
							catch(Exception e)
							{
								resultMessage=e.getMessage();
								checkResult = "failure";
								caseExecResult="failure";
							}
							excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
							break;
					case "检查点_文本存在":
						try
						{			
							Thread.sleep(2000);
							driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
							String checkedText;
							String pageSourceString;								
							pageSourceString=driver.getPageSource();  //获取页面pagesource									
							if(value[1].equals("")){
								checkedText = value[3]; //如果未传入取值类型，需要在value[3]中直接传入要检查的文本，否则，从测试数据文件中读取要检查的文本，作为期望值。
							}
							else{  								
								bot = driver.findElements(By.className(value[1]));	
								System.out.println(bot.get(Integer.parseInt(value[2])).getAttribute("text"));
								checkedText = bot.get(Integer.parseInt(value[2])).getAttribute("text");
							}
							actualValue = checkedText;
							expectedValue = value[5]; //默认情况下，假定校验成功，在期望结果与实际结果一样												
							checkResult=warpingFunctions.verifyContainTest(pageSourceString,checkedText,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = "";
								 }
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
							actualValue = "";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
					case "检查点_文本不存在":
						try						
						{  	   Thread.sleep(2000);
							driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);						
							String checkedText;
							String pageSourceString;
							pageSourceString=driver.getPageSource();  //获取页面pagesource						
							if(value[1].equals("")){
								checkedText = value[3]; //如果未传入取值类型，需要在value[3]中直接传入要检查的文本，否则，从测试数据文件中读取要检查的文本，作为期望值。
							}
							else{
								checkedText = TD.getTestData(value[1]);
							}
							actualValue = "";
							expectedValue = ""; //默认情况下，假定校验成功，在期望结果与实际结果一样													
							checkResult=warpingFunctions.verifyContainTest(pageSourceString,checkedText,"n");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = checkedText;
								 }
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
							actualValue = "";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;	
					case "检查点_id_字段包含":
						try
						{
							String bigText, smallText;							
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);			
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[3])));
							if(value[2].equals("")){
								bigText = driver.findElement(By.id(value[1])).getAttribute("resourceId");						
							}
							else{
								bot = driver.findElements(By.id(value[1]));
								bigText = bot.get(Integer.parseInt(value[2])).getAttribute("resourceId");
							}
							if(value[3].equals("")) //value[3]中可以指定从数据文件读取的内容；如果value[3]为空，则要验证被包含的字段，直接从value[5]读取
							{
	                            smallText=value[5];
	                        }
	                        else
	                        {
								smallText = TD.getTestData(value[3]);
							}
							actualValue = smallText;
							expectedValue = smallText; //默认情况下，假定校验成功，在期望结果与实际结果一样													
							checkResult=warpingFunctions.verifyContainTest(bigText,smallText,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = bigText;
								 }
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
							actualValue = "";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
					case "检查点_xpath_字段包含":
						try
						{
							String bigText, smallText;
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);			
							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[3])));
							if(value[2].equals("")){
								bigText = driver.findElement(By.xpath(value[1])).getAttribute("name"); //获取content desc里的信息
							}
							else{
								List<WebElement> bot = driver.findElements(By.id(value[1]));
								bigText = bot.get(Integer.parseInt(value[2])).getAttribute("name");
							}
							if(value[3].equals("")) //value[3]中可以指定从数据文件读取的内容；如果value[3]为空，则要验证被包含的字段，直接从value[5]读取
							{
	                            smallText=value[5];
	                        }
	                        else
	                        {
								smallText = TD.getTestData(value[3]);
							}
							actualValue = smallText;
							expectedValue = smallText; //默认情况下，假定校验成功，在期望结果与实际结果一样													
							checkResult=warpingFunctions.verifyContainTest(bigText,smallText,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = bigText;
								 }
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
							actualValue = "";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
					case "检查点_id_字段不包含":
						try
						{
							String bigText, smallText;
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							if(value[2].equals("")){
								bigText = driver.findElement(By.id(value[1])).getAttribute("text");
							}
							else{
								bot = driver.findElements(By.id(value[1]));
								bigText = bot.get(Integer.parseInt(value[2])).getAttribute("text");
							}							
							if(value[3].equals("")) //value[3]中可以指定从数据文件读取的内容；如果value[3]为空，则要验证被包含的字段，直接从value[5]读取
							{
	                            smallText=value[5];
	                        }
	                        else
	                        {
								smallText = TD.getTestData(value[3]);
							}
							actualValue = "";
							expectedValue = ""; //默认情况下，假定校验成功，在期望结果与实际结果一样													
							checkResult=warpingFunctions.verifyContainTest(bigText,smallText,"n");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = smallText;
								 }
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
							actualValue = "";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
					case "检查点_xpath_字段不包含":
						try
						{
							String bigText, smallText;
							Thread.sleep(2000);
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							if(value[2].equals("")){
								bigText = driver.findElement(By.xpath(value[1])).getAttribute("name"); //获取content desc里的信息
							}
							else{
								List<WebElement> bot = driver.findElements(By.id(value[1]));
								bigText = bot.get(Integer.parseInt(value[2])).getAttribute("name");
							}
							if(value[3].equals("")) //value[3]中可以指定从数据文件读取的内容；如果value[3]为空，则要验证被包含的字段，直接从value[5]读取
							{
	                            smallText=value[5];
	                        }
	                        else
	                        {
								smallText = TD.getTestData(value[3]);
							}
							actualValue = "";
							expectedValue = ""; //默认情况下，假定校验成功，在期望结果与实际结果一样													
							checkResult=warpingFunctions.verifyContainTest(bigText,smallText,"n");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = smallText;
								 }
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
					case"检查点_id_内容为空"	:
						try {
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);
							WebElement wen;
							if (value[2].equals(""))
							{
								 wen = driver.findElement(By.id(value[1]));
								wen.equals("");
								checkResult="pass";
							}
							else
							{
								bot = driver.findElements(By.id(value[1]));
								 wen = bot.get(Integer.parseInt(value[2]));
								 wen.equals("");
								checkResult="pass";
								}							
						} catch (Exception e) {
							resultMessage=e.getMessage();
							checkResult="fail";
							caseExecResult="failure";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;
//					case "检查点_id_数值":
//						try
//						{
//							String bigText, smallText;
//							Thread.sleep(2000);
//							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定	
//							wait.until(ExpectedConditions.presenceOfElementLocated(By.id(value[1])));
//							if(value[2].equals("")){
//								bigText = driver.findElement(By.id(value[1])).getAttribute("text");
//							}
//							else{
//								bot = driver.findElements(By.id(value[1]));
//								bigText = bot.get(Integer.parseInt(value[2])).getAttribute("text");
//							}							
//							smallText = TD.computAndGetData(value[3]);
//							actualValue = smallText;
//							expectedValue = smallText; //默认情况下，假定校验成功，在期望结果与实际结果一样
//													
//							checkResult=warpingFunctions.verifyContainTest(bigText,smallText,"y");
//							if(checkResult=="fail")
//							{
//								FileUtil.takeTakesScreenshot(driver);
//								resultMessage = FileUtil.filePath;
//								caseExecResult="fail";
//								actualValue = "";
//							 }
//						}
//						catch(Exception e)
//						{
//							resultMessage=e.getMessage();
//							checkResult = "failure";
//							caseExecResult="failure";
//							actualValue = "";
//						}
//						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
//						break;
					case "检查点_id_比较":
						try
						{
							int comment1, comment2,totalString;
							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
							comment1 = Integer.parseInt(TD.getTestData(value[1])) ;//获取评论数前的值
							comment2=Integer.parseInt(TD.getTestData(value[3]));//获取评论数后的值
							totalString = comment1 + 1 ;							
							int actualValue = totalString;
							int expectedValue =totalString ; //默认情况下，假定校验成功，在期望结果与实际结果一样
							checkResult=warpingFunctions.comment(comment2, totalString);
								if (checkResult == "fail") {
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
									actualValue = totalString;									
								}
						}
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							checkResult = "failure";
							caseExecResult="failure";
							actualValue = "";
						}
						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
						break;						
//					case "检查点_xpath_数值":
//						try
//						{
//							String bigText, smallText;
//							Thread.sleep(2000);
//							WebDriverWait wait = new WebDriverWait(driver, maxWaitTime);//最多等待时间由maxWaitTime指定
//							if(value[2].equals("")){
//								bigText = driver.findElement(By.xpath(value[1])).getAttribute("name"); //获取content desc里的值
//							}
//							else{
//								List<WebElement> bot = driver.findElements(By.id(value[1]));
//								bigText = bot.get(Integer.parseInt(value[2])).getAttribute("name");
//							}
//							
//							smallText = TD.computAndGetData(value[3]);
//							actualValue = smallText;
//							expectedValue = smallText; //默认情况下，假定校验成功，在期望结果与实际结果一样
//													
//							checkResult=warpingFunctions.verifyContainTest(bigText,smallText,"y");
//							if(checkResult=="fail")
//							{
//								FileUtil.takeTakesScreenshot(driver);
//								resultMessage = FileUtil.filePath;
//								caseExecResult="fail";
//								actualValue = "";
//							 }
//						}
//						catch(Exception e)
//						{
//							resultMessage=e.getMessage();
//							checkResult = "failure";
//							caseExecResult="failure";
//							actualValue = "";
//						}
//						excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
//						break;
					case "等待下载完成":
						try
						{
							int waitItem = 0;
							String pageSourceString;
							pageSourceString=driver.getPageSource();  //获取页面pagesource
							checkResult = "fail";
							while (waitItem < 24 && checkResult=="fail")
							{
								Thread.sleep(5000);
								checkResult=warpingFunctions.verifyContainTest(pageSourceString,value[3],"y");
								waitItem = waitItem + 1;
								pageSourceString=driver.getPageSource();  //再次获取页面pagesource
							}							
							if(checkResult=="fail") //
								{
									resultMessage = "下载时间超长";
								}
							 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;					
						
					case "检查点_xpath对比":
						try {							
							if (value[2].equals("")) {
								actualValue=driver.findElement(By.xpath(value[1])).getText();								
							} else {
								bot = driver.findElements(By.xpath(value[1]));			
							actualValue = bot.get(Integer.parseInt(value[2])).getText();
							}							
							expectedValue  = value[5];
						//	System.out.println("文本值~~~~~~~~~~~~~~~~~~~~"+actualValue);
							checkResult=warpingFunctions.verifyContainTest(actualValue,expectedValue,"y");
							if(checkResult=="fail")
								{
									FileUtil.takeTakesScreenshot(driver);
									resultMessage = FileUtil.filePath;
									caseExecResult="fail";
								 }
							 }
							catch(Exception e)
							{
								resultMessage=e.getMessage();
								checkResult = "failure";
								caseExecResult="failure";
							}
							excel.writeCheckResult(value[4], resultMessage, checkResult, actualValue, expectedValue);
							break;
					case "对比_文本":
						try {
							String except,real;
							String strcontrast=value[3];
							String[] strs=strcontrast.split("，");
							except=TD.getTestData(strs[0]);
							real=TD.getTestData(strs[1]);
							if (!except.equals(real)) {
								resultMessage="对比内容不一致";
							}							
						} catch (Exception e) {
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);//写入单个测试用例单个步骤的执行结果
						break;	
					case "等待时间":
						try
						{
							Thread.sleep(Integer.parseInt(value[1])); 
						 }
						catch(Exception e)
						{
							resultMessage=e.getMessage();
							caseExecResult="failure";
						}
						excel.writeResult(value[4], resultMessage);
						break;
					}					
					 if(resultMessage.length()==0)
					 {
					   	System.out.print(dateUtil.getCurrentTime()+"  编号  "+i+":  ----------"+value[4].toString()+"----------成功跑通"+"\r");
						Reporter.log(dateUtil.getCurrentTime()+"  编号  "+i+":  ----------"+value[4].toString()+"----------成功跑通"+"\r");
					 }
					 else{
					   	System.out.print(dateUtil.getCurrentTime()+"  编号  "+i+":  ----------"+value[4].toString()+"----------执行失败"+"\r");
					   	Reporter.log(dateUtil.getCurrentTime()+"  编号  "+i+":  ----------"+value[4].toString()+"----------执行失败"+"\r");
						caseExecResult="fail"; 						
					 }
				}				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
						//e.getMessage();
						e.printStackTrace();
						}
				if(caseExecResult.equals("fail"))
				{
					continue;
				}
			}	
			System.out.println("测试用例：【"+testCaseName+"】  全部执行结束！！！");
			//head.setUp();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
public String getCaseExecResult()
{
	return caseExecResult;
}	
@SuppressWarnings("deprecation")
public void writeHeaderSingleCase(String caseName) throws IOException {
	 Testr.testmethod();
	 String result2= Testr.str2;
	 String excelPath=result2+String.valueOf(dateUtil.getYear(excel.date))+"-"+String.valueOf(dateUtil.getMonth(excel.date))+"-"+String.valueOf(dateUtil.getDay(excel.date))+"-"+String.valueOf(dateUtil.getHour(excel.date))+"-"+String.valueOf(dateUtil.getMinute(excel.date))+caseName+".xlsx";	
	Workbook workbook = new XSSFWorkbook();
	Sheet sheet = workbook.createSheet("testdata1");
	FileOutputStream outputStream = new FileOutputStream(excelPath);
	try {
		Row row0 = sheet.createRow(0);
		String[] headers = new String[] { "编号", "检查点", "实际结果", "预期结果","检查结果", "错误描述" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell_1 = row0.createCell(i, Cell.CELL_TYPE_STRING);		
			CellStyle style = excel.getStyle(workbook);
			cell_1.setCellStyle(style);
			cell_1.setCellValue(headers[i]);
			sheet.autoSizeColumn(i);
		}
       workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		System.out.print("测试用例日志创建成功。\n");
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
		


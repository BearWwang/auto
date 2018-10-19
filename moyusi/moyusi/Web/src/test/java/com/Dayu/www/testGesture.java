
package com.Dayu.www;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class testGesture {
	static int width;
    static int height;
    static String actualparameter;
    static String actualelement;
    static String returnMessage = "";
	private Object fisrtpage;
    
    //向左右上下滑动屏幕
    public void swipeTo(String actualparameter, WebDriver driver) throws InterruptedException {
    	width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
    	switch (actualparameter)
    	{
    	case "向右滑动":
    		Thread.sleep(3000);    	
    		((JavascriptExecutor) driver).executeScript("document.documentElement.scrollLeft="+ "1000");  
    		break;
    	case"向上滑动":
    		((JavascriptExecutor) driver).executeScript("document.documentElement.scrollTop=0");
    		break;
    	case "向下滑动":
    		Thread.sleep(3000);
    		((JavascriptExecutor) driver).executeScript("document.documentElement.scrollTop="+ "500");  
    		Thread.sleep(1000);
    		break;
    	default :
    		break;
    	}	
    }
    //向下滑动查找到某元素为止，可以翻页查找
    public static void swipeToelement(String actualelement, WebDriver driver,String actualparameter,List<WebElement> bot) throws InterruptedException {
    	width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
    	boolean a = false;
    	int counter = 0;
    	String totalText=driver.getPageSource();  //滑动前获取pagesource			
		while(!a && counter <10){ //如果找不到，最多找10遍
			if(totalText.contains(actualparameter))
			{
			   bot = driver.findElements(By.id(actualelement));
			   for(int i = 0; i<bot.size(); i++ ){				 
				   System.out.print(bot.get(i).getText()+i+'\n');
				   if(bot.get(i).getAttribute("text").equals(actualparameter)){
					   System.out.print("找到"+bot.get(i).getText()+"元素"+'\n');   
					   bot.get(i).click();
					   a = true;
					   break;
				   }		  
			   }
			}					   
		   if(a == false){
			    System.out.print("没有找到"+actualparameter+"元素!"+'\n');
		        Thread.sleep(1000);
		   }
		   counter = counter +1;
		   totalText=driver.getPageSource();  //重新获取pagesource
		}
     }
    //滑动查找到某元素为止，可以翻页查找,可以通过mode参数配置向上下左右四个不同方向进行滑动
    public static void swipeToelementAllD(String actualelement, WebDriver driver,String actualparameter,List<WebElement> bot,String mode) throws InterruptedException {
    	width = driver.manage().window().getSize().width;
        height = driver.manage().window().getSize().height;
    //    WebElement fisrtpage=driver.findElements("android.widget.FrameLayout");
    	boolean a = false;
    	int count=0;
		while(!a && count<10){
			bot = driver.findElements(By.id(actualelement));
		   for(int i = 0; i<bot.size(); i++ ){
			   String proTitle = bot.get(i).getText();
			   System.out.print(proTitle+i+'\n');
			   if(proTitle.contains(actualparameter)){
				   System.out.print("找到"+proTitle+'\n');   
				   a = true;
				   break;
			   }		  
		   }		   
		   if(a == false){
			    System.out.print("没有找到"+actualparameter+"!"+'\n');
				Thread.sleep(3000);
				switch (mode) {
				case "向下":
		//			  new TouchAction(driver).press(fisrtpage,width/2,height*8/10).waitAction(Duration.ofMillis(1000)).moveTo(fisrtpage,width/2,height*2/10).release().perform();
					
					break;					
				case "向上":
			//		 new TouchAction(driver).press(fisrtpage,width/2,height*3/10).waitAction(Duration.ofMillis(1000)).moveTo(fisrtpage,width/2,height*8/10).release().perform();	
				 
					break;
				case "向左":
				//	  new TouchAction(driver).press(fisrtpage,width*8/10,height/2).waitAction(Duration.ofMillis(1000)).moveTo(fisrtpage,width*1/10,height/2).release().perform();					
				
					break;
				case "向右":
			//		  new TouchAction(driver).press(fisrtpage,width*1/10,height/2).waitAction(Duration.ofMillis(1000)).moveTo(fisrtpage,width*8/10,height/2).release().perform();

					break;
				default:
					break;
				}
		        
		        Thread.sleep(1000);
		   }
		   count++;
		}
		if(count == 10)
		{
			returnMessage = "没找到要点击的文本信息";
		}
     }

    
    //滑动查找到某元素为止，并点击该元素；可以翻页查找
    public static void swipeAndClick(String actualelement, WebDriver driver,String actualparameter,String direction) throws InterruptedException 
    {
    	width = driver.manage().window().getSize().width;
        height =  driver.manage().window().getSize().height;
    	boolean findZhangJie = false;
    	int counter = 0;
    	List<WebElement> bot;
    	String totalText=driver.getPageSource();  //滑动前获取pagesource
		while(!findZhangJie && counter <30){ //如果找不到，最多找10遍
			if(totalText.contains(actualparameter))
			{
				bot = driver.findElements(By.id(actualelement));
				   for(int i = 0; i<bot.size(); i++ ){
					   String proTitle = bot.get(i).getText();
					   if(proTitle.contains(actualparameter))
					   {
						   System.out.print("找到"+proTitle+'\n');   
						   bot.get(i).click();
						   findZhangJie = true;
						   //driver.swipe(width / 2, height*8/10, width / 2, height*5/10, 5000); //找到元素以后继续往下滑动一下，不然可能找不到标题
						   break;
					   }		  
				   }
			}
		   if(findZhangJie == false){
			    System.out.print("没有找到"+actualparameter+"!"+'\n');
				Thread.sleep(3000);
				if(direction.equals("down"))
				{
				//	 new TouchAction(driver).press(width/2,height*3/4).waitAction(Duration.ofMillis(1000)).moveTo(width/2,height/4).release().perform();
				//	driver.swipe(width / 2, height*4/10, width / 2, height*9/10, 5000); //向上滑
				}
				else
				{   
				//	 new TouchAction(driver).press(width/2,height/4).waitAction(Duration.ofMillis(1000)).moveTo(width/2,height*3/4).release().perform();
				//	driver.swipe(width / 2, height*9/10, width / 2, height*4/10, 5000); //向下滑
				}	
		        Thread.sleep(1000);
		   }
		   totalText=driver.getPageSource();  //重新获取pagesource
		   counter = counter +1;
		}
		if(counter == 10)
		{
			returnMessage = "没找到要点击的文本信息";
		}
     }
    //轻触屏幕中央，弹出阅读器的设置页面
//    public static void getReadSet(AndroidDriver driver, String appType, String oprType)
//    {
//    	width = driver.manage().window().getSize().width;
//        height = driver.manage().window().getSize().height;
//    	boolean setDisplayed = false;
//    	int counter = 0;
//    	String displayedText="",displayedText1 = "不可能出现的字符串",displayedText2 = "不可能出现的字符串2";
//    	String totalText;
//    	returnMessage = "";
//    	if(!oprType.equals("")) //如果传进来的value【3】中有赋值，表示要取消设置页面
//    	{
//    		new TouchAction(driver).tap(width/2, height/2).perform();
//    		//driver.tap(1, width/2, height/2, 500); //轻点屏幕中央
//    	}
//    	else //如果传进来的value[3]中为空，点击屏幕中央直至出现设置页面
//    	{
//	    	if(appType.equalsIgnoreCase("ZS")) //获取弹出设置洁面后页面上应该能显示的字段
//			{
//	    		displayedText = "目录";
//	    		displayedText1 = "退出朗读"; //对于追书来说，有可能弹出页面显示的信息不一样，所以不同的情况都要考虑
//	    		displayedText2 = "减速 -";
//			}
//	    	else if(appType.equalsIgnoreCase("MHD"))
//			{
//				displayedText = "目录";
//			}
//			else if(appType.equalsIgnoreCase("KJ"))
//			{
//				displayedText = "下一章";
//				displayedText1 = "退出朗读"; //对于开卷来说，有可能弹出页面显示的信息不一样，所以不同的情况都要考虑
//				displayedText2 = "放 弃";
//			}
//			else
//			{
//				System.out.print("未设定app类型");
//			}
//	    	try{
//	    		new TouchAction(driver).tap(width/2, height/2).perform();
//	    	//	driver.tap(1, width/2, height/2, 500); //轻点屏幕中央
//		    	totalText=driver.getPageSource();  
//			}
//			catch(Exception e)
//			{
//				totalText=driver.getPageSource();  
//			}
//
//	    	
//			while(!setDisplayed && counter <6){ //如果弹不出来，最多弹6遍
//				if(totalText.contains(displayedText) || totalText.contains(displayedText1)||totalText.contains(displayedText2))
//				{
//				   setDisplayed = true;
//				   break;
//				}
//				else
//				{
//			        try{
//			        	Thread.sleep(3000);
//			        	new TouchAction(driver).tap(width/2, height/2).perform();
//			    //		driver.tap(1, width/2, height/2, 500); //轻点屏幕中央
//						totalText=driver.getPageSource();
//					}
//					catch(Exception e)
//					{
//						totalText=driver.getPageSource();  
//					}
//					counter = counter +1;
//			   }
//			}
//			if(counter == 6)
//			{
//				returnMessage = "未能弹出设置界面";
//			}
//    	}
//
//    }
    public String getReturnMessage()
    {
    	return returnMessage;
    }
}
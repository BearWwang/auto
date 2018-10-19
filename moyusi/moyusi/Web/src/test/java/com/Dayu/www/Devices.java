package com.Dayu.www;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Devices {
	 static Testr test1=new Testr();//读取环境配置信息：test.preperites,
	  static DesiredCapabilities capabilities;
	 public DesiredCapabilities get_capabilities(int i){
	        //配置appuim信息
	        DesiredCapabilities capabilities = new DesiredCapabilities();  
	        //capabilities.setCapability(CapabilityType.BROWSER_NAME, ""); //定义使用的浏览器Chrome,Safari
	        switch (i) {
	        case 1:
	            capabilities.setCapability("platformName", "Android");  //手机操作系统
	            capabilities.setCapability("deviceName", Testr.str8);//手机类型
	            capabilities.setCapability("udid", Testr.str8);  //连接手机的唯一标识
	            capabilities.setCapability("platformVersion", Testr.str9); //操作系统版本
	            break;
	        case 2:
	            capabilities.setCapability("platformName", "Android");  
	            capabilities.setCapability("deviceName","EJL4C16C15005850");
	            capabilities.setCapability("udid", "EJL4C16C15005850");
	            capabilities.setCapability("platformVersion", "6.0");  
	            break;
	        default:
	            break;
	        }
	        capabilities.setCapability("app",test1.str10);//app
	        capabilities.setCapability("appPackage", test1.str11); //app包名
	        capabilities.setCapability("appActivity","com.ui.StartActivity");//要启动的Android Activity名
	        capabilities.setCapability("noReset", true); //不要在会话前重置应用状态
	        capabilities.setCapability("fullReset", false); //Android是否删除应用，IOS是否删除整个模拟器目录
	        capabilities.setCapability("sessionOverride", true);
	        //输入配置
	        capabilities.setCapability("unicodeKeyboard", true); //是否启动Unicode输入法
	        capabilities.setCapability("resetKeyboard", false);  //结束后是否切换回默认输入法
	        //H5驱动关键，不配置webview会被识别成com.tencent.mm的webview，不是com.tencent.mm:tools的
	      //  ChromeOptions op= new ChromeOptions();
	       // op.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
	    //    capabilities.setCapability(ChromeOptions.CAPABILITY, op);
	        return capabilities;
	        
	    }
	

}

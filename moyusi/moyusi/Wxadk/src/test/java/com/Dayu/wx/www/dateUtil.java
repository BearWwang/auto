package com.Dayu.wx.www;
import java.text.SimpleDateFormat;
import java.util.Date;
//该类主要用于生成年，月，日，小时，分钟，和秒的信息，用于生成保存截图文件目录名和文件名

public class dateUtil {
	
	/*
	 * 格式化输出日期
	 */
public static String format(Date date, String format){
String result="";
try{
	if (date!=null){
		java.text.DateFormat df = new java.text.SimpleDateFormat(format);
	    result=df.format(date);
}
}
catch(Exception e)
{
	e.printStackTrace();
}
return result;
}

/*
 * 返回年份
 */
public static int getYear(Date date)
{
	java.util.Calendar c=java.util.Calendar.getInstance();
	c.setTime(date);
	return c.get(java.util.Calendar.YEAR);
}
/*
 * 返回月份
 */
public static int getMonth(Date date)
{
	java.util.Calendar c=java.util.Calendar.getInstance();
	c.setTime(date);
	return c.get(java.util.Calendar.MONTH)+1;
}
/*
 * 返回在月份中的第几天
 */
public static int getDay(Date date)
{
	java.util.Calendar c=java.util.Calendar.getInstance();
	c.setTime(date);
	return c.get(java.util.Calendar.DAY_OF_MONTH);
}
/*
 * 返回小时
 */
public static int getHour(Date date)
{
	java.util.Calendar c=java.util.Calendar.getInstance();
	c.setTime(date);
	return c.get(java.util.Calendar.HOUR_OF_DAY);
}

/*
 * 返回分钟
 */
public static int getMinute(Date date)
{
	java.util.Calendar c=java.util.Calendar.getInstance();
	c.setTime(date);
	return c.get(java.util.Calendar.MINUTE);
}
/*
 * 返回秒
 */
public static int getSecond(Date date)
{
	java.util.Calendar c=java.util.Calendar.getInstance();
	c.setTime(date);
	return c.get(java.util.Calendar.SECOND);
}
 public static String getCurrentTime() {
	 Date date = new Date();
	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :HH:mm:ss");

	return dateFormat.format(date);
	 
	
}
}
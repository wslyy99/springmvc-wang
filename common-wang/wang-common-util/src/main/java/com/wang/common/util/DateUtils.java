package com.wang.common.util;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author Admin
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{

    private static String[] parsePatterns =
    { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate()
    {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static Date getCurrDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date dateResult = new Date();
        try
        {
            dateResult = dateFormat.parse(dateFormat.format(new Date()));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateResult;
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern)
    {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern)
    {
        String formatDate = null;
        if (pattern != null && pattern.length > 0)
        {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        }
        else
        {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date)
    {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String formatDateTime(Date date,String format)
    {
        return formatDate(date, format);
    }
    
    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String formatTime(Date date)
    {
        return formatDate(date, "HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime()
    {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime()
    {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear()
    {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth()
    {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay()
    {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek()
    {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取过去的天数
     * 
     * @param date
     * @return
     */
    public static long pastDays(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     * 
     * @param date
     * @return
     */
    public static long pastHour(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     * 
     * @param date
     * @return
     */
    public static long pastMinutes(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     * 
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis)
    {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
                * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
                + sss;
    }
    
    /**
     * 将long类型转化为Date
     * @param str
     * @return
     * @throws ParseException
     */
    public static String LongToFormateDate(long str) {  
    	return formatDate(new Date(str * 1000), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取两个日期之间的天数
     * 
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after)
    {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }
    
    /**
     * 获取两个日期之间的小时数
     * 
     * @param before
     * @param after
     * @return
     */
    public static double getHourOfTwoDate(Date before, Date after)
    {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60);
    }
    
    /**
     * 获取两个日期之间的分钟数
     * 
     * @param before
     * @param after
     * @return
     */
    public static double getMinuteOfTwoDate(Date before, Date after)
    {
        return (getMillisecondOfTwoDate(before, after)) / (1000 * 60);
    }
    
    /**
     * 获取两个日期之间的秒数
     * 
     * @param before
     * @param after
     * @return
     */
    public static double getSecondsOfTwoDate(Date before, Date after)
    {
        return (getMillisecondOfTwoDate(before, after)) / 1000;
    }
    
    /**
     * 获取两个日期之间的这毫秒数
     * 
     * @param before
     * @param after
     * @return
     */
    public static double getMillisecondOfTwoDate(Date before, Date after)
    {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) ;
    }

    /**
     * @Title: nextMonth
     * @Description: 日期计算，以月为单位，以当前时间为节点。eg:nextMonth(1)
     *               表示下一个月；nextMonth(-1)表示上一个月
     * @author administrator 2016年5月5日 上午11:57:22
     * @param monthNum
     *            月份数量
     * @return
     * @throws
     */
    public static Date nextMonth(int monthNum)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(getCurrDateTime());
        cal.add(GregorianCalendar.MONTH, monthNum);
        return cal.getTime();
    }

    /**
     * @Title: nextYear
     * @Description: 日期计算，以年为单位，以当前时间为节点。eg:nextMonth(1)表示后一年；nextMonth(-1)表示前一年
     * @author administrator 2016年5月5日 下午12:02:00
     * @param yearNum
     * @return
     * @throws
     */
    public static Date nextYear(int yearNum)
    {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(getCurrDateTime());
        cal.add(GregorianCalendar.YEAR, yearNum);
        return cal.getTime();
    }
    
    
    
    public static String getWeeks()
    {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd , ww, E");
        //sdf.format("2015-12-26");
        Calendar cl = Calendar.getInstance();
        cl.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
        cl.setMinimalDaysInFirstWeek(3);              // 每年的第一周必须大于或等于3天，否则就算上一年的最后一周
        //cl.set(2015, 11, 26); 
        return String.valueOf(getWeekOfYear(cl)+""+cl.get(Calendar.WEEK_OF_YEAR));
    }
    
    public static int getWeekOfYear(Calendar cl){
        if(cl.get(Calendar.MONTH)==Calendar.JANUARY && cl.get(Calendar.WEEK_OF_YEAR)>50){
            return cl.get(Calendar.YEAR)-1;
        }else{
            return cl.get(Calendar.YEAR);
        }
    }
    
    /** 
     * 获取过去7天，包括今天 
     */  
    public static List<String> dateToWeek() {
    	List<String> list = new ArrayList<String>();
        SimpleDateFormat fomater = new SimpleDateFormat("yyyy-MM-dd");  
        for (int i = 7; i > 0; i--) {  
            Calendar calendar = Calendar.getInstance();  
            //测试跨年时候的情况  
            //calendar.set(2016, 12, 3);  
            //System.out.println("_________"+fomater.format(calendar.getTime()));
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);  
            calendar.set(Calendar.DAY_OF_YEAR, dayOfYear - i);  
            list.add(fomater.format(calendar.getTime())); 
            //System.out.println(fomater.format(calendar.getTime()));  
        } 
        return list;
    }  
    
    /** 
     * 获取下月第一天
     */  
    public static Date nextMonthFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException
    {
    	
    	System.out.println(DateUtils.formatDateTime(DateUtils.nextMonthFirstDate(),"yyyy-MM-dd HH:mm:ss"));
//        
//        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd , ww, E");
//        //sdf.format("2009-12-31");
//        Calendar cl = Calendar.getInstance();
//        cl.setFirstDayOfWeek(GregorianCalendar.SUNDAY);// 每周以周日开始
//        cl.setMinimalDaysInFirstWeek(3);                // 每年的第一周必须大于或等于3天，否则就算上一年的最后一周
//        
//        cl.set(2015, 11, 26);    // 使用SimpleDateFormat获取的周数是错误的，get(Calendar.WEEK_OF_YEAR)是对的
//        System.out.println(sdf.format(cl.getTime())+"/t"+cl.get(Calendar.WEEK_OF_YEAR)+"/t"+getWeekOfYear(cl));
//        cl.set(2015, 11, 27);
//        System.out.println(sdf.format(cl.getTime())+"/t"+cl.get(Calendar.WEEK_OF_YEAR)+"/t"+getWeekOfYear(cl));
//        cl.set(2016, 0, 1);
//        System.out.println(sdf.format(cl.getTime())+"/t"+cl.get(Calendar.WEEK_OF_YEAR)+"/t"+getWeekOfYear(cl));
//        cl.set(2016, 0, 3);
//        System.out.println(sdf.format(cl.getTime())+"/t"+cl.get(Calendar.WEEK_OF_YEAR)+"/t"+getWeekOfYear(cl));
//        
        
        
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
//        System.out.println(df.format(getCurrDateTime()));// new Date()为获取当前系统时间
//
//        System.out.println(df.format(nextMonth(-6)));
//        System.out.println(df.format(nextYear(-6)));
        
      
        
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //
        // java.util.Date time = null;
        // time = sdf.parse(sdf.format(new Date()));
        // System.out.println(formatDate(parseDate("2010/3/6")));
        // System.out.println(getDate("yyyy年MM月dd日 E"));
        // long time = new Date().getTime()-parseDate("2012-11-19").getTime();
        // System.out.println(time/(24*60*60*1000));
        // System.out.println(getDate("yyyyMMdd"));
        
        //System.out.println(formatDate(DateUtils.getCurrDateTime(), "yyyy-MM"));
    	/*List<String> list=DateUtils.dateToWeek();
    	for(String str: list)
    	{
    		System.out.println(str);
    	}*/
    	//DateUtils.dateToWeek();
//        System.out.println(getSecondsOfTwoDate(getCurrDateTime(), Tools.str2Date("2016-07-15 23:59:59")));
    }
}

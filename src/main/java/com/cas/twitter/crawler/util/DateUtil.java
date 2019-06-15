package com.cas.twitter.crawler.util;
/**
 * @see 提供对日期，时间的操作
 * */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 日期时间样式：yyyyMMddHHmmss
	 */
	public static final SimpleDateFormat DATE_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 日期时间样式：yyyy-MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat DATE_YYYYMMDDHHMMSS_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 日期时间样式：yyyy/MM/dd HH:mm:ss
	 */
	public static final SimpleDateFormat DATE_YYYYMMDDHHMMSS_3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * 日期时间样式：yyyy-M-d H:mm:ss
	 */
	public static final SimpleDateFormat DATE_YYYYMDHMMSS = new SimpleDateFormat("yyyy-M-d H:mm:ss");
	/**
	 * 日期时间样式：yyyy-MM-dd HH:mm
	 */
	public static final SimpleDateFormat DATE_YYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * 日期时间样式：yyyy-M-d H:mm
	 */
	public static final SimpleDateFormat DATE_YYYYMDHMM = new SimpleDateFormat("yyyy-M-d H:mm");
	/**
	 * 日期时间样式：yyyy-MM-dd
	 */
	public static final SimpleDateFormat DATE_YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd"); 
	/**
	 * 日期时间样式：yyyyMMdd
	 */
	public static final SimpleDateFormat DATE_YYYYMMDD_2 = new SimpleDateFormat("yyyyMMdd"); 
	/**
	 * 日期时间样式：yyMMdd
	 */
	public static final SimpleDateFormat DATE_YYMMDD = new SimpleDateFormat("yyMMdd"); 
	/**
	 * 日期时间样式：yyyy-M-d
	 */
	public static final SimpleDateFormat DATE_YYYYMD = new SimpleDateFormat("yyyy-M-d");
	/**
	 * 日期时间样式：MM-dd HH:mm
	 */
	public static final SimpleDateFormat DATE_MMDDHHMM = new SimpleDateFormat("MM-dd HH:mm");
	/**
	 * 日期时间样式：MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat DATE_MMDDHHMMSS = new SimpleDateFormat("MM-dd HH:mm:ss");
	/**
	 * 日期时间样式：MM-dd
	 */
	public static final SimpleDateFormat DATE_MMDD = new SimpleDateFormat("MM-dd");
	/**
	 * 日期时间样式：M月d日
	 */
	public static final SimpleDateFormat DATE_MD = new SimpleDateFormat("M月d日");
	/**
	 * 日期时间样式：M月
	 */
	public static final SimpleDateFormat DATE_M = new SimpleDateFormat("M月");
	/**
	 * 日期时间样式：MM
	 */
	public static final SimpleDateFormat DATE_MM = new SimpleDateFormat("MM");
	/**
	 * 日期时间样式：M月第w周
	 */
	public static final SimpleDateFormat DATE_MW = new SimpleDateFormat("M月第W周");
	/**
	 * 日期时间样式：MMW
	 */
	public static final SimpleDateFormat DATE_MMW = new SimpleDateFormat("MMW");
	/**
	 * 日期时间样式：M-d H:mm
	 */
	public static final SimpleDateFormat DATE_MDHMM = new SimpleDateFormat("M-d H:mm");
	/**
	 * 日期时间样式：HH:mm
	 */
	public static final SimpleDateFormat DATE_HHMM = new SimpleDateFormat("HH:mm");
	/**
	 * 日期时间样式：HH:mm:ss
	 */
	public static final SimpleDateFormat DATE_HHMMSS = new SimpleDateFormat("HH:mm:ss");

	
	/**
	 * 格式日期格式  如将：20100803120223 格式化成：2010-08-03 12:02:23
	 * @param dateStr 输入的日期格式 20100803120223
	 * @return
	 */
	public static String formartDateStr(String dateStr){
		String format = "";
		if (dateStr==null && "".equals(dateStr)) {
			format = "&nbsp;";
		}else {
			if (dateStr.length()==14) {
				try {
					DateUtil.DATE_YYYYMMDDHHMMSS_2.parse(dateStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				format = dateStr.substring(0,4) + "-";
//				format += dateStr.substring(4,6) + "-";
//				format += dateStr.substring(6,8) + " ";
//				format += dateStr.substring(8,10) + ":";
//				format += dateStr.substring(10,12) + ":";
//				format += dateStr.substring(12,14);
			}else if (dateStr.length()==8) {
				try {
					DateUtil.DATE_YYYYMMDD.parse(dateStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				format = dateStr.substring(0,3) + "-";
//				format += dateStr.substring(4,5) + "-";
//				format += dateStr.substring(6,7);
//			}else {
			}
			if (format.equals("")) {
				format = dateStr;
			}
		}
		return format;
	}
	
	/**
	 * 获取当时间的格式化输出
	 * @param formatString 日期格式
	 */
	public static String getCurrentDate(String formatString){
		SimpleDateFormat format = new SimpleDateFormat(formatString);
		return format.format(new Date());
	}
	
	/**
	 * 获取当时间的格式化输出
	 * @param formatString 日期格式
	 */
	public static String getCurrentDate(){
		return getCurrentDate("yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将数据库中所保存的日期格式转化为正常的日期格式进行显示
	 * @param dateStr 数据库中所保存的日期格式
	 * @return 正常的日期格式
	 */
	public static String converteDate(String dateStr){
		return converteDate(dateStr,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将数据库中所保存的日期格式转化为正常的日期格式进行显示
	 * @param dateStr 数据库中所保存的日期格式
	 * @return 正常的日期格式
	 */
	public static String converteDate(String dateStr,String formatString){
		if(dateStr==null || "".equals(dateStr.trim())){
			return "&nbsp;";
		}
		try{
			Date eventDate = new Date();
			long timeLong = Long.valueOf(dateStr).longValue();
			if (dateStr.length()<13) {
				timeLong = Long.valueOf(dateStr).longValue()*1000;
			}
			eventDate.setTime(timeLong);
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			return format.format(eventDate);
		}catch (Exception e) {
			return dateStr;
		}
	}
	
	public static String getCurrentDayBefore( int day){
		Date date = new Date();//获取当前时间    
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
		//calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
		//calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		calendar.add(Calendar.DAY_OF_MONTH, day);//前一天  
		return formatDate(calendar.getTime(), DATE_YYYYMMDD);//获取一年前的时间，或者一个月前的时间 
	}

	/**
	 * @param dateStr String 日期字符串
	 * @param formateString String 日期的格式。例如：yyyy-MM-dd HH:mm:ss
	 * @return 根据传入的字符串获得相对应的日期对象 
	 * */
	public static Date getDate(String dateStr,String formatString) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}	
	/**
	 * @param dateStr String 日期字符串
	 * @return根据传入的字符串获得相对应的日期对象.这里指定的日期格式是 yyyy-MM-dd HH:mm:ss
	 * */	
	public static Date getDate(String dateStr) {
		return getDate(dateStr,"yyyy-MM-dd HH:mm:ss");
	} 

	/**
	 * @see 根据日期对象获得相对应的长整型。
	 * 	注意到这里实际上用了java的getTime方法， 获得long以后除以1000. 因为数据库的中的时间只精确到秒。而java方法精确到了毫秒。
	 * */
	public static long getTimeX(String dateStr){
		long time = 0;
		time = getDate(dateStr).getTime()/1000;
		return time;
	}
	public static long getTimeX(String dateStr,String format){
		long time = 0;
		time = getDate(dateStr,format).getTime()/1000;
		return time;
	}	
	public static long getTimeX(Date date){
		long time = 0;
		time =date.getTime()/1000;
		return time;
	}
	/**
	 * @see 根据日期对象获得相对应的长整型。精确到毫秒
	 * 返回0为出错
	 */
	public static long getTime(String dateStr){
		long time = -1;
		String format="yyyy-MM-dd HH:mm:ss";
		Date date=getDate(dateStr,format);
		if(date!=null){
			time = getDate(dateStr,format).getTime();
		}
		return time;
	}	
	
	/**
	 * @author   2009-7-31
	 * @see  根据时间获得分区名 这个是暂时的写法，因为这里只确定是1一个小时一个分区
	 * 
	 * */
	public static String getPartitionName(String dateStr,String protocol){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//P20090731080000_1249002000
		String partitionName = "P";
		
		Date date = getDate(dateStr);
		//date.setHours(date.getHours()+1);
		date.setMinutes(0);
		date.setSeconds(0);
		partitionName = partitionName + DateUtil.DATE_YYYYMMDDHHMMSS.format(date);
//		partitionName = partitionName + sdf.format(date);
		date.setHours(date.getHours()+1);
		partitionName = partitionName + "_" + date.getTime()/1000;
		return partitionName;
	}
	
	/**
	 * @author 刘伯睿  2009-7-31
	 * @see  根据时间获得分区名 这个是暂时的写法，因为这里只确定是1一个小时一个分区
	 * 
	 * */
	public static String getPartitionName(long dateTime,String protocol){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//P20090731080000_1249002000
		String partitionName = "P";
		Date date = new Date();
		date.setTime(dateTime*1000);
		//date.setHours(date.getHours()+1);
		date.setMinutes(0);
		date.setSeconds(0);
//		partitionName = partitionName + sdf.format(date);
		partitionName = partitionName + DateUtil.DATE_YYYYMMDDHHMMSS.format(date);
		date.setHours(date.getHours()+1);
		partitionName = partitionName + "_" + date.getTime()/1000;
		return partitionName;
	}
	
	/*
	 * 将格式为"yyyy-MM-dd HH:mm:ss"的日期数据转化为长整型，以解决Long.parseLong()方法的有问题
	 * @param str
	 * @return
	 */
	public static long converteDataStr2Long(String str){
		Date date = new Date();

		String temp = str.substring(0,4);
		date.setYear(Integer.valueOf(temp).intValue()-1900);
		temp = str.substring(5,7);
		date.setMonth(Integer.valueOf(temp).intValue()-1);
		temp = str.substring(8,10);
		date.setDate(Integer.valueOf(temp).intValue());
		temp = str.substring(11,13);
		date.setHours(Integer.valueOf(temp).intValue());
		temp = str.substring(14,16);
		date.setMinutes(Integer.valueOf(temp).intValue());
		temp = str.substring(17,19);
		date.setSeconds(Integer.valueOf(temp).intValue());
		return date.getTime()/1000;
	}
	
	/**
	 * 将Date对象格式化输出
	 * @param date Date对象
	 * @param formartStr 格式
	 * @return
	 */
	public static String formatDate(Date date, String formatStr){
		String dateStr = "";
		if (date!=null) {
			if (formatStr == null || "".equals(formatStr.trim())) {
				formatStr = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			dateStr = format.format(date);
		}
		return dateStr;
	}
	
	/**
	 * 把date转换为String
	 * @return
	 */	
	public static String formatDateToString(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * 将指定日期按照yyyy-MM-dd HH:mm:ss格式输出
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return formatDate(date,"");
	}
	/**
	 * 将Date对象格式化输出
	 * @param date Date对象
	 * @param formartStr 格式
	 * @return
	 */
	public static String formatDate(Date date, SimpleDateFormat format){
		String dateStr = "";
		if (date!=null) {
			if (format == null ) {
				format = DateUtil.DATE_YYYYMMDDHHMMSS_2;
			}			
			dateStr = format.format(date);
		}
		return dateStr;
	}	
	/**
	 * 获取两个时间之间的间隔，不同的type返回不同的间隔单位，
	 * d-表示相隔多少天，h-表示相隔多少个小时，"m"-表示相隔多少分钟,s-表示相隔多少秒
	 * @param beginTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public static long  getTimeBetween(String beginTime,String endTime,String type){
		long begin=getDate(beginTime).getTime();//开始时间毫秒数
		long end=getDate(endTime).getTime();//结束时间毫秒数
		long dis=end-begin;
		long ret=0;
		//根据不同的传入类型计算间隔
		if("d".equals(type)||type==null){//日
			ret=Math.round(dis/(24*60*60*1000));
		}else if("h".equals(type)){
			ret=Math.round(dis/(60*60*1000));
		}else if("m".equals(type)){
			ret=Math.round(dis/(60*1000));
		}else if("s".equals(type)){
			ret=Math.round(dis/1000);
		}
		return ret;
	}
	/**
	 * 获取当前日期的前N小时的日期
	 * @param hour
	 */
	public final  static String getDateBefore(int hour){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, hour);
		String strDate=DateUtil.formatDate(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		return strDate;
	} 
	
	/**
	 * 获取当前日期的前N小时的日期
	 * @param hour
	 * @param formatStr
	 */
	public final  static String getDateBefore(int hour,String formatStr){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, hour);
		String strDate=DateUtil.formatDate(c.getTime(), formatStr);
		return strDate;
	}
	
	public final static String formatDate(String dateStr, String formatStrIn,String formatStrOut){
		try{
			SimpleDateFormat sdfIn = new SimpleDateFormat(formatStrIn);
			Date date = sdfIn.parse(dateStr);
			SimpleDateFormat sdfOut = new SimpleDateFormat(formatStrOut);
			dateStr = sdfOut.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dateStr;
		
	}
	
	
	
	/**
	 * @desc 在个位数的月和日前加上0
	 * @since 2015-3-25 
	 * @param str
	 * @return
	 */
	public static String insert0(String str) {
		String ret = str;
		try {
			if(Integer.parseInt(str) < 10) {
				str = "0" + Integer.parseInt(str);
				ret = str;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret = str;
		}
		return ret;
	}
	
	/**
	 * 获取昨天天日期
	 * @param hour
	 * @param formatStr
	 */
	public final  static String getYesterday(String formatStr){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		String strDate=DateUtil.formatDate(c.getTime(), formatStr);
		return strDate;
	}
	
	public final  static String getNextMonth(String formatStr){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 30);
		String strDate=DateUtil.formatDate(c.getTime(), formatStr);
		return strDate;
	}
	
	public static void main(String[] args){
		System.out.println(getYesterday("yyyy-MM-dd"));
	}
	
}

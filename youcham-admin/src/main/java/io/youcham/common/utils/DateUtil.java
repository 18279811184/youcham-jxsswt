package io.youcham.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	public static String monthFormat="yyyy-MM";
	/** 格式为: yyyy-MM-dd*/
	private static String defaultFormat="yyyy-MM-dd";
	/** 格式为: yyyy-MM-dd*/
	public static String DATE_FORMAT_YMD = "yyyy-MM-dd";
	/** 格式为: yyyyMMdd*/
	public static String DATE_FORMAT_LX_YMD = "yyyyMMdd";
	/** 格式为: yyyy-MM-dd HH*/
	public static String DATE_FORMAT_YMDH = "yyyy-MM-dd HH";
	/** 格式为: yyyy-MM-dd HH:mm*/
	public static String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
	/** 格式为: yyyy-MM-dd HH:mm:ss*/
	public static String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	/** 格式为: yyyyMMddHHmmss*/
	public static String DATE_FORMAT_LX_YMDHMS = "yyyyMMddHHmmss";
	/** 格式为: yyyyMMddHHmm*/
	public static String DATE_FORMAT_LX_YMDHM = "yyyyMMddHHmm";

	/**
	 * 将指定日期按照指定格式转换成字串
	 * @param date	指定日期
	 * @param format	指定格式
	 * @return	日期字串
	 */
	public static String dateToStr(Date date, String format) {
		if(date == null) return null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		String strDate = simpledateformat.format(date);
		return strDate;
	}
	/**
	 * 将指定日期按照GMT指定格式转换成字串
	 * @param date	指定日期
	 * @param format	指定格式
	 * @return	日期字串
	 */
	public static String dateToGMTStr(Date date, String format) {
		if(date == null) return null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		simpledateformat.setTimeZone(TimeZone.getTimeZone("GMT"));  
		String strDate = simpledateformat.format(date);
		return strDate;
	}
	/**
	 * 将字串形式的指定日期按照指定格式转换成字串
	 * @param strDate	字串形式的指定日期
	 * @param format	指定格式
	 * @return	日期字串
	 */
	public static String strToStr(String strDate, String format) {
		strDate = formatStr(strDate);
		if(strDate == null)
			return null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		ParsePosition parseposition = new ParsePosition(0);
		Date date = simpledateformat.parse(strDate, parseposition);

		strDate = simpledateformat.format(date);
		return strDate;
	}

	/**
	 * 将字串形式的指定日期按照指定格式转换成日期
	 * @param strDate	字串形式的指定日期（格式：2006-10-16 15:10:03的部分）
	 * @param format	指定格式
	 * @return	日期
	 */
	public static Date strToDate(String strDate, String format) {

		strDate = formatStr(strDate);
		if(strDate == null)
			return null;
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		ParsePosition parseposition = new ParsePosition(0);
		Date date = simpledateformat.parse(strDate, parseposition);
		return date;
	}

	/**
	 * 将指定日期按照指定格式转换成日期
	 * @param date	指定日期
	 * @param format	指定格式
	 * @return	日期
	 */
	public static Date dateToDate(Date date, String format) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		String strDate = simpledateformat.format(date);

		ParsePosition parseposition = new ParsePosition(0);
		date = simpledateformat.parse(strDate, parseposition);
		return date;
	}

	/**
	 * 将日期字串格式化为"yyyy-MM-dd HH:mm:ss"形式
	 * @param strDate	日期字串（格式：2006-10-16 15:10:03的部分）
	 * @return	格式化后的日期字串
	 */
	private static String formatStr(String strDate) {
		if("".equals(strDate) || strDate==null) {
                    return null;
                }
		String strYear = "1900", strMonth = "01", strDay = "01";
		String strHour = "00", strMinute = "00", strSecond = "00";

		// 用" "分割年月日和时分秒
		String[] strs = strDate.split(" ");

		// 获取年月日
		String strYMD = strs[0];

		// 获取时分秒
		String strHMS;
		if (strs.length < 2) {
			strHMS = "00:00:00";
		} else {
			strHMS = strs[1];
		}

		// 用"-"分割年、月和日
		String[] strYMSs = strYMD.split("-");

		// 获取年
		if (strYMSs.length > 0 && !"".equals(strYMSs[0])) {
			strYear = strYMSs[0];
		}
		// 获取月
		if (strYMSs.length > 1) {
			strMonth = strYMSs[1].length() == 1 ? ("0" + String
					.valueOf(strYMSs[1])) : String.valueOf(strYMSs[1]);
		}
		// 获取日
		if (strYMSs.length > 2) {
			strDay = strYMSs[2].length() == 1 ? ("0" + String
					.valueOf(strYMSs[2])) : String.valueOf(strYMSs[2]);
		}

		// 用":"分割时分秒
		String[] strHMSs = strHMS.split(":");

		// 获取时
		if (strHMSs.length > 0) {
			strHour = strHMSs[0];
		}
		// 获取分
		if (strHMSs.length > 1) {
			strMinute = strHMSs[1].length() == 1 ? ("0" + String
					.valueOf(strHMSs[1])) : String.valueOf(strHMSs[1]);
		}
		// 获取秒
		if (strHMSs.length > 2) {
			strSecond = strHMSs[2].length() == 1 ? ("0" + String
					.valueOf(strHMSs[2])) : String.valueOf(strHMSs[2]);
		}

		strDate = strYear + "-" + strMonth + "-" + strDay + " " + strHour + ":"
				+ strMinute + ":" + strSecond;

		return strDate;
	}
	/**
	 * 将日期字串"yyyy-MM-dd"与"HH:mm"合并，作为date输出
	 * @param ymd	日期字串（格式：2006-10-16部分）
	 * @param hm	日期字串（格式：12：22部分）
	 * @return	合并后的date
	 */
	public static Date combineYmdHm(String ymd,String hm)
	{
		String ymdhm=ymd+" "+hm;		
		Date d=strToDate(ymdhm,DATE_FORMAT_YMDHM);
		return d;
	}
	
	
	/**
	 * 将日期字串"yyyy-MM-dd"转换为date，并且是当前日期最后一秒
	 * @param ymd	日期字串（格式：2006-10-16部分）
	 * @return	合并后的date
	 */
	public static Date endOfDate(String ymd)
	{
		String ymdhms=ymd+" "+"23:59:59";
		Date d=strToDate(ymdhms,DATE_FORMAT_YMDHMS);
		return d;
	}
	
	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDay(String format){
		    if(format==null){
		    	format=defaultFormat;
		    }
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat(format);
			String sdate=sdf.format(date);
			return sdate;
	}
	/**
	 * 转化日期格式
	 * @param dateStr 需要转化的日期
	 * @param formatStr 转化的格式
	 * @return 转化后的日期
	 */

	public static Date convertToDate(String dateStr) {
		if (dateStr == null) {
			return null;
		}

		try {
			DateFormat format = new SimpleDateFormat(DATE_FORMAT_YMD);
			return format.parse(dateStr);
		}
		catch (Exception e) {
			return null;
		}
	}
	/**
	 * 字符转时间格式化（yyyy-MM-dd）
	 * @param date  字符类型
	 * @return 时间类型
	 */
	public static Date DateFormat(String date)
	{
	    Date fdate = null;
		if(date!=null){
		    DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			try {
				fdate = df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			return fdate;
			}
		return null;		
	}

    public static String TIME_MIN = "MIN";
    public static String TIME_HOUR = "HOUR";
    public static String TIME_DAY = "DAY";
    public static String TIME_MONTH = "MONTH";
    public static String TIME_YEAR = "YEAR";
    public static String MID_DATA_FORMAT;
    public static Calendar calendar ;
    public static DateFormat dateFormat;
    public static String JAVA_DATE_FORMAT = "yyyy:MM:dd HH:mm:ss";
    public static String ORACLE_DATE_FORMAT = "YYYY:MM:DD HH24:mi:ss";
    public static String MSSQLSERVER_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public DateUtil()
    {
    }

    public static Date getCurrentDate()
    {
        return new Date();
    }

    public static Date getDate(Date date, Integer integer, String s)
    {
        if(integer != null)
            return getDate(date, integer.intValue(), s);
        else
            return date;
    }

    public static Date getDate(Date date, int i, String s)
    {
        calendar.setTime(date);
        if(s.equalsIgnoreCase(TIME_MIN))
            calendar.add(12, i);
        else
        if(s.equalsIgnoreCase(TIME_HOUR))
            calendar.add(11, i);
        else
        if(s.equalsIgnoreCase(TIME_DAY))
            calendar.add(5, i);
        else
        if(s.equalsIgnoreCase(TIME_MONTH))
            calendar.add(2, i);
        else
        if(s.equalsIgnoreCase(TIME_YEAR))
            calendar.add(1, i);
        return calendar.getTime();
    }

    public static String getDateStr(Date date)
    {
        if(date == null)
        {
            return null;
        } else
        {
            String s = dateFormat.format(date);
            return s;
        }
    }

    public static String getOracleDateStr(Date date)
    {
        if(date == null)
        {
            return null;
        } else
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(JAVA_DATE_FORMAT);
            String s = simpledateformat.format(date);
            return s;
        }
    }

    public static String getSqlServerDateStr(Date date)
    {
        if(date == null)
        {
            return null;
        } else
        {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(MSSQLSERVER_DATE_FORMAT);
            String s = simpledateformat.format(date);
            return s;
        }
    }


    public static String convertToStr(Date date, String s)
    {
        if(date == null)
            return "";
        SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
        return simpledateformat.format(date);
    }

    public static Date convertToDate(String s, String s1)
    {
        if(s == null)
            return null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(s1);
        try {
			return simpledateformat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
    /**
     * day天
     * @param day
     * @return
     */
    public static Date beforeDate(int day){
    	//获取当前日期

    	Date today = new Date();
//    	String endDate = sdf.format(today);//当前日期
    	//获取三十天前日期
    	Calendar theCa = Calendar.getInstance();
    	theCa.setTime(today);
    	theCa.add(theCa.DATE, day);//最后一个数字30可改，30天的意思
    	Date start = theCa.getTime();
    	return start;
    }
    static 
    {
        MID_DATA_FORMAT = "yyyy-MM-dd";
        dateFormat = new SimpleDateFormat(MID_DATA_FORMAT);
    }

}

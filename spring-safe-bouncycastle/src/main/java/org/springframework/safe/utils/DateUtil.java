package org.springframework.safe.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 时间工具类
 * @author Sir.D
 */
public class DateUtil {
	public static final Pattern DATETIME_PATTERN = Pattern.compile( "^(\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:\\d{2})$" );
	public static final Pattern DATE_PATTERN	 = Pattern.compile( "^(\\d{4}\\-\\d{2}\\-\\d{2})$" );
	public static final Pattern TIME_PATTERN	 = Pattern.compile( "^(\\d{2}:\\d{2}:\\d{2})$" );
//	public static final SimpleDateFormat defaultFormat() = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	
	public static void main(String[] args){
//		Date date = getExpire( new Date(1505029872738L), 24);
		
		String date = getYesterday();
		System.out.println( date );
	}

	public static SimpleDateFormat defaultFormat() {
		return new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	}

	/**
	 * TODO
	 * 格式时间
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String get( String pattern, Date date ){
		if ( null == date ) {
			date = new Date();
		}

		try {
			if( null == pattern ) {
				return defaultFormat().format( date );
			}

			return new SimpleDateFormat( pattern ).format( date );
		} catch ( Exception e ) {
			return defaultFormat().format( date );
		}
	}

	/**
	 * 获取当前时间组成的路径
	 * @return yyyy/MM/dd/ or yyyy\\MM\\dd\\
	 */
	public static String getPathOfDate() {
		return DateUtil.get( "yyyy" + File.separator + "MM" + File.separator + "dd" + File.separator, null );
	}

	/**
	 * TODO
	 * 获取上个月的今天的日期
	 * @return Date
	 */
	public static Date getTodayOfLastMonth(){
		Calendar calendar = Calendar.getInstance(); 
		calendar.add( Calendar.MONTH, -1 );
		return calendar.getTime();
	}
	
	/**
	 * TODO
	 * 获取指定月的最大天数
	 * @param month		月份
	 * @return 28, 29, 30 or 31
	 */
	public static int getMaxDayOfMonth( int month ){
		Calendar calendar = Calendar.getInstance();
		calendar.set( Calendar.MONTH, month - 1 );
		return calendar.getActualMaximum( Calendar.DAY_OF_MONTH );
	}
	
	/**
	 * TODO
	 * 获取指定天数的时间差
	 * @param day	天数 ( 为正数时, 表示未来的 day 天; 为负数时, 表示过去的 day 天 )
	 * @return
	 */
	public static Date getDayPlus( int day ){
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.DATE, day );
		return calendar.getTime();
	}
	
	/**
	 * TODO
	 * 获取指定分钟的时间差
	 * @param minute	分钟数 ( 为正数时, 表示未来的 minute 分钟; 为负数时, 表示过去的 minute 分钟 )
	 * @return
	 */
	public static Date getMinutePlus( int minute ){
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.MINUTE, minute );
		return calendar.getTime();
	}
	
	/**
	 * TODO
	 * 获取现在的日期
	 * @return yyyy-MM-dd
	 */
	public static String getDate() {
		return DateUtil.get( "yyyy-MM-dd", null );
	}
	
	/**
	 * TODO
	 * 获取昨天的时间
	 * @return
	 */
	public static String getYesterday(){
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.DATE, -1 );
		return DateUtil.get( "yyyy-MM-dd", calendar.getTime() );
	}

	/**
	 * TODO
	 * 获取现在的日期字符串
	 * @return yyyyMMdd
	 */
	public static String getDateString() {
		return DateUtil.get( "yyyyMMdd", null );
	}
	

	/**
	 * TODO
	 * 获取现在的时间字符串
	 * @return HHmmss
	 */
	public static String getTimeString() {
		return DateUtil.get( "HHmmss", null );
	}

	/**
	 * TODO
	 * 获取现在的日期时间
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
		return defaultFormat().format( new Date() );
	}
	
	/**
	 * TODO
	 * 获取现在的日期时间字符串
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateTimeString() {
		return DateUtil.get("yyyyMMddHHmmss", null);
	}
	
	/**
	 * TODO
	 * 获取现在的时间戳
	 * @return
	 */
	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * TODO
	 * 获取现在的时间戳
	 * @return
	 */
	public static long getTimeMillis(LocalDateTime time) {
		return time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
	}

	/**
	 * 根据时间戳获取时间
	 * @param milliseconds
	 * @return
	 */
	public static LocalDateTime getLocalDateTime( long milliseconds ) {
		return LocalDateTime.ofEpochSecond(milliseconds/1000,0,ZoneOffset.ofHours(8));
	}

	/**
	 *
	 * @param time
	 * @return
	 */
	public static String getDateString(LocalDateTime time) {
		return getDateString(time, null);
	}

	/**
	 * 获取字符串时间
	 * @param time
	 * @return
	 */
	public static String getDateString(LocalDateTime time, String pattern) {
		DateTimeFormatter formatter;
		if (null == pattern) {
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		} else {
			formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
		}
		return formatter.format(time);
	}

	/**
	 * 时间戳转换成字符串时间
	 * @return
	 */
	public static String milliseconds2DateString( long milliseconds ) {
		return defaultFormat().format( new Date( milliseconds ) );
	}
	
	/**
	 * TODO
	 * 字符串转换成日期
	 * @param datetime
	 * @return
	 */
	public static Date str2Date( String datetime ){
		if ( null != datetime ) {
			try {
				// yyyy-MM-dd HH:mm:ss
				if ( DATETIME_PATTERN.matcher( datetime ).matches() ) {
					return defaultFormat().parse( datetime );
				}
				// yyyy-MM-dd
				if ( DATE_PATTERN.matcher( datetime ).matches() ) {
					return new SimpleDateFormat( "yyyy-MM-dd" ).parse( datetime );
				}
				// HH:mm:ss
				if ( TIME_PATTERN.matcher( datetime ).matches() ) {
					return new SimpleDateFormat( "yyyy-MM-ddHH:mm:ss" ).parse( getDate() + datetime );
				}
			} catch ( ParseException e ) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * TODO
	 * 计算一个日期在 months 个月后的过期时间
	 * @param date		给定的时间
	 * @param months	多少月后过期
	 * @return
	 */
	public static Date getExpire( Date date, int months ) {
		long expire, current;
		if( null == date ){
			date = new Date();
			expire = current = date.getTime();
		} else {
			expire = date.getTime();
			current = System.currentTimeMillis();
		}
		
		Calendar calendar = Calendar.getInstance();
		if ( expire > current ) {
			// 过期时间是未来的一个时间, 那 months 个月后的过期时间 = 过期时间 + months
			calendar.setTime( date );
		}

		calendar.add( Calendar.MONTH, months );
		return calendar.getTime();
	}

}

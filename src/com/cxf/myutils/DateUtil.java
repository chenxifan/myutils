/*
 * 创建日期：2014-4-29
 */
package com.cxf.myutils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 职责：日期工具类
 * 
 * @author chenxf
 * @version 1.0
 */
public class DateUtil {

	/**
	 * 职责：根据指定的格式，将字符转换化为日期
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date string2Date(String dateStr, String format) throws Exception {
		Date date = null;
		DateFormat df = new SimpleDateFormat(format);
		if (dateStr != null && !dateStr.trim().equals("")) {
			date = df.parse(dateStr);
		}
		return date;
	}

	/**
	 * 职责：日期转化为字符串，格式为yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		return date2String(date, "yyyy-MM-dd");
	}

	/**
	 * 职责：将日期转化为指定格式的字符串。<br/>
	 * 注意：如果格式为null，则默认为yyyy-MM-dd
	 * 
	 * @param date
	 * @param format
	 * @return 如果时间为null，则返回null。
	 */
	public static String date2String(Date date, String format) {
		if (format == null) {
			format = "yyyy-MM-dd";
		}

		DateFormat df = new SimpleDateFormat(format);
		if (date == null) {
			return null;
		}

		return df.format(date);
	}

	/**
	 * 职责：获取本周上周或者下周的起止日期，周一为某周的开始
	 * 
	 * @param returnType
	 *            返回类型参数：-1 上周，0 本周，1 下周
	 * @return 返回格式星期一和星期日的日期对象数组
	 */
	public static Date[] getMondayAndSundayOfWeek(int returnType) {
		Date[] beginEnd = new Date[2];
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;

		calendar1.add(Calendar.DATE, offset1 + 7 * returnType);
		calendar2.add(Calendar.DATE, offset2 + 7 * returnType);

		beginEnd[0] = calendar1.getTime();
		beginEnd[1] = calendar2.getTime();
		return beginEnd;
	}

	/**
	 * 职责：获取本周上周或者下周的起止日期，周一为某周的开始
	 * 
	 * @param returnType
	 *            返回类型参数：-1 上周，0 本周，1 下周
	 * @return 返回格式星期一和星期日的日期字符串数组，格式为yyyy-MM-dd
	 */
	public static String[] getMondayAndSundayOfWeekStr(int returnType) {
		Date[] beginEnd = getMondayAndSundayOfWeek(returnType);
		String[] beginEndStr = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		beginEndStr[0] = sdf.format(beginEnd[0].getTime());
		beginEndStr[1] = sdf.format(beginEnd[1].getTime());
		return beginEndStr;
	}

	/**
	 * 职责：得到指定月份的开始时间和结束时间
	 * 
	 * @param monthCount
	 *            指定的月数，0：代表系统时间的当前月份， 1：代表系统时间的下一个月份,-1代表系统时间的上一个月，以此类推。
	 * @return 返回开始时间和结束时间日期对象数组
	 */
	public static Date[] getMonthBeginAndEndDay(int monthCount) {
		Date[] beginEnd = new Date[2];

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MONTH, monthCount);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		beginEnd[0] = calendar.getTime();

		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
		beginEnd[1] = calendar.getTime();

		return beginEnd;
	}

	/**
	 * 职责：获得指定日期的凌晨时间
	 * 
	 * @param srcDate
	 *            指定的日期
	 * @return 指定日期的凌晨时间，即某日的0时0分0秒
	 */
	public static Date getZeroDate(Date srcDate) {
		if (srcDate == null) {
			return srcDate;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(srcDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 职责：获得指定日期的午夜时间
	 * 
	 * @param srcDate
	 *            指定的日期
	 * @return 指定日期的凌晨时间，即某日的23时59分59秒
	 */
	public static Date getMidnightDate(Date srcDate) {
		if (srcDate == null) {
			return srcDate;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(srcDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 职责：获取2个日期间隔的天数。<br/>
	 * 规则：第一个日期 距离 第二个日期 的天数；如果第二个日期为空，则获取第一日期距离当前系统时间的天数 实现思路：要判断是否在同一个月
	 * 
	 * @param firstDate
	 *            第一个日期
	 * @param secondDate
	 *            第二日期，如果为Null，则用系统时间代替
	 * @return 返回天数
	 */
	public static int getDaysBetweenTwoDate(Date firstDate, Date secondDate) {
		if (firstDate == null) {
			System.err.println("起始日期为null!");
			return -1;
		}

		Calendar calendarFirst = Calendar.getInstance();
		calendarFirst.setTime(firstDate);

		Calendar calendarSecond = Calendar.getInstance();
		if (secondDate == null) {
			calendarSecond.setTimeInMillis(System.currentTimeMillis());
		} else {
			calendarSecond.setTime(secondDate);
		}

		int totalDayCount = 0;

		// 判断两个日期是否相同的年月
		if (calendarFirst.get(Calendar.YEAR) == calendarSecond.get(Calendar.YEAR)
				&& calendarFirst.get(Calendar.MONTH) == calendarSecond.get(Calendar.MONTH)) {
			totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH)
					- calendarSecond.get(Calendar.DAY_OF_MONTH);
			return totalDayCount;
		}

		totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH); // 获取当前月份的天数
		while (true) {
			// 设置第二个日期为当前月份的月初时间（第一天，00时00分00秒）
			calendarFirst.set(Calendar.DAY_OF_MONTH, 1);
			calendarFirst.set(Calendar.HOUR_OF_DAY, 0);
			calendarFirst.set(Calendar.MINUTE, 0);
			calendarFirst.set(Calendar.SECOND, 0);

			// 第一个日期大于第二个日期
			if (calendarFirst.getTimeInMillis() > calendarSecond.getTimeInMillis()) {
				calendarFirst.add(Calendar.SECOND, -1);// 减一秒，得到下一个月末的时间

				// 判断两个日期是否相同的年月
				if (calendarFirst.get(Calendar.YEAR) == calendarSecond.get(Calendar.YEAR)
						&& calendarFirst.get(Calendar.MONTH) == calendarSecond.get(Calendar.MONTH)) {
					totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH)
							- calendarSecond.get(Calendar.DAY_OF_MONTH);
					return totalDayCount;
				}
				totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH);
			} else {
				// 获得上一个月末的日期
				calendarFirst.add(Calendar.MONTH, 2); // 先加2个月
				calendarFirst.add(Calendar.SECOND, -1);// 再减一秒

				if (calendarFirst.get(Calendar.YEAR) == calendarSecond.get(Calendar.YEAR)
						&& calendarFirst.get(Calendar.MONTH) == calendarSecond.get(Calendar.MONTH)) {
					totalDayCount += calendarSecond.get(Calendar.DAY_OF_MONTH);
					return -totalDayCount;
				}
				totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH);
			}
		}
	}

	/**
	 * 职责：得到二个日期间的间隔天数 <br/>
	 * 实现思路：将两个日期的时间戳相减，除以一天的秒数
	 * 
	 * @param date1
	 *            起始时间，格式"yyyy-MM-dd"
	 * @param date2
	 *            结束时间，格式"yyyy-MM-dd"
	 */
	public static int getDaysBetweenTwoDate(String date1, String date2) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date fromDate = myFormatter.parse(date1);
		java.util.Date toDate = myFormatter.parse(date2);
		return new Long((fromDate.getTime() - toDate.getTime()) / (24L * 60 * 60 * 1000))
				.intValue();
	}

	/**
	 * 职责：计算工龄，年龄<br/>
	 * 思路：当前时间与入职时间或出生日期做差
	 * 
	 * @param joinDateOrBartDate
	 *            入职时间或出生日期，格式yyyy-MM-dd
	 * @return 如果参数非法，则返回-1
	 */
	public static int getAge(String joinDateOrBartDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (joinDateOrBartDate == null || joinDateOrBartDate.length() <= 0) { // 非空判断
			return -1;
		}

		try {
			Date d = bartDateFormat.parse(joinDateOrBartDate);
			cal.setTime(d);
		} catch (Exception ec) {
			ec.printStackTrace();
			return -1;
		}

		int year2 = cal.get(Calendar.YEAR);
		int month2 = cal.get(Calendar.MONTH);
		int day2 = cal.get(Calendar.DAY_OF_MONTH);

		int y_c = year - year2;
		int m_c = month - month2;
		int d_c = day - day2;

		if (d_c < 0) {
			m_c -= 1;
		}
		if (m_c < 0) {
			y_c -= 1;
		}

		return y_c;
	}

	/**
	 * 职责：根据一个日期，返回是星期几的字符串 <br/>
	 * 实现思路：使用SimpleDateFormat("EEEE")将日期格式化为对应的星期几
	 * 
	 * @param sdate
	 *            指定日期，格式yyyy-MM-dd
	 * @return 返回星期一~星期日
	 */
	public static String getWeek(String sdate) throws Exception {
		// 再转换为时间
		Date date = string2Date(sdate, "yyyy-MM-dd");
		// Calendar c = Calendar.getInstance();
		// c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(date);
	}

	/**
	 * 职责：计算当月最后一天,返回字符串 <br/>
	 * 实现思路：根据Calendar的日期运算
	 * 
	 * @return 返回日期字符串，格式yyyy-MM-dd
	 */
	public static String getLastDayOfThisMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 职责：上月第一天
	 * 
	 * @return 返回日期字符串，格式yyyy-MM-dd
	 */
	public static String getFirstDayOfPreviousMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 职责：获取当月第一天
	 * 
	 * @return 返回日期字符串，格式yyyy-MM-dd
	 */
	public static String getFirstDayOfThisMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 职责：获得当前日期与本周日相差的天数 <br/>
	 * 注意：这个方法按照西方的传统来计算，即星期日是当周的第一天，则返回的数字是<=0 <br/>
	 * 实现思路：利用Calendar获取今天是星期几
	 * 
	 * @return
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 职责：获得上月最后一天的日期
	 * @return
	 */
	public static String getLastDayOfPreviousMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 职责：获得下个月第一天的日期
	 * @return
	 */
	public static String getNextMonthFirst() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 职责：获得下个月最后一天的日期
	 * @return
	 */
	public static String getLastDayOfNextMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 2);// 加2个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 职责：获得明年最后一天的日期 <br/>
	 * 实现思路：跟月份操作类似
	 * @return
	 */
	public static String getLastDayOfNextYear() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 2);// 加2年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 职责：获得明年第一天的日期
	 * @return
	 */
	public static String getNextYearFirst() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(lastDate.getTime());
		return str;

	}

	/**
	 * 职责：获得本年有多少天
	 * 实现思路：把日期设到当年最后一天，然后获取当天是当年的第几天
	 * @return
	 */
	public static int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	/**
	 * 职责：根据指定的月份，获得该月所在的季度日期范围
	 * @param month 指定月份
	 * @return 季度的起始日期，起始日:结束日
	 */
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfThisMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 职责：获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	public static int getLastDayOfThisMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 职责：是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 职责：计算昨天
	 * @return
	 */
	public Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

}

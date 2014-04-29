/*
 * 创建日期：2014-4-29
 */
package com.cxf.myutils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 日期工具类
 */
public class DateUtil {
	/**
	 * 字符转化为日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String dateStr) {
		if (!StringUtils.isEmpty(dateStr)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			try {
				return df.parse(dateStr);
			} catch (Exception e) {

			}
			try {
				return df1.parse(dateStr);
			} catch (Exception e) {

			}
			try {
				return df2.parse(dateStr);
			} catch (Exception e) {

			}
		}
		return null;
	}

	/**
	 * 字符转化为日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String dateStr, String format) {
		Date date = null;
		DateFormat df = new SimpleDateFormat(format);
		try {
			if (dateStr != null && !dateStr.trim().equals("")) {
				date = df.parse(dateStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}

	/**
	 * 日期转化为时间
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		String str = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			str = df.format(date);
		}
		return str;
	}

	/**
	 * 日期转化为时间
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date, String format) {
		String str = "";
		DateFormat df = new SimpleDateFormat(format);
		if (date != null) {
			str = df.format(date);
		}
		return str;
	}

	/**
	 * 获取本周上周或者下周的起止日期，周一为某周的开始
	 * 
	 * @param i
	 *            -1 上周，0 本周，1 下周
	 * @return
	 */
	public static String[] getMSDays(int i) {
		String[] beginEnd = new String[2];
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;

		calendar1.add(Calendar.DATE, offset1 + 7 * i);
		calendar2.add(Calendar.DATE, offset2 + 7 * i);

		String monday = new SimpleDateFormat("yyyy-MM-dd").format(calendar1
				.getTime());
		String sunday = new SimpleDateFormat("yyyy-MM-dd").format(calendar2
				.getTime());
		beginEnd[0] = monday;
		beginEnd[1] = sunday;
		return beginEnd;
	}

	/**
	 * 获取本周上周或者下周的起止日期，周一为某周的开始
	 * 
	 * @param i
	 *            -1 上周，0 本周，1 下周
	 * @return
	 */
	public static Date[] getMSDate(int i) {
		Date[] beginEnd = new Date[2];
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;

		calendar1.add(Calendar.DATE, offset1 + 7 * i);
		calendar2.add(Calendar.DATE, offset2 + 7 * i);

		beginEnd[0] = calendar1.getTime();
		beginEnd[1] = calendar2.getTime();
		return beginEnd;
	}

	/**
	 * 得到指定月份的开始时间和结束时间,
	 * 
	 * For Example: Map result = Test.getMonthBeginOrEndDay(0); //系统时间的当前月份 Date
	 * monthBeginDate = (Date) result.get("monthBeginDate"); Date monthEndDate =
	 * (Date) result.get("monthEndDate");
	 * 
	 * @param monthCount
	 *            ： 指定的月数，0：代表系统时间的当前月份， 1：代表系统时间的下一个月份,-1代表系统时间的上一个月，以此类推。
	 */
	public static Map getMonthBeginOrEndDay(int monthCount) {
		Map result = new HashMap();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.MONTH, monthCount);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		result.put("monthBeginDate", calendar.getTime());

		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.SECOND, -1);
		result.put("monthEndDate", calendar.getTime());
		return result;
	}

	/**
	 * 获得指定日期的凌晨时间
	 * 
	 * @param srcDate
	 *            :指定的日期
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
	 * 获得指定日期的午夜时间
	 * 
	 * @param srcDate
	 *            :指定的日期
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
	 * 获取2个日期间隔的天数，规则：第一个日期 距离 第二个日期 的天数；如果第二个日期为空，则获取第一日期距离当前系统时间的天数
	 * 
	 * @param firstDate
	 *            : 第一个日期
	 * @param secondDate
	 *            ：第二日期， 如果为Null，则用系统时间代替
	 * @return
	 */
	public static int getSpaceDay(Date firstDate, Date secondDate) {
		if (firstDate == null) {
			return -999999999;
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
		if (calendarFirst.get(Calendar.YEAR) == calendarSecond
				.get(Calendar.YEAR)
				&& calendarFirst.get(Calendar.MONTH) == calendarSecond
						.get(Calendar.MONTH)) {
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
			if (calendarFirst.getTimeInMillis() > calendarSecond
					.getTimeInMillis()) {
				calendarFirst.add(Calendar.SECOND, -1);// 减一秒，得到下一个月末的时间

				// 判断两个日期是否相同的年月
				if (calendarFirst.get(Calendar.YEAR) == calendarSecond
						.get(Calendar.YEAR)
						&& calendarFirst.get(Calendar.MONTH) == calendarSecond
								.get(Calendar.MONTH)) {
					totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH)
							- calendarSecond.get(Calendar.DAY_OF_MONTH);
					return totalDayCount;
				}
				totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH);
			} else {

				// 获得上一个月末的日期
				calendarFirst.add(Calendar.MONTH, 2); // 先加2个月
				calendarFirst.add(Calendar.SECOND, -1);// 在减一秒

				if (calendarFirst.get(Calendar.YEAR) == calendarSecond
						.get(Calendar.YEAR)
						&& calendarFirst.get(Calendar.MONTH) == calendarSecond
								.get(Calendar.MONTH)) {
					totalDayCount += calendarSecond.get(Calendar.DAY_OF_MONTH);
					return -totalDayCount;
				}
				totalDayCount += calendarFirst.get(Calendar.DAY_OF_MONTH);
			}
		}

	}

	/**
	 * 职责：计算工龄，年龄<br/>
	 * 思路：当前时间与入职时间或出生日期做差
	 * 
	 * @param strDate
	 * @return
	 */
	private String getYear(String strDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (strDate.length() > 0) { // 非空判断
			try {
				Date d = bartDateFormat.parse(strDate);
				cal.setTime(d);
			} catch (Exception ec) {
				ec.printStackTrace();
			}

			int year2 = cal.get(Calendar.YEAR);
			int month2 = cal.get(Calendar.MONTH);
			int day2 = cal.get(Calendar.DAY_OF_MONTH);

			int y_c = year - year2;
			int m_c = month = month2;
			int d_c = day - day2;

			if (d_c < 0) {
				m_c -= 1;
			}
			if (m_c < 0) {
				y_c -= 1;
			}

			return "" + y_c;
		} else {
			return "";
		}
	}

	// 用来全局控制 上一周，本周，下一周的周数变化
	private int weeks = 0;
	private int MaxDate;// 一月最大天数
	private int MaxYear;// 一年最大天数

	public static void main(String[] args) {
		TimeTest tt = new TimeTest();
		System.out.println("获取当天日期:" + tt.getNowTime("yyyy-MM-dd"));
		System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
		System.out.println("获取本周日的日期~:" + tt.getCurrentWeekday());
		System.out.println("获取上周一日期:" + tt.getPreviousWeekday());
		System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday());
		System.out.println("获取下周一日期:" + tt.getNextMonday());
		System.out.println("获取下周日日期:" + tt.getNextSunday());
		System.out.println("获得相应周的周六的日期:" + tt.getNowTime("yyyy-MM-dd"));
		System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth1());
		System.out.println("获取本月最后一天日期:" + tt.getDefaultDay11());
		System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst());
		System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd());
		System.out.println("获取下月第一天日期:" + tt.getNextMonthFirst());
		System.out.println("获取下月最后一天日期:" + tt.getNextMonthEnd());
		System.out.println("获取本年的第一天日期:" + tt.getCurrentYearFirst());
		System.out.println("获取本年最后一天日期:" + tt.getCurrentYearEnd());
		System.out.println("获取去年的第一天日期:" + tt.getPreviousYearFirst());
		System.out.println("获取去年的最后一天日期:" + tt.getPreviousYearEnd());
		System.out.println("获取明年第一天日期:" + tt.getNextYearFirst());
		System.out.println("获取明年最后一天日期:" + tt.getNextYearEnd());
		System.out.println("获取本季度第一天到最后一天:" + tt.getThisSeasonTime(11));
		System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:"
				+ TimeTest.getTwoDay("2008-12-1", "2008-9-29"));
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = TimeTest.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public String getDefaultDay11() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public String getFirstDayOfMonth1() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当天时间
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得相应周的周六的日期
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获得上月最后一天的日期
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	// 获得本年有多少天
	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	// 获得本季度
	public String getThisSeasonTime(int month) {
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
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	private int getLastDayOfMonth(int year, int month) {
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
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	// 获取当月第一天
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 计算当月最后一天,返回字符串
	public void getDefaultDay22() {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		// calendar.setTime(new Date());
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		// 打印
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(format.format(calendar.getTime()));
	}

	// 计算昨天
	public Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	// 计算昨天
	public String getYesterday33() {
		Date d = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
		String ZUOTIAN = sp.format(d);// 获取昨天日期
		return ZUOTIAN;
	}

	// 计算今天
	public Date getToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTime();
	}

}

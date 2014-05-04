/**
 * 创建日期：2014-5-4
 */
package com.cxf.myutils;

import java.util.Date;

import junit.framework.TestCase;

/**
 * 职责：DateUtil单元测试类
 * 
 * @author chenxf
 * @version 1.0
 */
public class DateUtilTest extends TestCase {

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#string2Date(java.lang.String, java.lang.String)}
	 * .
	 */
	public void testString2Date() {
		try {
			// 覆盖1
			Date date = DateUtil.string2Date("2014-05-04", "yyyy-MM-dd");
			assertNotNull(date);

			// 覆盖2
			date = DateUtil.string2Date(null, "yyyy-MM-dd");
			assertEquals(date, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#date2String(java.util.Date)}.
	 */
	public void testDate2StringDate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#date2String(java.util.Date, java.lang.String)}
	 * .
	 */
	public void testDate2StringDateString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getMondayAndSundayOfWeek(int)}.
	 */
	public void testGetMondayAndSundayOfWeek() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getMondayAndSundayOfWeekStr(int)}.
	 */
	public void testGetMondayAndSundayOfWeekStr() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getMonthBeginAndEndDay(int)}.
	 */
	public void testGetMonthBeginAndEndDay() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getZeroDate(java.util.Date)}.
	 */
	public void testGetZeroDate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getMidnightDate(java.util.Date)}.
	 */
	public void testGetMidnightDate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getDaysBetweenTwoDate(java.util.Date, java.util.Date)}
	 * .
	 */
	public void testGetDaysBetweenTwoDateDateDate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getDaysBetweenTwoDate(java.lang.String, java.lang.String)}
	 * .
	 */
	public void testGetDaysBetweenTwoDateStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getAge(java.lang.String)}
	 * .
	 */
	public void testGetAge() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getWeek(java.lang.String)}.
	 */
	public void testGetWeek() {
		try {
			String week = DateUtil.getWeek("2014-05-04");
			assertEquals("星期日", week);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getLastDayOfThisMonth()}.
	 */
	public void testGetLastDayOfThisMonth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getFirstDayOfPreviousMonth()}.
	 */
	public void testGetFirstDayOfPreviousMonth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getFirstDayOfThisMonth()}
	 * .
	 */
	public void testGetFirstDayOfThisMonth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getMondayPlus()}.
	 */
	public void testGetMondayPlus() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getLastDayOfPreviousMonth()}.
	 */
	public void testGetLastDayOfPreviousMonth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getNextMonthFirst()}.
	 */
	public void testGetNextMonthFirst() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getLastDayOfNextMonth()}.
	 */
	public void testGetLastDayOfNextMonth() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getLastDayOfNextYear()}.
	 */
	public void testGetLastDayOfNextYear() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getNextYearFirst()}.
	 */
	public void testGetNextYearFirst() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getMaxYear()}.
	 */
	public void testGetMaxYear() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getThisSeasonTime(int)}.
	 */
	public void testGetThisSeasonTime() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.cxf.myutils.DateUtil#getLastDayOfThisMonth(int, int)}.
	 */
	public void testGetLastDayOfThisMonthIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#isLeapYear(int)}.
	 */
	public void testIsLeapYear() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.cxf.myutils.DateUtil#getYesterday()}.
	 */
	public void testGetYesterday() {
		fail("Not yet implemented");
	}

}

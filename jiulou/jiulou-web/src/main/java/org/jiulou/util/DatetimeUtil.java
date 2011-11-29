package org.jiulou.util;

import java.text.SimpleDateFormat;

public class DatetimeUtil {
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	/**
	 * only for format the java.util.Date
	 */
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);
	/**
	 * only for format the java.util.Date
	 */	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
}

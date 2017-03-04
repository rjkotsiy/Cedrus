package com.cedrus.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {

	public static final String UTC_START_TIME_PATTERN = "T00:00:00.000000Z";
	public static final String ISO_DATETIME = "yyyy-MM-dd HH:mm";
	public static final String ISO_DATETIME_WITH_COMMA = "yyyy-MM-dd, HH:mm";
	public static final String ISO_DATE = "yyyy-MM-dd";
	public static final String PATTERN_YYYY_MM_DD_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
	public static final String PATTERN_YYYY_MM_DD_TIME_SHORT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ";
	public static final String PATTERN_YYYY_MM_DD_TIME_SUPER_SHORT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String PATTERN_YYYY_MM_DD_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";
	public static final String UTC_PATTERN_YYYY_MM_DD_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";

	private DateTimeUtils() {
	}
	
	public static Map<TimeUnit, Long> computeDiffDates(Date date1, Date date2) {
		long diffInMillies = date2.getTime() - date1.getTime();
		List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
		Collections.reverse(units);
		Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
		long milliesRest = diffInMillies;
		for (TimeUnit unit : units) {
			long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
			long diffInMilliesForUnit = unit.toMillis(diff);
			milliesRest = milliesRest - diffInMilliesForUnit;
			result.put(unit, diff);
		}
		return result;
	}

	public static LocalDate stringToLocalDate(String date, String pattern) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		if (pattern == null) {
			pattern = ISO_DATE;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(date, formatter);
	}

	public static LocalDateTime stringToLocalDateTime(String date, String pattern) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		if (pattern == null) {
			pattern = PATTERN_YYYY_MM_DD_TIME;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(date, formatter);
	}

	public static DateTime stringToDateTime(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}

		try {
			return DateTime.parse(date, DateTimeFormat.forPattern(PATTERN_YYYY_MM_DD_TIME));
		} catch (IllegalArgumentException exp) {
			return DateTime.parse(date + UTC_START_TIME_PATTERN, DateTimeFormat.forPattern(PATTERN_YYYY_MM_DD_TIME));
		}
	}

	public static LocalDateTime safeConvertToLocalDateTime(String date) {
		if (date == null || date.isEmpty()) {
			return null;
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_TIME);
			return LocalDateTime.parse(date, formatter);
		} catch (Exception e) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_TIME_SHORT);
				return LocalDateTime.parse(date, formatter);
			} catch (Exception e01) {
				try {
					date += UTC_START_TIME_PATTERN;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_TIME);
					return LocalDateTime.parse(date, formatter);
				} catch (Exception e02) {
					return null;
				}
			}
		}
	}

	public static String localDateToStringISO(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_DATE);
		return date.format(formatter);
	}
	
	public static String localDateTimeToStringHuman(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_DATETIME);
		return dateTime.format(formatter);
	}
	
	public static String localDateTimeToStringISO(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_TIME);
		return date.format(formatter);
	}

	public static String localDateToStringISO(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_DATE);
		return date.format(formatter);
	}
	
	public static String formatDateTimeString(String dateTime) {
		return formatDateTimeString(dateTime, ISO_DATETIME);
	}

	public static String formatDateTimeStringToCommaStyle(String dateTime) {
		return formatDateTimeString(dateTime, ISO_DATETIME_WITH_COMMA);
	}

	public static String formatDateTimeString(String dateTime, String format) {
		LocalDateTime localDateTime = DateTimeUtils.safeConvertToLocalDateTime(dateTime);
		if (localDateTime != null && format != null) {
			return localDateTime.format(DateTimeFormatter.ofPattern(format));
		} else {
			return null;
		}
	}
	
	public static String formatDateTimeStringWithTZone(String date) {
		LocalDateTime localDateTime = DateTimeUtils.stringToLocalDateTime(date, PATTERN_YYYY_MM_DD_TIME_ZONE);
		if (localDateTime != null) {
			return localDateTime.format(DateTimeFormatter.ofPattern(ISO_DATETIME));
		} else {
			return null;
		}
	}

	public static String formatTime(String hours, String minutes) {
		return "T" + addZeroToBegin(hours) + ":" + addZeroToBegin(minutes) + ":00.000000Z";
	}

	private static String addZeroToBegin(String number) {
		if (number.length() < 2) {
			return "0" + number;
		} else {
			return number;
		}
	}

	/**
	 * Compare two LocalDateTime in string format by DATE only.
	 */
	public static boolean compareLocalDateTimeStr(String str1, String str2) {
		LocalDate date1 = safeConvertToLocalDateTime(str1).toLocalDate();
		LocalDate date2 = safeConvertToLocalDateTime(str2).toLocalDate();
		return date1.isEqual(date2);
	}
	

}

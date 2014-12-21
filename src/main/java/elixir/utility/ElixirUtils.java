package elixir.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ElixirUtils {
	static final int FIANL_SERVICE_TIME_OFFSET = -1;
	static final int HALF_A_DAY_TIME = 12;
	static final int MORNING_SERVICE_TIME = 6;
	static final int AFTERNOON_SERVICE_TIME = MORNING_SERVICE_TIME + HALF_A_DAY_TIME;
	
	static final String FORMAT_IN_MYSQL = "yyyy/MM/dd HH:mm:ss";
	static final String FORMAT_IN_ARTICLE = "yyyy-MM-dd HH:mm:ss";
	static final int OFFSET_HOURS_IN_SERVICE = -12;
	static final String KOREA_ZONE_ID = "Asia/Seoul";
	
	private static final TimeZone KOREA_ZONE = TimeZone.getTimeZone(KOREA_ZONE_ID);
	private static SimpleDateFormat DATE_FORMAT_IN_ARTILCE = new SimpleDateFormat(FORMAT_IN_ARTICLE);
	
	
	public static Date getDate(int year, int month, int day, int hour) {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.set(year, month , day, hour, 0, 0);
		
		return calendar.getTime();
	}


	public static String getFormattedDate(int year, int month, int day, int hour) {
		
		return DATE_FORMAT_IN_ARTILCE.format(getDate(year, month, day, hour));
	}
	
	public static Date getNow() {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		
		return new Date(calendar.getTimeInMillis());
	}


	public static String formatDate(Date date) {
		
		return DATE_FORMAT_IN_ARTILCE.format(date);
	}

	public static Date parseFormattedDate(String formattedDate) {
		Date date = null;
		
		try {
			date = DATE_FORMAT_IN_ARTILCE.parse(formattedDate);
			
		} catch (ParseException e) {
			// do nothing
		}
		
		return date;
	}

	public static Date nextServiceDate(Date date) {
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.setTime(date);
		
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		int hour = calendar.get(Calendar.HOUR);
		
		int offset = 0;
		if (hour > AFTERNOON_SERVICE_TIME) {
			offset = HALF_A_DAY_TIME - (hour- AFTERNOON_SERVICE_TIME);
		} else if (hour <= AFTERNOON_SERVICE_TIME && hour > MORNING_SERVICE_TIME) {
			offset = AFTERNOON_SERVICE_TIME - hour;
		} else {
			offset = MORNING_SERVICE_TIME - hour;
		}
		
		calendar.add(Calendar.HOUR, offset);
		
		return calendar.getTime();
	}
	
	

	public static String[] getServiceDatesByTime(int year, int month, int day, int hour) {
		String[] dates = new String[2];
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.set(year, month , day, hour, 0, 0);
		calendar.add(Calendar.SECOND, FIANL_SERVICE_TIME_OFFSET);
		
		dates[1] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		calendar.add(Calendar.SECOND, -(FIANL_SERVICE_TIME_OFFSET));
		calendar.add(Calendar.HOUR, OFFSET_HOURS_IN_SERVICE);
		dates[0] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		return dates;
	}


	public static String[] getServiceFormattedDatesByDate(Date date) {
		String[] dates = new String[2];
		Calendar calendar = Calendar.getInstance(KOREA_ZONE);
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, FIANL_SERVICE_TIME_OFFSET);
		
		dates[1] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		calendar.add(Calendar.SECOND, -(FIANL_SERVICE_TIME_OFFSET));
		calendar.add(Calendar.HOUR, OFFSET_HOURS_IN_SERVICE);
		dates[0] = DATE_FORMAT_IN_ARTILCE.format(calendar.getTime());
		
		return dates;
	}


	public static Timestamp toTimestamp(String date) {
		Date parsedDate = ElixirUtils.parseFormattedDate(date);
		
		return new Timestamp(parsedDate.getTime());
	}


	public static Date parse(String format, String formattedDate) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateformat.parse(formattedDate);
		} catch (ParseException e) {
			// do nothing
		}
		
		return date;
	}


	public static String format(String format, Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		
		return dateformat.format(date);
	}



	
}

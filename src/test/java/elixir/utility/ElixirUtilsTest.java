package elixir.utility;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;

public class ElixirUtilsTest {

	@Test
	public void getFormattedDate() {
		String actualDate1 = ElixirUtils.getFormattedDate(2014, Calendar.DECEMBER, 7, 6);
		String actualDate2 = ElixirUtils.getFormattedDate(2014, Calendar.DECEMBER, 7, 18);
		
		assertThat(actualDate1, is("2014-12-07 06:00:00"));
		assertThat(actualDate2, is("2014-12-07 18:00:00"));
		
	}
	
	@Test
	public void parseAndFormat() {
		String format1 = "yyMMdd";
		String format2 = "yyyyMMdd";
		String format3 = "yyyy-MM-dd";
		String format4 = "yyyy.MM.dd HH:mm:ss";
		
		String formattedDate1 = "881207";
		String formattedDate2 = "19881207";
		String formattedDate3 = "1988-12-07";
		String formattedDate4 = "1988.12.07 18:30:00";
		
		Date date1 = ElixirUtils.parse(format1, formattedDate1);
		Date date2 = ElixirUtils.parse(format2, formattedDate2);
		Date date3 = ElixirUtils.parse(format3, formattedDate3);
		Date date4 = ElixirUtils.parse(format4, formattedDate4);
		
		assertThat(formattedDate1, is(ElixirUtils.format(format1, date1)));
		assertThat(formattedDate2, is(ElixirUtils.format(format2, date2)));
		assertThat(formattedDate3, is(ElixirUtils.format(format3, date3)));
		assertThat(formattedDate4, is(ElixirUtils.format(format4, date4)));
		
	}
	
	@Test
	public void format() {
		
		
	}
	
	@Test
	public void getDate() {
		Date actualDate1 = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 6);
		Date actualDate2 = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 18);
		
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		Calendar expectedCal1 = Calendar.getInstance(zone);
		expectedCal1.set(2014,  Calendar.DECEMBER, 7, 6, 0, 0);
		
		Calendar expectedCal2 = Calendar.getInstance(zone);
		expectedCal2.set(2014,  Calendar.DECEMBER, 7, 18, 0, 0);
		
		assertThat(actualDate1.getDate(), is(expectedCal1.getTime().getDate()));
		assertThat(actualDate2.getDate(), is(expectedCal2.getTime().getDate()));
	}
	
	@Test
	public void formatDateByString() {
		Calendar calendar = Calendar.getInstance();
		Date today = new Date(calendar.getTimeInMillis());
		
		String actualFormattedDate = ElixirUtils.formatDate(today);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String expectedFOrmattedDate = format.format(today);
		
		assertThat(actualFormattedDate, is(expectedFOrmattedDate));
		
	}
	
	@Test
	public void formatDateByTimestamp() {
		Calendar calendar = Calendar.getInstance();
		Date today = new Date(calendar.getTimeInMillis());
		Timestamp todayTimestamp = new Timestamp(today.getTime());
		
		String actualFormattedDate = ElixirUtils.formatDate(todayTimestamp);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String expectedFOrmattedDate = format.format(today);
		
		assertThat(actualFormattedDate, is(expectedFOrmattedDate));
		
	}
	
	
	@Test
	public void parseFormattedDate() {
		Calendar calendar = Calendar.getInstance();
		Date expectedDate = new Date(calendar.getTimeInMillis());
		String formattedDate = ElixirUtils.formatDate(expectedDate);
		
		Date actualDate = ElixirUtils.parseFormattedDate(formattedDate);
		assertThat(actualDate.getDate(), is(expectedDate.getDate()));
		
	}
	
	@Test
	public void toTimestamp() {
		String date = "2014-12-07 06:00:00";
		Timestamp actualTimestamp = ElixirUtils.toTimestamp(date);
		String actualDate = ElixirUtils.formatDate(new Date(actualTimestamp.getTime()));
		
		assertThat(actualDate, is(date));
	}

	
	
	@Test
	public void nextServiceDate() {
		List<Date> dates = new ArrayList<Date>();
		dates.add(ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 5));
		dates.add(ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 6));
		dates.add(ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 17));
		dates.add(ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 18));
		dates.add(ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 19));
		
		List<String> actualDates = new ArrayList<String>();
		
		for (Date date : dates) {
			Date next = ElixirUtils.nextServiceDate(date);
			actualDates.add(ElixirUtils.formatDate(next));
		}
		
		assertThat(actualDates.get(0), is("2014-12-07 06:00:00"));
		assertThat(actualDates.get(1), is("2014-12-07 06:00:00"));
		assertThat(actualDates.get(2), is("2014-12-07 18:00:00"));
		assertThat(actualDates.get(3), is("2014-12-07 18:00:00"));
		assertThat(actualDates.get(4), is("2014-12-08 06:00:00"));
		
	}
	
	@Test
	public void getServicFormattedDatesByDate() {
		Date date1 = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 6);
		Date date2 = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 18);
		
		String[] actualDates1 = ElixirUtils.getServiceFormattedDatesByDate(date1);
		String[] actualDates2 = ElixirUtils.getServiceFormattedDatesByDate(date2);
		
		assertThat(actualDates1[0], is("2014-12-06 18:00:00"));
		assertThat(actualDates1[1], is("2014-12-07 05:59:59"));
		assertThat(actualDates2[0], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[1], is("2014-12-07 17:59:59"));
	}
	
	@Test
	public void getServiceDatesByTime() {
		String[] actualDates1 = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 6);
		String[] actualDates2 = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 18);
		
		assertThat(actualDates1[0], is("2014-12-06 18:00:00"));
		assertThat(actualDates1[1], is("2014-12-07 05:59:59"));
		assertThat(actualDates2[0], is("2014-12-07 06:00:00"));
		assertThat(actualDates2[1], is("2014-12-07 17:59:59"));
	}
	
	// create	
	public static List<Date> preparedList() {
		
		return Arrays.asList(new Date[] {
				ElixirUtils.getDate(2014, Calendar.JANUARY, 1, 6),
				ElixirUtils.getDate(2014, Calendar.JANUARY, 2, 6),
				ElixirUtils.getDate(2014, Calendar.JANUARY, 3, 6)
		});
		
	}

}

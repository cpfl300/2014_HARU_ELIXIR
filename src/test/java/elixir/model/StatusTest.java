package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import elixir.utility.ElixirUtils;

public class StatusTest {
	
	private Status first;
	private Status second;
	private Status third;
	
	private Date beforeMorning;
	private Date afterMorning;
	private Date beforeAfternoon;
	private Date afterAfternoon;
	
	@Before
	public void setup() {
		first = new Status("20141207", false);
		second = new Status("20141207", true);
		third = new Status("20141208", false);
		
		beforeMorning = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 5);
		afterMorning = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 6);
		beforeAfternoon = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 17);
		afterAfternoon = ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 18);
	}
	
	@Test
	public void next_day1_morning() {
		Status status = new Status();
		Status actual = null;
		status.setDate("20141207");
		status.setAfternoon(false);
		
		// when1 previous
		actual = status.next(beforeMorning);
		
		// then
		StatusTest.ASSERT(actual, first);

		// when2 current
		actual = status.next(afterMorning);
		
		// then
		StatusTest.ASSERT(actual, second);
		
		// when3 next
		actual = status.next(beforeAfternoon);
		
		// then
		StatusTest.ASSERT(actual, second);
	}
	
	@Test
	public void next_day1_afternoon() {
		Status status = new Status();
		Status actual = null;
		status.setDate("20141207");
		status.setAfternoon(true);
		
		// when1 previous
		actual = status.next(afterMorning);
		
		// then
		StatusTest.ASSERT(actual, second);

		// when2 current
		actual = status.next(beforeAfternoon);
		
		// then
		StatusTest.ASSERT(actual, second);
		
		// when3 next
		actual = status.next(afterAfternoon);
		
		// then
		StatusTest.ASSERT(actual, third);
	}
	
	
	// assert
	public static void ASSERT(Status actual, Status expected) {
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(actual.isAfternoon(), is(expected.isAfternoon()));
	}
	
	

}

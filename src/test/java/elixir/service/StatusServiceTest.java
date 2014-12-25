package elixir.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import elixir.dao.StatusDao;
import elixir.model.Status;
import elixir.model.StatusTest;
import elixir.utility.ElixirUtils;


@RunWith(MockitoJUnitRunner.class)
public class StatusServiceTest {
	
	@InjectMocks
	private StatusService statusService;
	
	@Mock
	private StatusDao statusDao;
	
	private Status lastStatus;
	private Date afternoon;
	private Date dateAtMorning;
	
	
	@Before
	public void setup() {
		afternoon = ElixirUtils.getDate(2014, Calendar.DECEMBER, 17, 13);
		dateAtMorning = ElixirUtils.getDate(2014, Calendar.DECEMBER, 17, 11);
		lastStatus = new Status("20141207", true);
		
	}
	@Test
	public void add() {
		Status status = new Status("20141207", true);
		
		statusService.add(status);
		
		verify(statusDao, times(1)).add(status);
		
	}
	
	@Test
	public void getLastStatus() {		
		when(statusDao.getLast()).thenReturn(lastStatus);
		
		Status actual = statusService.getLastStatus();
		
		verify(statusDao, times(1)).getLast();
		StatusTest.ASSERT(actual, lastStatus);
	}
	

	@Test
	public void next_lastHaruStatus가_다음_서비스_시간일_때() {
		// given
//		when(haruStatusDao.getLastStatus()).thenReturn(lastHaruStatus);
		
		// when
		Status actual = statusService.next();
		
		// then
		assertThat(actual.getDate(), is("20141207"));
		assertThat(actual.isAfternoon(), is(true));
	}

}

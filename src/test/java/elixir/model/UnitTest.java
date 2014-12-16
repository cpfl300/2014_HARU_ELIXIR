package elixir.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class UnitTest {

	@Test
	public void getName() {
		assertThat(Unit.MIN.getName(), is("minute"));
		assertThat(Unit.HOUR.getName(), is("hour"));
		assertThat(Unit.DAY.getName(), is("day"));
	}
	
	@Test
	public void valueOf() {
		assertThat(Unit.getEnum("minute"), is(Unit.MIN));
		assertThat(Unit.getEnum("hour"), is(Unit.HOUR));
		assertThat(Unit.getEnum("day"), is(Unit.DAY));		
	}

}

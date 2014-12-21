package elixir.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CountUnitTest {

	@Test
	public void getName() {
		assertThat(CountUnit.MIN.getName(), is("minute"));
		assertThat(CountUnit.HOUR.getName(), is("hour"));
		assertThat(CountUnit.DAY.getName(), is("day"));
	}
	
	@Test
	public void valueOf() {
		assertThat(CountUnit.getEnum("minute"), is(CountUnit.MIN));
		assertThat(CountUnit.getEnum("hour"), is(CountUnit.HOUR));
		assertThat(CountUnit.getEnum("day"), is(CountUnit.DAY));		
	}

}

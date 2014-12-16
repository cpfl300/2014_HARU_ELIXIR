package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ServiceUnitTest {
	
	@Test
	public void getValue() {
		assertThat(ServiceUnit.MORNING.getValue(), is(false));
		assertThat(ServiceUnit.AFTERNOON.getValue(), is(true));
	}
	
	@Test
	public void getEnum() {
		assertThat(ServiceUnit.getEnum(false), is(ServiceUnit.MORNING));
		assertThat(ServiceUnit.getEnum(true), is(ServiceUnit.AFTERNOON));
	}
}

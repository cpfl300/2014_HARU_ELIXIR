package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ServiceUnitTest {
	
	@Test
	public void getValue() {
		assertThat(ServiceUnit.MORNING.getValue(), is(0));
		assertThat(ServiceUnit.AFTERNOON.getValue(), is(1));
	}
	
	@Test
	public void getEnum() {
		assertThat(ServiceUnit.getEnum(0), is(ServiceUnit.MORNING));
		assertThat(ServiceUnit.getEnum(1), is(ServiceUnit.AFTERNOON));
	}
}

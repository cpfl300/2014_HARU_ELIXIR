package elixir.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class StatusTest {

	public static void ASSERT(Status actual, Status expected) {
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(actual.isAfternoon(), is(expected.isAfternoon()));
	}

}

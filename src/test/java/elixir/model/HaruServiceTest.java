package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HaruServiceTest {

	// assert
	public static void ASSERT(HaruService actual, HaruService expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(actual.getStatus(), is(expected.getStatus()));
	}
	
	// create
	public static HaruService create(int id, String date, ServiceUnit serviceUnit) {
		HaruService service = new HaruService();
		
		service.setId(id);
		service.setDate(date);
		service.setStatus(serviceUnit);
		
		return service;
	}

}

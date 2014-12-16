package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class HaruServiceTest {

	// assert
		public static void ASSERT(HaruService actual, HaruService expected) {
			assertThat(actual.getId(), is(expected.getId()));
			assertThat(actual.getDate(), is(expected.getDate()));
			assertThat(actual.getStatus(), is(expected.getStatus()));
		}
		
		public static void ASSERTS(List<HaruService> actuals, List<HaruService> expecteds) {
			assertThat(actuals.size(), is(expecteds.size()));
			for (int i=0; i<actuals.size(); i++) {
				HaruService actual = actuals.get(i);
				HaruService expected = expecteds.get(i);
				
				HaruServiceTest.ASSERT(actual, expected);
			}		
		}
		
		// create
		public static HaruService create(int id, String date, ServiceUnit serviceUnit) {
			HaruService service = new HaruService();
			
			service.setId(id);
			service.setDate(date);
			service.setStatus(serviceUnit);
			
			return service;
		}
		
		public static List<HaruService> preparedList() {
			
			return Arrays.asList(new HaruService[] {
					HaruServiceTest.create(1, "20140101", ServiceUnit.MORNING),
					HaruServiceTest.create(2, "20140102", ServiceUnit.AFTERNOON),
					HaruServiceTest.create(3, "20140103", ServiceUnit.MORNING)
			});
		}

}

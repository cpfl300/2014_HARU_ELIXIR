package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class HaruServicesTest {


	public static List<HaruService> preparedList() {
		
		return Arrays.asList(new HaruService[] {
				HaruServiceTest.create(1, "20140101", ServiceUnit.MORNING),
				HaruServiceTest.create(2, "20140102", ServiceUnit.AFTERNOON),
				HaruServiceTest.create(3, "20140103", ServiceUnit.MORNING)
		});
	}

	public static void ASSERTS(List<HaruService> actuals, List<HaruService> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			HaruService actual = actuals.get(i);
			HaruService expected = expecteds.get(i);
			
			HaruServiceTest.ASSERT(actual, expected);
		}		
	}

}

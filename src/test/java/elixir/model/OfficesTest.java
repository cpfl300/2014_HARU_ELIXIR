package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import elixir.test.ElixirTestUtils;

public class OfficesTest {

	public static List<Office> preparedList() {
		
		return Arrays.asList(new Office[] {
				OfficeTest.create(1, "101", "경향신문", "종합"),
				OfficeTest.create(19, "209", "한국경제", "경제"),
				OfficeTest.create(74, "711", "인벤", "인터넷")
		});
	}

	public static List<Office> preparedList(String[] fields) {
		List<Office> offices = preparedList();
		ElixirTestUtils.initComplementaryFields(offices, fields);
		
		return offices;
	}

	public static void ASSERTS(List<Office> actuals, List<Office> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Office actual = actuals.get(i);
			Office expected = expecteds.get(i);
			
			OfficeTest.ASSERT(actual, expected);
		}		
	}

}

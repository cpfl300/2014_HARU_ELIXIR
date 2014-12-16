package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class CountMethodTest {
	
	
	// create
	public static CountMethod create(int id, int value, Unit unit) {
		CountMethod countMethod = new CountMethod();
		
		countMethod.setId(id);
		countMethod.setValue(value);
		countMethod.setUnit(unit);
		
		return countMethod;
		
	}
	
	// assert
	public static void ASSERT(CountMethod actual, CountMethod expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getValue(), is(expected.getValue()));
		assertThat(actual.getUnit(), is(expected.getUnit()));
	}
	
	public static void ASSERTS(List<CountMethod> actuals, List<CountMethod> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			CountMethod actual = actuals.get(i);
			CountMethod expected = expecteds.get(i);
			
			CountMethodTest.ASSERT(actual, expected);
		}		
	}
	
	
	public static List<CountMethod> preparedList() {
		
		return Arrays.asList(new CountMethod[] {
				CountMethodTest.create(1, 5, Unit.MIN),
				CountMethodTest.create(2, 1, Unit.HOUR),
				CountMethodTest.create(3, 1, Unit.DAY)
		});
	}
}

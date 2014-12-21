package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class SignaturesTest {
	
	public static void ASSERTS(List<Signature> actuals, List<Article> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Signature actual = actuals.get(i);
			Article expected = expecteds.get(i);
			
			SignatureTest.ASSERT(actual, expected);
		}	
	}

	public static List<Signature> preparedList() {
		
		return Arrays.asList(new Signature[]{
				SignatureTest.create("001", "0006718568"),
				SignatureTest.create("003", "0005636597"),
				SignatureTest.create("277", "0003177935")});
	}
}

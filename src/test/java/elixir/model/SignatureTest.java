package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class SignatureTest {

	public static void ASSERT(Signature actual, Article expected) {
		assertThat(actual.getOfficeId(), is(expected.getOffice().getOfficeId()));
		assertThat(actual.getArticleId(), is(expected.getArticleId()));
	}
	
	
	public static Signature create(String officeId, String articleId) {
		Signature sign = new Signature();
		
		sign.setArticleId(articleId);
		sign.setOfficeId(officeId);
		
		return sign;
	}

	public static List<Signature> preparedList() {
		
		return Arrays.asList(new Signature[]{
				SignatureTest.create("001", "0006718568"),
				SignatureTest.create("003", "0005636597"),
				SignatureTest.create("277", "0003177935")});
	}
}

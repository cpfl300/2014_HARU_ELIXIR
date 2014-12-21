package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
}

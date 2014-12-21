package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ArticlesTest {
	
	private List<Article> articles;
	
	@Before
	public void setup() {
		List<List<Section>> sectionsList = new ArrayList<List<Section>>();
		sectionsList.add(SectionTest.preparedList1());
		sectionsList.add(SectionTest.preparedList2());
		sectionsList.add(SectionTest.preparedList2());
		
		articles = ArticleTest.preparedList(OfficeTest.preparedList(), sectionsList);
	}

	@Test
	public void sign() {
		List<Signature> signatures = Articles.sign(articles);
		
		SignaturesTest.ASSERTS(signatures, articles);
	}
	
	// assert
	public static void ASSERTS(List<Article> actuals, List<Article> expecteds, String[] fieldArr) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Article actual = actuals.get(i);
			Article expected = expecteds.get(i);
			
			ArticleTest.ASSERT(actual, expected, fieldArr);
		}		
	}

}

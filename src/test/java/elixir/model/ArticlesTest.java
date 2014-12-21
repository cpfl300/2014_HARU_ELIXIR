package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.test.ElixirTestUtils;

public class ArticlesTest {
	
	private List<Article> articles;
	
	@Before
	public void setup() {
		List<List<Section>> sectionsList = new ArrayList<List<Section>>();
		sectionsList.add(SectionsTest.preparedList1());
		sectionsList.add(SectionsTest.preparedList2());
		sectionsList.add(SectionsTest.preparedList2());
		
		articles = ArticlesTest.preparedList(OfficesTest.preparedList(), sectionsList);
	}

	@Test
	public void sign() {
		List<Signature> signatures = Articles.sign(articles);
		
		SignaturesTest.ASSERTS(signatures, articles);
	}
	
	public static List<Article> preparedList(List<Office> offices, List<List<Section>> sectionsList, String[] fields) {
		List<Article> articles = preparedList(offices, sectionsList);
		ElixirTestUtils.initComplementaryFields(articles, fields);
		
		return articles;
	}

	public static List<Article> preparedList(List<Office> offices, List<List<Section>> sectionsList) {
		
		return Arrays.asList(new Article[] {
				ArticleTest.create(1, "111", offices.get(0), sectionsList.get(0), "content1", "20140101", "010101", "title1", "orgUrl1", "imageUrl1"),
				ArticleTest.create(2, "222", offices.get(1), sectionsList.get(1), "content2", "20140102", "010102", "title2", "orgUrl2", "imageUrl2"),
				ArticleTest.create(3, "333", offices.get(2), sectionsList.get(2), "content3", "20140103", "010103", "title3", "orgUrl3", "imageUrl3")
		});
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

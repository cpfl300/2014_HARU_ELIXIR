package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import elixir.test.ElixirTestUtils;

public class ArticleTest {
	
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
		for (Article article : articles) {
			Signature signature = article.sign();
			SignatureTest.ASSERT(signature, article);
		}
	}
	
	@Test(expected=SignatureFailureException.class)
	public void sign_failure() {
		articles.get(0).setOffice(null);
		
		for (Article article : articles) {
			Signature signature = article.sign();
			SignatureTest.ASSERT(signature, article);
		}
	}
	


	public static void ASSERT(Article actual, Article expected, String[] fieldArr) {
		List<String> fields = Arrays.asList(fieldArr);
		
		if (fields.contains("id"))
				assertThat(actual.getId(), is(expected.getId()));
		
		if (fields.contains("articleId"))
			assertThat(actual.getArticleId(), is(expected.getArticleId()));
		
		if (fields.contains("office") && expected.getOffice() != null)
			OfficeTest.ASSERT(actual.getOffice(), expected.getOffice());
		
		if (fields.contains("id") && expected.getSections() != null)
			SectionsTest.ASSERT(actual.getSections(), expected.getSections());
		
		if (fields.contains("title"))
			assertThat(actual.getTitle(), is(expected.getTitle()));
		
		if (fields.contains("content"))
			assertThat(actual.getContent(), is(expected.getContent()));
		
		if (fields.contains("orgUrl"))
			assertThat(actual.getOrgUrl(), is(expected.getOrgUrl()));
		
		if (fields.contains("contributionDate"))
			assertThat(actual.getContributionDate(), is(expected.getContributionDate()));
		
		if (fields.contains("contributionTime"))
			assertThat(actual.getContributionTime(), is(expected.getContributionTime()));
		
		if (fields.contains("imageUrl"))
			assertThat(actual.getImageUrl(), is(expected.getImageUrl()));
		
		if (fields.contains("score"))
			assertThat(actual.getScore(), is(expected.getScore()));
		
		if (fields.contains("timestamp"))
			assertThat(actual.getTimestamp(), is(expected.getTimestamp()));
	}


	// create
	public static Article create(int id, String articleId, Office office, List<Section> sections, String content, String contributionDate, String contributionTime,
			String title, String orgUrl, String imageUrl) {
		Article article = new Article();
		
		article.setId(id);
		article.setArticleId(articleId);
		article.setOffice(office);
		article.setSections(sections);
		article.setContent(content);
		article.setContributionDate(contributionDate);
		article.setContributionTime(contributionTime);
		article.setTitle(title);
		article.setOrgUrl(orgUrl);
		article.setImageUrl(imageUrl);
		
		return article;
	}

	
	public static List<Article> preparedList(List<Office> offices, List<List<Section>> sectionsList) {
		
		return Arrays.asList(new Article[] {
				ArticleTest.create(1, "111", offices.get(0), sectionsList.get(0), "content1", "20140101", "010101", "title1", "orgUrl1", "imageUrl1"),
				ArticleTest.create(2, "222", offices.get(1), sectionsList.get(1), "content2", "20140102", "010102", "title2", "orgUrl2", "imageUrl2"),
				ArticleTest.create(3, "333", offices.get(2), sectionsList.get(2), "content3", "20140103", "010103", "title3", "orgUrl3", "imageUrl3")
		});
	}
	
	public static List<Article> preparedList(List<Office> offices, List<List<Section>> sectionsList, String[] fields) {
		List<Article> articles = ArticleTest.preparedList(offices, sectionsList);
		ElixirTestUtils.initComplementaryFields(articles, fields);
		
		return articles;
	}
	

}

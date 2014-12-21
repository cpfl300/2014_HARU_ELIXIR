package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ArticleTest {
	
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
	

}

package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elixir.utility.ElixirUtils;

public class ArticleTest {
	private static final Logger log = LoggerFactory.getLogger(ArticleTest.class);
	
	private static final String FACK_STR = "-----";
	
	private List<Article> articles;
	private List<Date> dates;
	
	
	@Before
	public void setup() {
		prepareDates();
		
	}
	
	
	



	private void prepareArticles(Article[] articleArr) {
		articles = Arrays.asList(articleArr);
	}



	private void prepareDates() {
		dates = Arrays.asList(new Date[]{
				ElixirUtils.getDate(2014, Calendar.DECEMBER, 7, 6),
				ElixirUtils.getDate(2014, Calendar.DECEMBER, 8, 6),
				ElixirUtils.getDate(2014, Calendar.DECEMBER, 9, 6)
		});
		
	}
	
	
	private void NEW_ASSERT(Article actual, Article expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getArticleId(), is(expected.getArticleId()));
		assertThat(actual.getOffice().getOfficeId(), is(expected.getOffice().getOfficeId()));
		assertThat(actual.getSection().getSectionId(), is(expected.getSection().getSectionId()));
		assertThat(actual.getContributionDate(), is(expected.getContributionDate()));
		assertThat(actual.getContributionTime(), is(expected.getContributionTime()));
		
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getContent(), is(expected.getContent()));
		assertThat(actual.getOrgUrl(), is(expected.getOrgUrl()));
		assertThat(actual.getImageUrl(), is(expected.getImageUrl()));
		assertThat(actual.getScore(), is(expected.getScore()));
		
		assertThat(actual.getTimestamp(), is(expected.getTimestamp()));
	}
	

	public static void ASSERT(Article actual, Article expected) {
		assertThat(actual.getArticleId(), is(expected.getArticleId()));
		assertThat(actual.getOffice().getOfficeId(), is(expected.getOffice().getOfficeId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getContent(), is(expected.getContent()));
		assertThat(actual.getOrgUrl(), is(expected.getOrgUrl()));
		
		if (expected.getSection() != null) {
			assertThat(actual.getSection().getSectionId(), is(expected.getSection().getSectionId()));			
		}
		
		assertThat(actual.getContributionDate(), is(expected.getContributionDate()));
		assertThat(actual.getContributionTime(), is(expected.getContributionTime()));
		assertThat(actual.getImageUrl(), is(expected.getImageUrl()));
		
	}

	public static void ASSERTS(List<Article> actuals, List<Article> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Article actual = actuals.get(i);
			Article expected = expecteds.get(i);
			
			ArticleTest.ASSERT(actual, expected);
		}		
	}

	// create
	public static Article create(int id, String articleId, Office office, Section section, String content, String contributionDate, String contributionTime,
			String title, String orgUrl, String imageUrl) {
		Article article = new Article();
		
		article.setId(id);
		article.setArticleId(articleId);
		article.setOffice(office);
		article.setSection(section);
		article.setContent(content);
		article.setContributionDate(contributionDate);
		article.setContributionTime(contributionTime);
		article.setTitle(title);
		article.setOrgUrl(orgUrl);
		article.setImageUrl(imageUrl);
		
		return article;
	}

	
	public static List<Article> preparedList(List<Office> offices, List<Section> sections) {
		
		return Arrays.asList(new Article[] {
				ArticleTest.create(1, "111", offices.get(0), sections.get(0), "content1", "20140101", "010101", "title1", "orgUrl1", "imageUrl1"),
				ArticleTest.create(2, "222", offices.get(1), sections.get(1), "content2", "20140102", "010102", "title2", "orgUrl2", "imageUrl2"),
				ArticleTest.create(3, "333", offices.get(2), sections.get(2), "content3", "20140103", "010103", "title3", "orgUrl3", "imageUrl3")
		});
	}

}

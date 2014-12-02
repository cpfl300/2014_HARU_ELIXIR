package elixir.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import elixir.config.ElixirConfig;
import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.model.Journal;
import elixir.model.Section;
import elixir.utility.ElixirUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElixirConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class ArticleDaoTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private HotissueDao hotissueDao;
	
	private List<Article> articles;
	
	private Article article1;
	private Article article2;
	private Article article3;
	private Article article4;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	private Journal journal1;
	private Journal journal2;
	private Journal journal3;
	
	private Section section1;
	private Section section2;
	private Section section3;
	
	@Before
	public void setup() {
		// fixture
		makeJournalFixtures();
		makeSectionFixtures();
		makeHotissueFixtures();
		makeArticleFixtures();
		
	}
	
	// read	
	@Test
	public void getCount() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
	}
	
	// delete
	@Test
	public void deleteById() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		assertThat(articleDao.getCount(), is(3));
		
		assertThat(articleDao.deleteById(1), is(1));
		assertThat(articleDao.getCount(), is(2));
		
		assertThat(articleDao.deleteById(2), is(1));
		assertThat(articleDao.getCount(), is(1));
		
		assertThat(articleDao.deleteById(3), is(1));
		assertThat(articleDao.getCount(), is(0));
	}
	
	@Test
	public void deleteAll() {
		prepareHotissues();
		prepareArticleDao();
		
		assertThat(articleDao.deleteAll(), is(0));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.addArticle(article1);
		assertThat(articleDao.deleteAll(), is(1));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		assertThat(articleDao.deleteAll(), is(2));
		assertThat(articleDao.getCount(), is(0));
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		assertThat(articleDao.deleteAll(), is(3));
		assertThat(articleDao.getCount(), is(0));
	
	}
	
	// create article
	@Test
	public void addArticle() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.addArticle(article1);
		assertThat(articleDao.getCount(), is(1));
		
		articleDao.addArticle(article2);
		assertThat(articleDao.getCount(), is(2));
		
		articleDao.addArticle(article3);
		assertThat(articleDao.getCount(), is(3));
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void addArticle_DuplicationKeyException() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article1);
		assertThat(articleDao.getCount(), is(2));
	}
	
	// create articles
	@Test
	public void addArticles() {
		prepareHotissues();
		prepareArticleDao();
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		int[] actualCounts = articleDao.addArticles(articles);
		assertThat(actualCounts.length, is(3));
	}
	
	@Test
	public void addArticlesIncludedDuplicateKey() {
		prepareHotissues();
		prepareArticleDao();
		
		articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article1);
		articles.add(article1);
		
		int actualCounts[] = articleDao.addArticles(articles);
		int actualCount = 0;
		for (int affectedRow : actualCounts) {
			actualCount += affectedRow;
		}

		assertThat(actualCount, is(1));
	}
	
	// read by id
	@Test
	public void addAndFind() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.addArticle(this.article1);
		Article actualArticle1 = articleDao.findById(1);
		assertSameArticle(actualArticle1, this.article1);
		
		articleDao.addArticle(this.article2);
		Article actualArticle2 = articleDao.findById(2);
		assertSameArticle(actualArticle2, this.article2);
		
		articleDao.addArticle(this.article3);
		Article actualArticle3 = articleDao.findById(3);
		assertSameArticle(actualArticle3, this.article3);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void notFound() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.addArticle(this.article1);
		Article actualArticle1 = articleDao.findById(4);
		assertSameArticle(actualArticle1, this.article1);
		
	}
	
	// read by date
	@Test
	public void findBetweenDates() throws ParseException {
		prepareHotissues();
		prepareArticleDao();
		
		article1.setDate("2014-12-07 05:59:59");
		article2.setDate("2014-12-07 06:00:00");
		article3.setDate("2014-12-07 17:59:59");
		article4.setDate("2014-12-07 18:00:00");
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		articleDao.addArticle(article4);

	    String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER , 7, 18);
		List<Article> actualArticles = articleDao.findBetweenDates(dates);
		
		assertThat(actualArticles.size(), is(2));
		assertThat(actualArticles.get(0).getId(), is(2));
		assertThat(actualArticles.get(1).getId(), is(3));
	}
	
	// read by ordered score
	@Test
	public void findByScoreOrderFromOneTo() {
		prepareHotissues();
		prepareArticleDao();
		
		article1.setScore(10.1);
		article2.setScore(20.1);
		article3.setScore(30.1);
		
		List<Article> articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
		int[] updateStatus = articleDao.addArticles(articles);
		assertThat(getCount(updateStatus), is(3));
		
		final int size = 2;
		List<Article> actualArticles = articleDao.findByScoreOrderFromOneTo(size);
		
		assertThat(actualArticles.size(), is(size));
		assertThat(actualArticles.get(0).getScore(), is(30.1));
		assertThat(actualArticles.get(1).getScore(), is(20.1));
	}
	

	
	// udpate score
	@Test
	public void updateScores() {
		prepareHotissues();
		prepareArticleDao();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		
		List<Article> updatedArticles = new ArrayList<Article>();
		updatedArticles.add(new Article(article1.getId(), 11.1));
		updatedArticles.add(new Article(article2.getId(), 22.2));
		updatedArticles.add(new Article(article3.getId(), 33.3));
		
		int[] state = articleDao.updateScores(updatedArticles);
		
		assertThat(getCount(state), is(3));
		assertThat(articleDao.findById(article1.getId()).getScore(), is(updatedArticles.get(0).getScore()));
		assertThat(articleDao.findById(article2.getId()).getScore(), is(updatedArticles.get(1).getScore()));
		assertThat(articleDao.findById(article3.getId()).getScore(), is(updatedArticles.get(2).getScore()));
		
	}
	

	// at half_day
	// read at half day
	@Test
	public void getCountAtHalfDay() {
		int actualCount = articleDao.getCountAtHalfDay();
		assertThat(actualCount, is(0));
	}
	
	
	// delete at half day
	@Test
	public void deleteAllAtHalfDay() {
		articleDao.deleteAllAtHalfDay();
		assertThat(articleDao.getCountAtHalfDay(), is(0));
	}
	
	// add article at half day
	@Test
	public void addArticleAtHalfDay() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// add
		articleDao.addArticleAtHalfDay(new Article(article1.getId(), 1, date));
		assertThat(articleDao.getCountAtHalfDay(), is(1));
		
		articleDao.addArticleAtHalfDay(new Article(article2.getId(), 2, date));
		assertThat(articleDao.getCountAtHalfDay(), is(2));
		
		articleDao.addArticleAtHalfDay(new Article(article3.getId(), 2, date));
		assertThat(articleDao.getCountAtHalfDay(), is(3));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void addArticleAtHalfDay_NonexistForeignKey() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// add
		articleDao.addArticleAtHalfDay(new Article(article1.getId(), 1, date));
		assertThat(articleDao.getCountAtHalfDay(), is(1));
		
		// add - except
		articleDao.addArticleAtHalfDay(new Article(article2.getId(), 2, date));
		assertThat(articleDao.getCountAtHalfDay(), is(2));
	}
	
	// add articles at half day
	@Test
	public void addArticlesAtHalfDay() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		List<Article> articlesForHalfDay = new ArrayList<Article>();
		articlesForHalfDay.add(new Article(article1.getId(), 1, date));
		articlesForHalfDay.add(new Article(article2.getId(), 2, date));
		articlesForHalfDay.add(new Article(article3.getId(), 3, date));
		
		// add
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(3));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void addArticlesAtHalfDay_NonexistForeignKey() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		List<Article> articlesForHalfDay = new ArrayList<Article>();
		articlesForHalfDay.add(new Article(article1.getId(), 1, date));
		articlesForHalfDay.add(new Article(article2.getId(), 2, date));
		articlesForHalfDay.add(new Article(article3.getId(), 3, date));
		
		// add
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(1));
	}

	
	// read
	@Test
	public void addAndfindByIdAtHalfDay() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// input
		int id1 = articleDao.addArticleAtHalfDay(new Article(article1.getId(), 1, date));
		int id2 = articleDao.addArticleAtHalfDay(new Article(article2.getId(), 2, date));
		int id3 = articleDao.addArticleAtHalfDay(new Article(article3.getId(), 3, date));
		assertThat(articleDao.getCountAtHalfDay(), is(3));
		
		// find
		Article actual1 = articleDao.findByIdAtHalfDay(id1);
		assertSameArticle(actual1, article1, 1);
		
		Article actual2 = articleDao.findByIdAtHalfDay(id2);
		assertSameArticle(actual2, article2, 2);
		
		Article actual3 = articleDao.findByIdAtHalfDay(id3);
		assertSameArticle(actual3, article3, 3);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void addAndfindByIdAtHalfDay_EmptyResultDataAccessException() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// input
		articleDao.addArticleAtHalfDay(new Article(article1.getId(), 1, date));
		assertThat(articleDao.getCountAtHalfDay(), is(1));
		
		// find - except
		Article actual1 = articleDao.findByIdAtHalfDay(0);
		assertSameArticle(actual1, article1, 1);
	}
	
	
	@Test
	public void findByArticleIdAtHalfDay() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// input
		List<Article> articlesForHalfDay = new ArrayList<Article>();
		articlesForHalfDay.add(new Article(article1.getId(), 1, date));
		articlesForHalfDay.add(new Article(article2.getId(), 2, date));
		articlesForHalfDay.add(new Article(article3.getId(), 3, date));
		
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(3));
		
		// find
		Article actual1 = articleDao.findByArticleIdAtHalfDay(articlesForHalfDay.get(0).getId());
		assertSameArticle(actual1, article1, 1);
		
		Article actual2 = articleDao.findByArticleIdAtHalfDay(articlesForHalfDay.get(1).getId());
		assertSameArticle(actual2, article2, 2);
		
		Article actual3 = articleDao.findByArticleIdAtHalfDay(articlesForHalfDay.get(2).getId());
		assertSameArticle(actual3, article3, 3);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findByArticleIdAtHalfDay_EmptyResultDataAccessException() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		
		// date
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// input
		articleDao.addArticleAtHalfDay(new Article(article1.getId(), 1, date));
		assertThat(articleDao.getCountAtHalfDay(), is(1));
		
		// find - except
		Article actual1 = articleDao.findByArticleIdAtHalfDay(0);
		assertSameArticle(actual1, article1, 1);
	}
	


	@Test
	public void findBetweenDatesAtHalfDay() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		articleDao.addArticle(article4);
		
		// service dates
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		
		List<Article> articlesForHalfDay = new ArrayList<Article>();
		articlesForHalfDay.add(new Article(article1.getId(), 1, ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 27, 17)));
		articlesForHalfDay.add(new Article(article2.getId(), 2, dates[0]));
		articlesForHalfDay.add(new Article(article3.getId(), 3, dates[1]));
		articlesForHalfDay.add(new Article(article4.getId(), 4, ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 18)));
		
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(4));
		
		// find
		List<Article> actualArticles = articleDao.findBetweenDatesAtHalfDay(dates);
		assertThat(actualArticles.size(), is(2));
		assertSameArticle(actualArticles.get(0), article2);
		assertSameArticle(actualArticles.get(1), article3);
	}
	
	
	@Test
	public void notFoundBetweenDatesAtHalfDay() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		articleDao.addArticle(article4);
		
		// service dates
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		String[] fakeDates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 30, 6);
		
		// input
		List<Article> articlesForHalfDay = new ArrayList<Article>();
		articlesForHalfDay.add(new Article(article1.getId(), 1, dates[0]));
		articlesForHalfDay.add(new Article(article2.getId(), 2, dates[0]));
		articlesForHalfDay.add(new Article(article3.getId(), 3, dates[1]));
		articlesForHalfDay.add(new Article(article4.getId(), 4, dates[1]));
		
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(4));
		
		// find
		List<Article> actualArticles = articleDao.findBetweenDatesAtHalfDay(fakeDates);
		assertThat(actualArticles.size(), is(0));
	}
	
	
	@Test
	public void findBySequenceBetweenDatesAtHalfDay() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		List<Article> articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		articles.add(article4);
		
		int[] updateState = articleDao.addArticles(articles);
		assertThat(getCount(updateState), is(4));
		
		// service dates
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		
		// input
		List<Article> articlesForHalfDay = new ArrayList<Article>();
		articlesForHalfDay.add(new Article(article1.getId(), 1, dates[0]));
		articlesForHalfDay.add(new Article(article2.getId(), 2, dates[0]));
		articlesForHalfDay.add(new Article(article3.getId(), 3, dates[1]));
		articlesForHalfDay.add(new Article(article4.getId(), 4, dates[1]));
		
		updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(4));
		
		// find
		for (int i=0; i<4; i++) {
			int sequence = i+1;
			Article actual = articleDao.findBySequenceBetweenDatesAtHalfDay(dates, sequence);
			assertSameArticle(actual, articles.get(i), sequence);
		}		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findBySequenceBetweenDatesAtHalfDay_EmptyResultDataAccessException() {
		// prepare
		prepareHotissues();
		prepareArticleDao();
		prepareArticleDaoAtHalfDay();
		
		List<Article> articles = new ArrayList<Article>();
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		articles.add(article4);
		
		int[] updateState = articleDao.addArticles(articles);
		assertThat(getCount(updateState), is(4));
		
		// service dates
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		
		// input
		List<Article> articlesForHalfDay = new ArrayList<Article>();
		articlesForHalfDay.add(new Article(article1.getId(), 1, dates[0]));
		articlesForHalfDay.add(new Article(article2.getId(), 2, dates[0]));
		articlesForHalfDay.add(new Article(article3.getId(), 3, dates[1]));
		articlesForHalfDay.add(new Article(article4.getId(), 4, dates[1]));
		
		updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(4));
		
		// find - except
		articleDao.findBySequenceBetweenDatesAtHalfDay(dates, 5);	
	}
	
	private void assertSameArticle(Article actual, Article expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getHotissue().getId(), is(expected.getHotissue().getId()));
		assertThat(actual.getJournal().getId(), is(expected.getJournal().getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getSection().getId(), is(expected.getSection().getId()));
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(ElixirUtils.parseFormattedDate(actual.getTimestamp()), is(Date.class));
		assertThat(actual.getContent(), is(expected.getContent()));
		assertThat(actual.getHits(), is(expected.getHits()));
		assertThat(actual.getCompletedReadingCount(), is(expected.getCompletedReadingCount()));
		assertThat(actual.getScore(), is(expected.getScore()));
	}
	
	private void assertSameArticle(Article actual, Article expected, int sequence) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getHotissue().getId(), is(expected.getHotissue().getId()));
		assertThat(actual.getJournal().getId(), is(expected.getJournal().getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getSection().getId(), is(expected.getSection().getId()));
		assertThat(actual.getDate(), is(expected.getDate()));
		assertThat(ElixirUtils.parseFormattedDate(actual.getTimestamp()), is(Date.class));
		assertThat(actual.getContent(), is(expected.getContent()));
		assertThat(actual.getHits(), is(expected.getHits()));
		assertThat(actual.getCompletedReadingCount(), is(expected.getCompletedReadingCount()));
		assertThat(actual.getScore(), is(expected.getScore()));
		
		assertThat(actual.getSequence(), is(sequence));
	}
	
	private int getCount(int[] affectedRows) {
		int size = 0;
		
		for (int row : affectedRows) {
			size += row;
		}
		
		return size;
	}

	private void prepareHotissues() {
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
	}
	
	private void prepareArticleDao() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
	}
	
	private void prepareArticleDaoAtHalfDay() {
		articleDao.deleteAllAtHalfDay();
		assertThat(articleDao.getCountAtHalfDay(), is(0));
	}

	private void makeSectionFixtures() {
		section1 = new Section(3);
		section2 = new Section(10);
		section3 = new Section(23);		
	}


	private void makeJournalFixtures() {
		journal1 = new Journal(84);
		journal2 = new Journal(10);
		journal3 = new Journal(23);		
	}
	
	private void makeHotissueFixtures() {
		hotissue1 = new Hotissue(1, "hotissue1");
		hotissue2 = new Hotissue(2, "hotissue2");
		hotissue3 = new Hotissue(3, "hotissue3");
	}
	
	private void makeArticleFixtures() {
		article1 = new Article(1, hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000);
		article2 = new Article(2, hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000);
		article3 = new Article(3, hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000);
		article4 = new Article(4, hotissue3, journal3, section3, "title4", "1444-04-04 04:11:11", "content4", 40000, 10000);
	}

}

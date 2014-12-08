package elixir.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Arrays;
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
		makeFixtures();		
	}
	
	// read	
	@Test
	public void getCount() {
		initDao();
		
		// getCount
		articleDao.add(article1);
		assertThat(articleDao.getCount(), is(1));
		
		articleDao.add(article2);
		assertThat(articleDao.getCount(), is(2));
		
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
	}
	
	// delete
	@Test
	public void deleteById() {
		initDao();
		prepareArticleDao(new Article[]{article1, article2, article3});
		assertThat(articleDao.getCount(), is(3));
		
		// delete
		assertThat(articleDao.deleteById(article1.getId()), is(1));
		assertThat(articleDao.getCount(), is(2));
		
		assertThat(articleDao.deleteById(article2.getId()), is(1));
		assertThat(articleDao.getCount(), is(1));
		
		assertThat(articleDao.deleteById(article3.getId()), is(1));
		assertThat(articleDao.getCount(), is(0));
	}
	
	@Test
	public void deleteAll() {
		initDao();
		prepareArticleDao(new Article[]{article1, article2, article3});
		assertThat(articleDao.getCount(), is(3));
		
		// deleteAll
		assertThat(articleDao.deleteAll(), is(3));
		assertThat(articleDao.getCount(), is(0));
	}
	
	
	// create article
	@Test
	public void add() {
		initDao();
		
		// add
		articleDao.add(article1);
		assertThat(articleDao.getCount(), is(1));
		
		articleDao.add(article2);
		assertThat(articleDao.getCount(), is(2));
		
		articleDao.add(article3);
		assertThat(articleDao.getCount(), is(3));
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void add_duplicationKeyException() {
		initDao();
		
		articleDao.add(article1);
		
		// add - except
		articleDao.add(article1);
		
		assertThat(articleDao.getCount(), is(2));
	}
	
	// create articles
	@Test
	public void addArticles() {
		initDao();
		
		articles = Arrays.asList(new Article[]{article1, article2, article3});
		
		// add
		int[] actualCounts = articleDao.addArticles(articles);
		
		assertThat(actualCounts.length, is(3));
	}
	
	@Test
	public void addArticles_includedDuplicateKey() {
		initDao();
		
		articles = Arrays.asList(new Article[]{article1, article1, article1});
		
		// add - duplicatedSet
		int updateState[] = articleDao.addArticles(articles);
		
		assertThat(getCount(updateState), is(1));
	}
	
	// read by id
	@Test
	public void addAndFindById() {
		initDao();
		
		// add
		articleDao.add(article1);
		
		// find
		Article actualArticle1 = articleDao.findById(1);
		
		// assert
		assertSameArticle(actualArticle1, article1);
		
		articleDao.add(article2);
		Article actualArticle2 = articleDao.findById(2);
		assertSameArticle(actualArticle2, article2);
		
		articleDao.add(article3);
		Article actualArticle3 = articleDao.findById(3);
		assertSameArticle(actualArticle3, article3);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findById_emptyResultDataAccessException() {
		initDao();
		
		// add
		articleDao.add(article1);
		
		// find - except
		Article actualArticle1 = articleDao.findById(4);
		
		assertSameArticle(actualArticle1, article1);
		
	}
	
	// read by date
	@Test
	public void findBetweenDates() throws ParseException {
		initDao();
		
		// prepare
		article1.setDate("2014-12-07 05:59:59");
		article2.setDate("2014-12-07 06:00:00");
		article3.setDate("2014-12-07 17:59:59");
		article4.setDate("2014-12-07 18:00:00");
		prepareArticleDao(new Article[]{article1, article2, article3, article4});
		
	    String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER , 7, 18);
	    
	    // find
		List<Article> actualArticles = articleDao.findBetweenDates(dates);
		
		assertThat(actualArticles.size(), is(2));
		assertThat(actualArticles.get(0).getId(), is(2));
		assertThat(actualArticles.get(1).getId(), is(3));
	}
	
	// read by ordered score
	@Test
	public void findByScoreOrderFromOneTo() {
		initDao();
		
		// prepare
		article1.setScore(10.1);
		article2.setScore(20.1);
		article3.setScore(30.1);
		prepareArticleDao(new Article[]{article1, article2, article3});
		
		// find
		final int size = 2;
		List<Article> actualArticles = articleDao.findByScoreOrderFromOneTo(size);
		
		// assert
		assertThat(actualArticles.size(), is(size));
		assertThat(actualArticles.get(0).getScore(), is(30.1));
		assertThat(actualArticles.get(1).getScore(), is(20.1));
	}
	

	
	// udpate score
	@Test
	public void updateScores() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1, article2, article3});
		List<Article> updatedArticles = Arrays.asList(new Article[]{new Article(article1.getId(), 11.1), new Article(article2.getId(), 22.2), new Article(article3.getId(), 33.3)});
		
		// update
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
		
		// delete
		articleDao.deleteAllAtHalfDay();
		
		assertThat(articleDao.getCountAtHalfDay(), is(0));
	}
	
	// add article at half day
	@Test
	public void addArticleAtHalfDay() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1, article2, article3});
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// add
		articleDao.addAtHalfDay(new Article(article1.getId(), 1, date));
		assertThat(articleDao.getCountAtHalfDay(), is(1));
		
		articleDao.addAtHalfDay(new Article(article2.getId(), 2, date));
		assertThat(articleDao.getCountAtHalfDay(), is(2));
		
		articleDao.addAtHalfDay(new Article(article3.getId(), 2, date));
		assertThat(articleDao.getCountAtHalfDay(), is(3));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void addAtHalfDay_dataIntegrityViolationException() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1});
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// add
		articleDao.addAtHalfDay(new Article(article1.getId(), 1, date));
		assertThat(articleDao.getCountAtHalfDay(), is(1));
		
		// add - except
		articleDao.addAtHalfDay(new Article(article2.getId(), 2, date));
		assertThat(articleDao.getCountAtHalfDay(), is(2));
	}
	
	// add articles at half day
	@Test
	public void addArticlesAtHalfDay() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1, article2, article3});
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		List<Article> articlesForHalfDay = Arrays.asList(new Article[]{new Article(article1.getId(), 1, date), new Article(article2.getId(), 2, date), new Article(article3.getId(), 3, date)}); 
		
		// add
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(3));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void addArticlesAtHalfDay_dataIntegrityViolationException() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1});
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		List<Article> articlesForHalfDay = Arrays.asList(new Article[]{new Article(article1.getId(), 1, date), new Article(article2.getId(), 2, date), new Article(article3.getId(), 3, date)});
		
		// add - except
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(1));
	}

	
	// read
	@Test
	public void addAndfindByIdAtHalfDay() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1, article2, article3});
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// add
		int id1 = articleDao.addAtHalfDay(new Article(article1.getId(), 1, date));
		int id2 = articleDao.addAtHalfDay(new Article(article2.getId(), 2, date));
		int id3 = articleDao.addAtHalfDay(new Article(article3.getId(), 3, date));
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
	public void addAndfindByIdAtHalfDay_emptyResultDataAccessException() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1});
		String date = ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 6);
		
		// add
		articleDao.addAtHalfDay(new Article(article1.getId(), 1, date));
		assertThat(articleDao.getCountAtHalfDay(), is(1));
		
		// find - except
		Article actual1 = articleDao.findByIdAtHalfDay(0);
		assertSameArticle(actual1, article1, 1);
	}

	@Test
	public void findBetweenDatesAtHalfDay() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1, article2, article3, article4});
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		List<Article> articlesForHalfDay = Arrays.asList(new Article[]{
				new Article(article1.getId(), 1, ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 27, 17)),
				new Article(article2.getId(), 2, dates[0]),
				new Article(article3.getId(), 3, dates[1]),
				new Article(article4.getId(), 4, ElixirUtils.getFormattedDate(2014, Calendar.NOVEMBER, 28, 18))
		});
				
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(4));
		
		// find
		List<Article> actualArticles = articleDao.findBetweenDatesAtHalfDay(dates);
		assertThat(actualArticles.size(), is(2));
		assertSameArticle(actualArticles.get(0), article2);
		assertSameArticle(actualArticles.get(1), article3);
	}
	
	
	@Test
	public void not_findBetweenDatesAtHalfDay() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1, article2, article3, article4});
		
		// service dates
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		String[] fakeDates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 30, 6);
		
		List<Article> articlesForHalfDay = Arrays.asList(new Article[]{
					new Article(article1.getId(), 1, dates[0]),
					new Article(article2.getId(), 2, dates[0]),
					new Article(article3.getId(), 3, dates[1]),
					new Article(article4.getId(), 4, dates[1])
				});
		
		// add
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(4));
		
		// find
		List<Article> actualArticles = articleDao.findBetweenDatesAtHalfDay(fakeDates);
		assertThat(actualArticles.size(), is(0));
	}
	
	
	@Test
	public void findBySequenceBetweenDatesAtHalfDay() {
		initDao();
		
		Article[] articleArr = new Article[]{article1, article2, article3, article4};
		articles = Arrays.asList(articleArr);
		
		// prepare
		prepareArticleDao(articleArr);
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		List<Article> articlesForHalfDay = Arrays.asList(new Article[]{
				new Article(article1.getId(), 1, dates[0]),
				new Article(article2.getId(), 2, dates[0]),
				new Article(article3.getId(), 3, dates[1]),
				new Article(article4.getId(), 4, dates[1])
		});
		
		// add
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
		assertThat(getCount(updateState), is(4));
		
		// find
		for (int i=0; i<4; i++) {
			int sequence = i+1;
			Article actual = articleDao.findBySequenceBetweenDatesAtHalfDay(dates, sequence);
			assertSameArticle(actual, articles.get(i), sequence);
		}		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findBySequenceBetweenDatesAtHalfDay_emptyResultDataAccessException() {
		initDao();
		
		// prepare
		prepareArticleDao(new Article[]{article1, article2, article3, article4});
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
		List<Article> articlesForHalfDay = Arrays.asList(new Article[]{
				new Article(article1.getId(), 1, dates[0]),
				new Article(article2.getId(), 2, dates[0]),
				new Article(article3.getId(), 3, dates[1]),
				new Article(article4.getId(), 4, dates[1])
		});
		
		// add
		int[] updateState = articleDao.addArticlesAtHalfDay(articlesForHalfDay);
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
	
	private void initDao() {
		articleDao.deleteAll();
		assertThat(articleDao.getCount(), is(0));
		
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
		
		articleDao.deleteAllAtHalfDay();
		assertThat(articleDao.getCountAtHalfDay(), is(0));
		
		
		hotissueDao.addHotissues(Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3}));
	}
	
	private void prepareArticleDao(Article[] articles) {
		articleDao.addArticles(Arrays.asList(articles));
	}
	
	private void makeFixtures() {
		section1 = new Section(3);
		section2 = new Section(10);
		section3 = new Section(23);
		
		journal1 = new Journal(84);
		journal2 = new Journal(10);
		journal3 = new Journal(23);
		
		hotissue1 = new Hotissue(1, "hotissue1");
		hotissue2 = new Hotissue(2, "hotissue2");
		hotissue3 = new Hotissue(3, "hotissue3");
		
		article1 = new Article(1, hotissue1, journal1, section1, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000);
		article2 = new Article(2, hotissue2, journal2, section2, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000);
		article3 = new Article(3, hotissue3, journal3, section3, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000);
		article4 = new Article(4, hotissue3, journal3, section3, "title4", "1444-04-04 04:11:11", "content4", 40000, 10000);
	}
}

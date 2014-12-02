package elixir.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HotissueDaoTest {
	
	private static final Logger log = LoggerFactory.getLogger(HotissueDaoTest.class);
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private ArticleDao articleDao;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	private Journal journal;
	private Section section;
	
	private List<Hotissue> hotissues;
	
	@Before
	public void setup() {
		makeFixtures();
	}
	
	// read
	@Test
	public void getCount() {
		initDao();
		
		// add and get
		hotissueDao.add(hotissue1);
		assertThat(hotissueDao.getCount(), is(1));
		
		// add and get
		hotissueDao.add(hotissue2);
		assertThat(hotissueDao.getCount(), is(2));
		
		// add and get
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
	}
	

	// find hotissue
	@Test
	public void findById() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		
		Hotissue actual1 = hotissueDao.findById(hotissue1.getId());
		assertSameStandardHotissue(actual1, hotissue1);
		
		Hotissue actual2 = hotissueDao.findById(hotissue2.getId());
		assertSameStandardHotissue(actual2, hotissue2);
		
		Hotissue actual3 = hotissueDao.findById(hotissue3.getId());
		assertSameStandardHotissue(actual3, hotissue3);
	}
	
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findById_emptyResultDataAccessException() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		
		// find - expect
		Hotissue actual1 = hotissueDao.findById(0);
		assertSameStandardHotissue(actual1, hotissue1);
	}


	@Test
	public void findByScoreOrderFromOneTo() {
		final int limit = 2;
		initDao();

		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		updateHotissueScore(new Hotissue[]{new Hotissue(1, 10.1), new Hotissue(2, 20.1), new Hotissue(3, 30.1)});
		
		// find
		List<Hotissue> actualHotissues = hotissueDao.findByScoreOrderFromOneTo(limit);
		
		assertThat(actualHotissues.size(), is(limit));
		assertThat(actualHotissues.get(0).getScore(), is(30.1));
		assertThat(actualHotissues.get(1).getScore(), is(20.1));
	}




	@Test
	public void getByName() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue actualHotissue1 = hotissueDao.getByName(hotissue1.getName());
		assertSameStandardHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.getByName(hotissue2.getName());
		assertSameStandardHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.getByName(hotissue3.getName());
		assertSameStandardHotissue(actualHotissue3, hotissue3);
		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void notGetByName() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		Hotissue fakeHotissue = new Hotissue("face hotissue");
		Hotissue actualHotissue = hotissueDao.getByName(fakeHotissue.getName());
		assertSameStandardHotissue(actualHotissue, fakeHotissue);
	}
	
	
	
	
	// delete
	@Test
	public void deleteAll() {
		initDao();
		
		hotissues = Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		
		// add
		hotissueDao.addHotissues(hotissues);
		assertThat(hotissueDao.getCount(), is(3));
		
		// delete
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	
	// insert
	@Test
	public void add() {
		initDao();
		
		// add first
		hotissueDao.add(hotissue1);
		assertThat(hotissueDao.getCount(), is(1));
		
		// add second
		hotissueDao.add(hotissue2);
		assertThat(hotissueDao.getCount(), is(2));
		
		// add third
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
	}
	
	
	@Test(expected=DuplicateKeyException.class)
	public void add_duplicatedKeyException() {
		initDao();
		
		// add
		hotissueDao.add(hotissue1);
		assertThat(hotissueDao.getCount(), is(1));
		
		// add - except
		hotissueDao.add(hotissue1);
		assertThat(hotissueDao.getCount(), is(2));
	}
	
	
	@Test
	public void addHotissues() {
		initDao();
		
		hotissues = Arrays.asList(new Hotissue[] {hotissue1, hotissue2, hotissue3});
		
		// add
		hotissueDao.addHotissues(hotissues);
		assertThat(hotissueDao.getCount(), is(3));
	
	}

	@Test
	public void addHotissue_includeDucplicateKey() {
		initDao();
		
		hotissues = Arrays.asList(new Hotissue[] {hotissue1, hotissue1, hotissue1});
		
		// add
		int updateState[] = hotissueDao.addHotissues(hotissues);
		assertThat(getCount(updateState), is(1));
	}
	
	
	// update
	@Test
	public void updateScores() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		
		hotissues = Arrays.asList(new Hotissue[] {new Hotissue(hotissue1.getId(), 11.1), new Hotissue(hotissue2.getId(), 22.2), new Hotissue(hotissue3.getId(), 33.3)});
		int[] state = hotissueDao.updateScores(hotissues);
		
		assertThat(getCount(state), is(3));
		assertThat(hotissueDao.findById(hotissue1.getId()).getScore(), is(hotissues.get(0).getScore()));
		assertThat(hotissueDao.findById(hotissue2.getId()).getScore(), is(hotissues.get(1).getScore()));
		assertThat(hotissueDao.findById(hotissue3.getId()).getScore(), is(hotissues.get(2).getScore()));
		
	}
	
	// update
	@Test
	public void updateScores_() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue1, hotissue1});
		assertThat(hotissueDao.getCount(), is(1));
		
		hotissues = Arrays.asList(new Hotissue[] {new Hotissue(hotissue1.getId(), 11.1), new Hotissue(hotissue2.getId(), 22.2), new Hotissue(hotissue3.getId(), 33.3)});
		int[] state = hotissueDao.updateScores(hotissues);
		
		assertThat(getCount(state), is(1));
		assertThat(hotissueDao.findById(hotissue1.getId()).getScore(), is(hotissues.get(0).getScore()));		
	}
	
	
	
	
	
	
	@Test
	public void getWithArticlesById() {
		initDao();
		hotissueDao.addHotissues(Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3}));
		
		
		Article article1 = new Article(1, hotissue1, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		Article article2 = new Article(2, hotissue1, journal, section, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		Article article3 = new Article(3, hotissue1, journal, section, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 10.1);
		
		articleDao.addArticle(article1);
		articleDao.addArticle(article2);
		articleDao.addArticle(article3);
		assertThat(articleDao.getCount(), is(3));
		
		Hotissue actualHotissue = hotissueDao.getWithArticlesById(hotissue1.getId());
		assertThat(actualHotissue.getArticles().size(), is(3));
	}
	
	
	@Test
	public void getWithArticlesByOrderedScore() {
		final int size = 2;
		
		prepareHotissueDao();
		hotissue1.setScore(10.1);
		hotissue2.setScore(20.1);
		hotissue3.setScore(30.1);
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);

		Article article11 = new Article(11, hotissue1, journal, section, "title11", "1111-01-01 01:11:11", "content11", 11000, 7100, 10.1);
		Article article12 = new Article(12, hotissue1, journal, section, "title12", "1111-01-01 01:11:12", "content12", 12000, 7200, 20.1);
		Article article21 = new Article(21, hotissue2, journal, section, "title21", "1222-02-02 02:11:11", "content21", 21000, 8100, 10.1);
		Article article22 = new Article(22, hotissue2, journal, section, "title22", "1222-02-02 02:11:12", "content22", 22000, 8200, 20.1);
		Article article31 = new Article(31, hotissue3, journal, section, "title31", "1333-03-03 03:11:11", "content31", 31000, 9100, 10.1);
		Article article32 = new Article(32, hotissue3, journal, section, "title32", "1333-03-03 03:11:12", "content32", 32000, 9200, 20.1);
		
		articleDao.addArticle(article11);
		articleDao.addArticle(article12);
		articleDao.addArticle(article21);
		articleDao.addArticle(article22);
		articleDao.addArticle(article31);
		articleDao.addArticle(article32);
		
		assertThat(articleDao.getCount(), is(6));
		assertThat(hotissueDao.getCount(), is(3));
		
		List<Hotissue> actualHotissues = hotissueDao.getWithArticlesByOrderedScore(size);
		
		assertThat(actualHotissues.size(), is(size));
		assertThat(actualHotissues.get(0).getArticles().get(0).getScore(), is(20.1));
		assertThat(actualHotissues.get(1).getArticles().get(0).getScore(), is(20.1));
		
	}
	
	
	
	@Test
	public void delete() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		hotissueDao.add(hotissue2);
		hotissueDao.add(hotissue3);
		assertThat(hotissueDao.getCount(), is(3));
		
		assertThat(hotissueDao.delete(1), is(1));
		assertThat(hotissueDao.getCount(), is(2));
		
		assertThat(hotissueDao.delete(2), is(1));
		assertThat(hotissueDao.getCount(), is(1));
		
		assertThat(hotissueDao.delete(3), is(1));
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void notDelete() {
		prepareHotissueDao();
		
		hotissueDao.add(hotissue1);
		
		Journal journal = new Journal(84);
		Section section = new Section(3);
		Hotissue hotissue = new Hotissue(1);
		
		Article article = new Article(1, hotissue, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		articleDao.addArticle(article);
		
		hotissueDao.delete(1);
	}
	
	
//	@Test
//	public void getBetweenServiceDates() {
//		Date date = RefineryUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
//		String[] dates = RefineryUtils.getServiceFormattedDatesByDate(date);
//		
//		List<Hotissue> actualHotissues = hotissueDao.getBetweenServiceDates(dates[0], dates[1]);
//		
//		for(Hotissue h : actualHotissues) {
//			log.debug("id: " + h.getId());
//		}
//
//	}
	

	private void assertSameStandardHotissue(Hotissue actual, Hotissue expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getName(), is(expected.getName()));
		assertThat(ElixirUtils.parseFormattedDate(actual.getTimestamp()), is(notNullValue()));
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
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	private void prepareHotissueDao(Hotissue[] hotissues) {
		hotissueDao.addHotissues(Arrays.asList(hotissues));
	}
	
	private void updateHotissueScore(Hotissue[] hotissues) {
		// TODO Auto-generated method stub
		
	}
	
	private void makeFixtures() {
		journal = new Journal(84);
		section = new Section(3);
		
		hotissue1 = new Hotissue(1, "hotissue1");
		hotissue2 = new Hotissue(2, "hotissue2");
		hotissue3 = new Hotissue(3, "hotissue3");
	}


}

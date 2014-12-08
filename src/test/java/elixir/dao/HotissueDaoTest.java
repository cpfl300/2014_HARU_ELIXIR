package elixir.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
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
public class HotissueDaoTest {
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private ArticleDao articleDao;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
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
		
		// find
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
	public void findByName() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));
		
		// find
		Hotissue actualHotissue1 = hotissueDao.findByName(hotissue1.getName());
		assertSameStandardHotissue(actualHotissue1, hotissue1);
		
		Hotissue actualHotissue2 = hotissueDao.findByName(hotissue2.getName());
		assertSameStandardHotissue(actualHotissue2, hotissue2);
		
		Hotissue actualHotissue3 = hotissueDao.findByName(hotissue3.getName());
		assertSameStandardHotissue(actualHotissue3, hotissue3);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findByName_emptyResultDataAccessException() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));
		
		// prepare fakehotissue
		Hotissue fakeHotissue = new Hotissue();
		
		// find - except
		Hotissue actualHotissue = hotissueDao.findByName(fakeHotissue.getName());
		assertSameStandardHotissue(actualHotissue, fakeHotissue);
	}
	
	
	@Test
	public void findByIdWithArticle() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));

		article1.setHotissue(hotissue1);
		article2.setHotissue(hotissue1);
		article3.setHotissue(hotissue1);		
		prepareArticleDao(new Article[]{article1, article2, article3});
		assertThat(articleDao.getCount(), is(3));
		
		// find
		Hotissue actualHotissue = hotissueDao.findByIdWithArticles(hotissue1.getId());
		assertThat(actualHotissue.getArticles().size(), is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findByIdWithArticle_emptyResultDataAccessException() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));
		
		// find - except
		Hotissue actualHotissue = hotissueDao.findByIdWithArticles(hotissue1.getId());
		assertThat(actualHotissue.getArticles().size(), is(0));
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

	// delete
	@Test
	public void deleteAll() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));
		
		// delete
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void deleteAll_dataIntegrityViolationException() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));
		
		prepareArticleDao(new Article[]{article1});
		assertThat(articleDao.getCount(), is(1));
		
		// delete - except
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	@Test
	public void deleteById() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));
		
		// delete
		hotissueDao.deleteById(hotissue1.getId());
		assertThat(hotissueDao.getCount(), is(2));
		
		hotissueDao.deleteById(hotissue2.getId());
		assertThat(hotissueDao.getCount(), is(1));
		
		hotissueDao.deleteById(hotissue3.getId());
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void deleteById_dataIntegrityViolationException() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		assertThat(hotissueDao.getCount(), is(3));
		
		prepareArticleDao(new Article[]{article1, article2, article3});
		assertThat(articleDao.getCount(), is(3));
		
		// delete - except
		hotissueDao.deleteById(hotissue1.getId());
		assertThat(hotissueDao.getCount(), is(2));
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
		
		// update
		int[] state = hotissueDao.updateScores(hotissues);
		
		assertThat(getCount(state), is(3));
		assertThat(hotissueDao.findById(hotissue1.getId()).getScore(), is(hotissues.get(0).getScore()));
		assertThat(hotissueDao.findById(hotissue2.getId()).getScore(), is(hotissues.get(1).getScore()));
		assertThat(hotissueDao.findById(hotissue3.getId()).getScore(), is(hotissues.get(2).getScore()));
		
	}
	
	// update
	@Test
	public void updateScores_includeDucplicateKey() {
		initDao();
		prepareHotissueDao(new Hotissue[]{hotissue1, hotissue1, hotissue1});
		assertThat(hotissueDao.getCount(), is(1));
		
		hotissues = Arrays.asList(new Hotissue[] {new Hotissue(hotissue1.getId(), 11.1), new Hotissue(hotissue2.getId(), 22.2), new Hotissue(hotissue3.getId(), 33.3)});
		
		// update - except
		int[] state = hotissueDao.updateScores(hotissues);
		
		assertThat(getCount(state), is(1));
		assertThat(hotissueDao.findById(hotissue1.getId()).getScore(), is(hotissues.get(0).getScore()));		
	}

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
		assertThat(articleDao.getCount(), is(0));
		
		hotissueDao.deleteAll();
		assertThat(hotissueDao.getCount(), is(0));
	}
	
	private void prepareHotissueDao(Hotissue[] hotissues) {
		hotissueDao.addHotissues(Arrays.asList(hotissues));
	}
	
	
	private void prepareArticleDao(Article[] articles) {
		articleDao.addArticles(Arrays.asList(articles));
	}
	
	
	private void updateHotissueScore(Hotissue[] hotissues) {
		hotissueDao.updateScores(Arrays.asList(hotissues));
	}
	
	private void makeFixtures() {
		journal = new Journal(84);
		section = new Section(3);
		
		hotissue1 = new Hotissue(1, "hotissue1");
		hotissue2 = new Hotissue(2, "hotissue2");
		hotissue3 = new Hotissue(3, "hotissue3");
		
		article1 = new Article(1, hotissue1, journal, section, "title1", "1111-01-01 01:11:11", "content1", 10000, 7000, 10.1);
		article2 = new Article(2, hotissue2, journal, section, "title2", "1222-02-02 02:11:11", "content2", 20000, 8000, 20.1);
		article3 = new Article(3, hotissue3, journal, section, "title3", "1333-03-03 03:11:11", "content3", 30000, 9000, 30.1);
	}


}

package elixir.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

import elixir.dao.HotissueDao;
import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.model.Journal;
import elixir.model.Section;
import elixir.utility.ElixirUtils;


@RunWith(MockitoJUnitRunner.class)
public class HotissueServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(HotissueServiceTest.class);
	
	@InjectMocks
	private HotissueService hotissueService;
	
	@Mock
	private HotissueDao hotissueDaoMock;
	
	@Mock
	private ArticleService articleServiceMock;
	
	private Hotissue hotissue1;
	private Hotissue hotissue2;
	private Hotissue hotissue3;
	
	private Journal journal1;
	private Journal journal2;
	private Journal journal3;
	
	private Section section1;
	private Section section2;
	private Section section3;
	
	private List<Hotissue> hotissues;
	
	@Before
	public void setup() {
		makeFixtures();
	}

	@Test
	public void calcScore() {
		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER , 7, 6);
		List<Article> articles = makeArticleFixtures();
		List<Hotissue> hotissues = Hotissue.orderByHotissue(articles);
		
		when(articleServiceMock.getArticlesBetweenDates(dates[0], dates[1])).thenReturn(articles);
		when(hotissueDaoMock.updateScores(hotissues)).thenReturn(new int[]{1,1,1});
		int actualCount = hotissueService.calcScore(dates[0], dates[1]); 
		
		assertThat(actualCount, is(3));	
	}
	
	@Test
	public void getById() {
		
		when(hotissueDaoMock.findById(hotissue1.getId())).thenReturn(hotissue1);
		Hotissue actualHotissue = hotissueService.getById(hotissue1.getId());
		
		assertThat(actualHotissue.getId(), is(hotissue1.getId()));

	}
	
	@Test
	public void getWithArticlesByOrderedScore() {
		final int size = 2;
		
		hotissue1.setScore(10.1);
		hotissue1.addArticle(new Article(1, 0.9));
		
		hotissue2.setScore(20.1);
		hotissue2.addArticle(new Article(2, 0.8));
		
		hotissue3.setScore(30.1);
		hotissue3.addArticle(new Article(3, 0.7));
		
		when(hotissueDaoMock.getWithArticlesByOrderedScore(size)).thenReturn(Arrays.asList(new Hotissue[]{hotissue3, hotissue2}));
		
		List<Hotissue> actualHotissues = hotissueService.getWithArticlesByOrderedScore(size);
		assertThat(actualHotissues.size(), is(size));
		assertThat(actualHotissues.get(0).getScore(), is(30.1));
		assertThat(actualHotissues.get(0).getArticles().get(0).getId(), is(3));
		
		assertThat(actualHotissues.get(1).getScore(), is(20.1));
		assertThat(actualHotissues.get(1).getArticles().get(0).getId(), is(2));
		
	}



	private List<Article> makeArticleFixtures() {
		List<Article> articles = new ArrayList<Article>();
		
		articles.add(new Article(11, hotissue1, journal1, section1, "title1-1", "1111-01-01 01:11:11", "content1-1", 11000, 7100, 1.1));
		articles.add(new Article(12, hotissue1, journal1, section1, "title1-2", "1111-01-01 01:11:12", "content1-2", 12000, 7200, 1.2));
		articles.add(new Article(13, hotissue1, journal1, section1, "title1-3", "1111-01-01 01:11:13", "content1-3", 13000, 7300, 1.3));
		
		articles.add(new Article(21, hotissue2, journal2, section2, "title2-1", "1222-02-02 02:11:11", "content2-1", 21000, 8100, 2.1));
		articles.add(new Article(22, hotissue2, journal2, section2, "title2-2", "1222-02-02 02:11:12", "content2-2", 22000, 8200, 2.2));
		articles.add(new Article(23, hotissue2, journal2, section2, "title2-3", "1222-02-02 02:11:13", "content2-3", 23000, 8300, 2.3));
		
		articles.add(new Article(31, hotissue3, journal3, section3, "title3-1", "1333-03-03 03:11:11", "content3-1", 31000, 9100, 3.1));
		articles.add(new Article(32, hotissue3, journal3, section3, "title3-2", "1333-03-03 03:11:12", "content3-2", 32000, 9200, 3.2));
		articles.add(new Article(33, hotissue3, journal3, section3, "title3-3", "1333-03-03 03:11:13", "content3-3", 33000, 9300, 3.3));
		
		return articles;
	}
	

	
	
	////////////
	
	@Test
	public void add() {
		// prepare
		when(hotissueDaoMock.findById(hotissue1.hashCode())).thenReturn(hotissue1);
		when(hotissueDaoMock.findById(hotissue2.hashCode())).thenReturn(hotissue2);
		when(hotissueDaoMock.findById(hotissue3.hashCode())).thenReturn(hotissue3);
		
		// add
		int actualId1 = hotissueService.add(hotissue1); 
		assertThat(actualId1, is(hotissue1.getId()));
		
		int actualId2 = hotissueService.add(hotissue2); 
		assertThat(actualId2, is(hotissue2.getId()));
		
		int actualId3 = hotissueService.add(hotissue3); 
		assertThat(actualId3, is(hotissue3.getId()));
	}
	
	@Test
	public void add_alreadyExistHotissue() {
		// prepare
		when(hotissueDaoMock.findById(hotissue1.hashCode())).thenReturn(hotissue1);
		
		// add
		int actualId1 = hotissueService.add(hotissue1); 
		assertThat(actualId1, is(hotissue1.getId()));
		
		int actualId2 = hotissueService.add(hotissue1); 
		assertThat(actualId2, is(hotissue1.getId()));
		
		int actualId3 = hotissueService.add(hotissue1); 
		assertThat(actualId3, is(hotissue1.getId()));
	}
	
	@Test
	public void addHotissues() {
		// prepare
		hotissues = Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		int[] expectedResult = new int[]{1,1,1};
		when(hotissueDaoMock.addHotissues(hotissues)).thenReturn(expectedResult);
		
		// add
		Set<Integer> actualSet = hotissueService.addHotissues(hotissues);
		
		// assert
		assertThat(actualSet.size(), is(getCount(expectedResult)));
		for (Hotissue e : hotissues) {
			assertThat(actualSet, hasItem(e.getId()));
		}
	}
	
	@Test
	public void addHotissues_includedDuplicateKey() {
		// prepare
		hotissues = Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3});
		int[] expectedResult = new int[]{1,0,1};
		when(hotissueDaoMock.addHotissues(hotissues)).thenReturn(expectedResult);
		
		// add
		Set<Integer> actualSet = hotissueService.addHotissues(hotissues);
		
		// assert
		assertThat(actualSet.size(), is(getCount(expectedResult)));
		for (int i=0; i<expectedResult.length; i++) {
			if (expectedResult[i] == 0) continue;
			assertThat(actualSet, hasItem(hotissues.get(i).getId()));
		}
	}
	

//	@Test
//	public void getHalfDay() {
//		// prepare
//		hotissues = Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3});
//		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.NOVEMBER, 28, 6);
//		
//		// mock
//		when(hotissueDaoMock.findBetweenDatesAtHalfDay(dates)).thenReturn(hotissues);
//		
//		List<Hotissue> actualHotissues = hotissueService.getHalfDay();
//		
//	}
//	
	
	
	
	@Test
	public void getByServiceDate() {
		Date date = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
		String[] dates = ElixirUtils.getServiceFormattedDatesByDate(date);
		
		hotissues = new ArrayList<Hotissue>();
		hotissues.add(hotissue1);
		hotissues.add(hotissue2);
		hotissues.add(hotissue3);
		
		when(hotissueDaoMock.getBetweenServiceDates(dates[0], dates[1])).thenReturn(hotissues);
		
		List<Hotissue> actualHotissues = hotissueService.getByServiceDate(date);
		assertThat(actualHotissues.size(), is(hotissues.size()));		
	}
	
	
	private void makeFixtures() {
		section1 = new Section(3);
		section2 = new Section(10);
		section3 = new Section(23);
		
		journal1 = new Journal(84);
		journal2 = new Journal(10);
		journal3 = new Journal(23);		

		// fixtures
		hotissue1 = new Hotissue("hotissue1");
		hotissue2 = new Hotissue("hotissue2");
		hotissue3 = new Hotissue("hotissue3");
		
	}
	
	private int getCount(int[] rows) {
		int count = 0;
		
		for (int row : rows) {
			count += row;
		}
		
		return count;
	}

}

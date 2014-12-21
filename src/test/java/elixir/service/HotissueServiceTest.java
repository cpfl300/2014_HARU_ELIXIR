package elixir.service;

import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elixir.dao.HotissueDao;
import elixir.model.Hotissue;
import elixir.model.HotissuesTest;
import elixir.model.Section;
import elixir.model.SectionsTest;
import elixir.utility.ElixirUtils;
import elixir.utility.ElixirUtilsTest;


@RunWith(MockitoJUnitRunner.class)
public class HotissueServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(HotissueServiceTest.class);
	
	@InjectMocks
	private HotissueService hotissueService;
	
	@Mock
	private HotissueDao hotissueDaoMock;
	
	private List<Hotissue> hotissues;
	private List<Section> sections;
	private List<Date> dates;
	
	@Before
	public void setup() {
		dates = ElixirUtilsTest.preparedList();
		sections = SectionsTest.preparedList();
		hotissues = HotissuesTest.preparedList(dates, sections);
	}
	
	@Test
	public void addAll() {
		hotissueService.addAll(hotissues);
		
		verify(hotissueDaoMock, times(1)).addAll(hotissues);
	}

//	@Test
//	public void calcScore() {
//		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER , 7, 6);
//		List<Article> articles = makeArticleFixtures();
//		List<Hotissue> hotissues = Hotissue.orderByHotissue(articles);
//		
//		when(articleServiceMock.getArticlesBetweenDates(dates[0], dates[1])).thenReturn(articles);
//		when(hotissueDaoMock.updateScores(hotissues)).thenReturn(new int[]{1,1,1});
//		int actualCount = hotissueService.calcScore(dates[0], dates[1]); 
//		
//		assertThat(actualCount, is(3));	
//	}
//	
//	@Test
//	public void getById() {
//		
//		when(hotissueDaoMock.findById(hotissue1.getId())).thenReturn(hotissue1);
//		Hotissue actualHotissue = hotissueService.getById(hotissue1.getId());
//		
//		assertThat(actualHotissue.getId(), is(hotissue1.getId()));
//
//	}
	
//	@Test
//	public void getWithArticlesByOrderedScore() {
//		final int size = 2;
//		
//		hotissue1.setScore(10.1);
//		hotissue1.addArticle(new Article(1, 0.9));
//		
//		hotissue2.setScore(20.1);
//		hotissue2.addArticle(new Article(2, 0.8));
//		
//		hotissue3.setScore(30.1);
//		hotissue3.addArticle(new Article(3, 0.7));
//		
//		when(hotissueDaoMock.getWithArticlesByOrderedScore(size)).thenReturn(Arrays.asList(new Hotissue[]{hotissue3, hotissue2}));
//		
//		List<Hotissue> actualHotissues = hotissueService.getWithArticlesByOrderedScore(size);
//		assertThat(actualHotissues.size(), is(size));
//		assertThat(actualHotissues.get(0).getScore(), is(30.1));
//		assertThat(actualHotissues.get(0).getArticles().get(0).getId(), is(3));
//		
//		assertThat(actualHotissues.get(1).getScore(), is(20.1));
//		assertThat(actualHotissues.get(1).getArticles().get(0).getId(), is(2));
//		
//	}



		
//	@Test
//	public void add() {
//		// prepare
//		when(hotissueDaoMock.findById(hotissue1.hashCode())).thenReturn(hotissue1);
//		when(hotissueDaoMock.findById(hotissue2.hashCode())).thenReturn(hotissue2);
//		when(hotissueDaoMock.findById(hotissue3.hashCode())).thenReturn(hotissue3);
//		
//		// add
//		int actualId1 = hotissueService.add(hotissue1); 
//		assertThat(actualId1, is(hotissue1.getId()));
//		
//		int actualId2 = hotissueService.add(hotissue2); 
//		assertThat(actualId2, is(hotissue2.getId()));
//		
//		int actualId3 = hotissueService.add(hotissue3); 
//		assertThat(actualId3, is(hotissue3.getId()));
//	}
//	
//	@Test
//	public void add_alreadyExistHotissue() {
//		// prepare
//		when(hotissueDaoMock.findById(hotissue1.hashCode())).thenReturn(hotissue1);
//		
//		// add
//		int actualId1 = hotissueService.add(hotissue1); 
//		assertThat(actualId1, is(hotissue1.getId()));
//		
//		int actualId2 = hotissueService.add(hotissue1); 
//		assertThat(actualId2, is(hotissue1.getId()));
//		
//		int actualId3 = hotissueService.add(hotissue1); 
//		assertThat(actualId3, is(hotissue1.getId()));
//	}
//	
//	@Test
//	public void addHotissues() {
//		// prepare
//		hotissues = Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3});
//		int[] expectedResult = new int[]{1,1,1};
//		when(hotissueDaoMock.addHotissues(hotissues)).thenReturn(expectedResult);
//		
//		// add
//		Set<Integer> actualSet = hotissueService.addHotissues(hotissues);
//		
//		// assert
//		assertThat(actualSet.size(), is(getCount(expectedResult)));
//		for (Hotissue e : hotissues) {
//			assertThat(actualSet, hasItem(e.getId()));
//		}
//	}
//	
//	@Test
//	public void addHotissues_includedDuplicateKey() {
//		// prepare
//		hotissues = Arrays.asList(new Hotissue[]{hotissue1, hotissue2, hotissue3});
//		int[] expectedResult = new int[]{1,0,1};
//		when(hotissueDaoMock.addHotissues(hotissues)).thenReturn(expectedResult);
//		
//		// add
//		Set<Integer> actualSet = hotissueService.addHotissues(hotissues);
//		
//		// assert
//		assertThat(actualSet.size(), is(getCount(expectedResult)));
//		for (int i=0; i<expectedResult.length; i++) {
//			if (expectedResult[i] == 0) continue;
//			assertThat(actualSet, hasItem(hotissues.get(i).getId()));
//		}
//	}
	

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
	
	
	
//	@Test
//	public void getByServiceDate() {
//		Date date = ElixirUtils.getDate(2014, Calendar.NOVEMBER, 28, 6);
//		String[] dates = ElixirUtils.getServiceFormattedDatesByDate(date);
//		
//		hotissues = new ArrayList<Hotissue>();
//		hotissues.add(hotissue1);
//		hotissues.add(hotissue2);
//		hotissues.add(hotissue3);
//		
//		when(hotissueDaoMock.getBetweenServiceDates(dates[0], dates[1])).thenReturn(hotissues);
//		
//		List<Hotissue> actualHotissues = hotissueService.getByServiceDate(date);
//		assertThat(actualHotissues.size(), is(hotissues.size()));		
//	}

}

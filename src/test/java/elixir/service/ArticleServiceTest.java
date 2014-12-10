package elixir.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import elixir.dao.ArticleDao;
import elixir.dao.JournalDao;
import elixir.dao.SectionDao;
import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.model.Journal;
import elixir.model.Section;
import elixir.utility.ElixirUtils;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceTest.class);
	
	
	@InjectMocks
	private ArticleService articleService;
	
	@Mock
	private ArticleDao articleDaoMock;
	
	@Mock
	private JournalDao journalDaoMock;
	
	@Mock
	private SectionDao sectionDaoMock;
	
	@Mock
	private HotissueService hotissueServiceMock;
	
	private Article article1;
	private Article article2;
	private Article article3;
	
	private List<Article> articles;
	private List<Hotissue> hotissues;
	private List<Section> sections;
	private List<Journal> journals;

	@Before
	public void setup() {
		makeJournalDaoMocks();
		makeSectionDaoMocks();
		makeHotissueServiceMocks();
		
		makeArticleDaoMocks();
	}

//	@Test
//	public void has() {
//		when(articleDaoMock.findById(article1.hashCode())).thenReturn(article1);
//		assertThat(articleService.has(article1.hashCode()), is(true));
//		
//		when(articleDaoMock.findById(article2.hashCode())).thenReturn(article2);
//		assertThat(articleService.has(article2.hashCode()), is(true));
//		
//		when(articleDaoMock.findById(article3.hashCode())).thenReturn(article3);
//		assertThat(articleService.has(article3.hashCode()), is(true));
//	}
//	
//	@Test
//	public void has_not() {
//		when(articleDaoMock.findById(article1.hashCode())).thenThrow(EmptyResultDataAccessException.class);
//		assertThat(articleService.has(article1.hashCode()), is(false));
//		
//		when(articleDaoMock.findById(article2.hashCode())).thenThrow(EmptyResultDataAccessException.class);
//		assertThat(articleService.has(article2.hashCode()), is(false));
//		
//		when(articleDaoMock.findById(article3.hashCode())).thenThrow(EmptyResultDataAccessException.class);
//		assertThat(articleService.has(article3.hashCode()), is(false));
//	}
	
	
//	@Test
//	public void add() {
//		int expectedArticle1Id = article1.hashCode();
//		assertThat(articleService.add(article1), is(expectedArticle1Id));
//		
//		int expectedArticle2Id = article2.hashCode();
//		assertThat(articleService.add(article2), is(expectedArticle2Id));
//		
//		int expectedArticle3Id = article3.hashCode();
//		assertThat(articleService.add(article3), is(expectedArticle3Id));
//	}
//	
//	@Test
//	public void add_not() {
//		doThrow(DuplicateKeyException.class).when(articleDaoMock).add(any(Article.class));
//		
//		int expectedArticle1Id = article1.hashCode();
//		assertThat(articleService.add(article1), is(expectedArticle1Id));
//		
//		int expectedArticle2Id = article2.hashCode();
//		assertThat(articleService.add(article2), is(expectedArticle2Id));
//		
//		int expectedArticle3Id = article3.hashCode();
//		assertThat(articleService.add(article3), is(expectedArticle3Id));
//	}
	
	
//	@Test
//	public void delete() {
//		makeHotissueServiceMocks();
//		article1.setHotissue(hotissue1);
//		article2.setHotissue(hotissue2);
//		article3.setHotissue(hotissue3);
//		
//		when(articleDaoMock.findById(article1.hashCode())).thenReturn(article1);		
//		when(articleDaoMock.findById(article2.hashCode())).thenReturn(article2);
//		when(articleDaoMock.findById(article3.hashCode())).thenReturn(article3);
//		
//		when(articleDaoMock.deleteById(article1.hashCode())).thenReturn(1);
//		when(articleDaoMock.deleteById(article2.hashCode())).thenReturn(1);
//		when(articleDaoMock.deleteById(article3.hashCode())).thenReturn(1);
//
//		when(hotissueServiceMock.delete(hotissue1.getId())).thenReturn(1);
//		when(hotissueServiceMock.delete(hotissue2.getId())).thenReturn(1);
//		when(hotissueServiceMock.delete(hotissue3.getId())).thenReturn(1);
//		
//		assertThat(articleService.delete(article1.hashCode()), is(1));
//		assertThat(articleService.delete(article1.hashCode()), is(1));
//		assertThat(articleService.delete(article1.hashCode()), is(1));
//	}
	
//	@Test
//	public void addArticles() {
//		articles = new ArrayList<Article>();
//		articles.add(article1);
//		articles.add(article2);
//		articles.add(article3);
//		
//		when(articleDaoMock.addArticles(articles)).thenReturn(new int[] {1, 1, 1});
//		
//		int actualCount = articleService.addArticles(articles);
//		
//		assertThat(actualCount, is(3));
//	}
//	
//	@Test
//	public void addArticlesIncludedDuplicateKey() {
//		articles = new ArrayList<Article>();
//		articles.add(article1);
//		articles.add(article1);
//		articles.add(article1);
//		
//		when(articleDaoMock.addArticles(articles)).thenReturn(new int[] {1, 0, 0});
//		
//		int actualCount = articleService.addArticles(articles);
//		
//		assertThat(actualCount, is(1));
//	}
	
//	@Test
//	public void calcScore() {
//		String[] dates = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 6);
//		
//		articles = new ArrayList<Article>();
//		articles.add(article1);
//		articles.add(article2);
//		articles.add(article3);
//		
//		when(articleDaoMock.findBetweenDates(dates)).thenReturn(articles);
//		when(articleDaoMock.updateScores(articles)).thenReturn(new int[] {1, 1, 1});
//		assertThat(articleService.calcScore(dates[0], dates[1]), is(3));
//	}
	
//	@Test
//	public void getArticlesBetweenDates() {
//		String[] mornings = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 6);
//		String[] afternoons = ElixirUtils.getServiceDatesByTime(2014, Calendar.DECEMBER, 7, 18);
//		
//		// fixtures
//		List<Article> articles = new ArrayList<Article>();
//		articles.add(new Article(1, hotissue1, journal1, section1, "title1", "2014-12-06 17:59:59", "content1", 10000, 11000));
//		articles.add(new Article(2, hotissue2, journal2, section2, "title2", "2014-12-06 18:00:00", "content2", 20000, 12000));
//		articles.add(new Article(3, hotissue3, journal3, section3, "title3", "2014-12-07 05:59:59", "content3", 30000, 13000));
//		articles.add(new Article(4, hotissue1, journal1, section1, "title4", "2014-12-07 06:00:00", "content4", 40000, 14000));
//		articles.add(new Article(5, hotissue2, journal2, section2, "title5", "2014-12-07 17:59:59", "content5", 50000, 15000));
//		articles.add(new Article(6, hotissue3, journal3, section3, "title6", "2014-12-07 18:00:00", "content6", 60000, 16000));
//		
//		List<Article> morningArticles = Arrays.asList(new Article[] {articles.get(1), articles.get(2)});		
//		List<Article> afternoonArticles = Arrays.asList(new Article[] {articles.get(3), articles.get(4)});
//
//
//		when(articleDaoMock.findBetweenDates(mornings)).thenReturn(morningArticles);		
//		when(articleDaoMock.findBetweenDates(afternoons)).thenReturn(afternoonArticles);
//		
//		
//		List<Article> actualMorningArticles = articleService.getArticlesBetweenDates(mornings[0], mornings[1]);
//		assertThat(actualMorningArticles.size(), is(2));
//		assertThat(actualMorningArticles.get(0).getId(), is(2));
//		assertThat(actualMorningArticles.get(1).getId(), is(3));
//		
//		List<Article> actualAfternoonArticles = articleService.getArticlesBetweenDates(afternoons[0], afternoons[1]);
//		assertThat(actualAfternoonArticles.size(), is(2));
//		assertThat(actualAfternoonArticles.get(0).getId(), is(4));
//		assertThat(actualAfternoonArticles.get(1).getId(), is(5));
//	}


//	@Test
//	public void getByOrderedScore() {
//		final int size = 2;
//		
//		article1.setScore(10.1);
//		article2.setScore(20.1);
//		article3.setScore(30.1);
//		
//		when(articleDaoMock.findByScoreOrderFromOneTo(size)).thenReturn(Arrays.asList(new Article[]{article3, article2}));
//		
//		List<Article> actualArticles = articleService.getByOrderedScore(size);
//		assertThat(actualArticles.size(), is(size));
//		assertThat(actualArticles.get(0).getScore(), is(30.1));
//		assertThat(actualArticles.get(1).getScore(), is(20.1));
//	}
	
	// read
	@Test
	public void getHalfDay() {
		
		// prepare
		Date serviceDate = ElixirUtils.nextServiceDate(ElixirUtils.getNow());
		String[] serviceRange = ElixirUtils.getServiceFormattedDatesByDate(serviceDate);
		articles = Arrays.asList(new Article[]{
					new Article(1, new Hotissue(1), new Journal(1), new Section(1), "title1", serviceRange[0], "content1", 10000, 7100, 10.0),
					new Article(2, new Hotissue(2), new Journal(2), new Section(2), "title2", serviceRange[0], "content2", 20000, 7200, 20.0),
					new Article(3, new Hotissue(3), new Journal(3), new Section(3), "title3", serviceRange[0], "content3", 30000, 7300, 30.0),
					new Article(4, new Hotissue(4), new Journal(4), new Section(4), "title4", serviceRange[0], "content4", 40000, 7400, 40.0),
				});
		updateSequence(articles);
			
		// mock
		when(articleDaoMock.findBetweenDatesAtHalfDay(serviceRange)).thenReturn(articles);
		
		// exec
		List<Article> actualArticles = articleService.getHalfDay();
		
		// assert
		assertThat(actualArticles.size(), is(articles.size()));
		
		for (int i=0; i<actualArticles.size(); i++) {
			Article actual = actualArticles.get(i);
			assertThat(actual.getJournal().getName(), is(journals.get(i).getName()));
			assertThat(actual.getSection().getMinor(), is(sections.get(i).getMinor()));
			assertThat(actual.getSequence(), is(i+1));
		}
	}

	@Test
	public void getHalfDayBySequence() {
		final int sequence = 1;
		
		// prepare
		Date serviceDate = ElixirUtils.nextServiceDate(ElixirUtils.getNow());
		String[] serviceRange = ElixirUtils.getServiceFormattedDatesByDate(serviceDate);
		Article article = new Article(1, new Hotissue(1), new Journal(1), new Section(1), "title1", serviceRange[0], "content1", 10000, 7100, 10.0);
		article.setSequence(sequence);
				
				
		// mock
		when(articleDaoMock.findBySequenceBetweenDatesAtHalfDay(serviceRange, sequence)).thenReturn(article);
		
		// exec
		Article actual = articleService.getHalfDayBySequence(sequence);
		
		// assert
		assertThat(actual.getJournal().getName(), is(journals.get(sequence-1).getName()));
		assertThat(actual.getSection().getMinor(), is(sections.get(sequence-1).getMinor()));
		assertThat(actual.getSequence(), is(sequence));
	}
	
	// update
	
	// delete
	
	
	

	private void makeHotissueServiceMocks() {
		Hotissue hotissue1 = new Hotissue("hotissue1", "1001-01-01 01:11:11");
		hotissue1.setId(hotissue1.hashCode());
		
		Hotissue hotissue2 = new Hotissue("hotissue2", "1002-02-02 02:11:11");
		hotissue2.setId(hotissue2.hashCode());
		
		Hotissue hotissue3 = new Hotissue("hotissue3", "1003-03-03 03:11:11");
		hotissue3.setId(hotissue3.hashCode());
		
		Hotissue hotissue4 = new Hotissue("hotissue4", "1004-04-04 04:11:11");
		hotissue4.setId(hotissue4.hashCode());
		
		hotissues = Arrays.asList(new Hotissue[]{ hotissue1, hotissue2, hotissue3, hotissue4 });
		
		
		when(hotissueServiceMock.getById(hotissue1.getId())).thenReturn(hotissue1);
		when(hotissueServiceMock.getById(hotissue2.getId())).thenReturn(hotissue2);
		when(hotissueServiceMock.getById(hotissue3.getId())).thenReturn(hotissue3);
		when(hotissueServiceMock.getById(hotissue4.getId())).thenReturn(hotissue4);
		
		when(hotissueServiceMock.add(hotissue1)).thenReturn(hotissue1.getId());
		when(hotissueServiceMock.add(hotissue2)).thenReturn(hotissue2.getId());
		when(hotissueServiceMock.add(hotissue3)).thenReturn(hotissue3.getId());
		when(hotissueServiceMock.add(hotissue4)).thenReturn(hotissue4.getId());
	}

	private void makeSectionDaoMocks() {
		Section section1 = new Section(1, "section_major1","section_minor1"); 
		Section section2 = new Section(2, "section_major2","section_minor2");
		Section section3 = new Section(3, "section_major3","section_minor3");
		Section section4 = new Section(4, "section_major4","section_minor4");
		
		sections = Arrays.asList(new Section[]{ section1, section2, section3, section4 });
		
		when(sectionDaoMock.get(section1.getId())).thenReturn(section1);
		when(sectionDaoMock.get(section2.getId())).thenReturn(section2);
		when(sectionDaoMock.get(section3.getId())).thenReturn(section3);
		when(sectionDaoMock.get(section4.getId())).thenReturn(section4);
		
		when(sectionDaoMock.getByMinor(section1.getMinor())).thenReturn(section1);
		when(sectionDaoMock.getByMinor(section2.getMinor())).thenReturn(section2);
		when(sectionDaoMock.getByMinor(section3.getMinor())).thenReturn(section3);
		when(sectionDaoMock.getByMinor(section4.getMinor())).thenReturn(section4);
	}

	private void makeJournalDaoMocks() {
		Journal journal1 = new Journal(1, "journal1", "journal_section1");
		Journal journal2 = new Journal(2, "journal2", "journal_section2");
		Journal journal3 = new Journal(3, "journal3", "journal_section3");
		Journal journal4 = new Journal(4, "journal4", "journal_section4");
		
		journals = Arrays.asList(new Journal[] { journal1, journal2, journal3, journal4 });
		
		when(journalDaoMock.get(journal1.getId())).thenReturn(journal1);
		when(journalDaoMock.get(journal2.getId())).thenReturn(journal2);
		when(journalDaoMock.get(journal3.getId())).thenReturn(journal3);
		when(journalDaoMock.get(journal4.getId())).thenReturn(journal4);
		
		when(journalDaoMock.getByName(journal1.getName())).thenReturn(journal1);
		when(journalDaoMock.getByName(journal2.getName())).thenReturn(journal2);
		when(journalDaoMock.getByName(journal3.getName())).thenReturn(journal3);
		when(journalDaoMock.getByName(journal4.getName())).thenReturn(journal4);
	}
	
	private void makeArticleDaoMocks() {
		
		articles = new ArrayList<Article>();
		
		for (int i=0; i<4; i++) {
			Hotissue hotissue = hotissues.get(i);
			Journal journal = journals.get(i);
			Section section = sections.get(i);
			String title = "title" + i;
			String date = "1111-01-01 01:11:1" + i;
			String content = "content" + i;
			int hits = 10000 * (i+1);
			int completedReadingCount = 7000 + 100 * (i+1);
			double score = 10.0 + (i+1);
			
			articles.add(new Article(hotissue, journal, section, title, date, content, hits, completedReadingCount, score)); 
		}
	}
	
	
	private void updateSequence(List<Article> articles) {
		
		for (int i=0; i<articles.size(); i++) {
			articles.get(i).setSequence(i+1);
		}
	}

}

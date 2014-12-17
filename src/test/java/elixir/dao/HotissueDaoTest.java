package elixir.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import elixir.config.ElixirConfig;
import elixir.model.Hotissue;
import elixir.model.HotissueTest;
import elixir.model.Section;
import elixir.model.SectionTest;
import elixir.test.ElixirTestUtils;
import elixir.utility.ElixirUtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElixirConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class HotissueDaoTest {
	
	@Autowired
	private SectionDao sectionDao;
	
	@Autowired
	private HotissueDao hotissueDao;
	
	private List<Hotissue> hotissues;
	private List<Section> sections;
	private List<Date> dates;
	
	private int lastId;
	
	@Before
	public void setup() {
		lastId = hotissueDao.getLastId();
		
		dates = ElixirUtilsTest.preparedList();
		sections = SectionTest.preparedList();
		hotissues = HotissueTest.preparedList(dates, sections);
	}										
	
	@AfterTransaction
	public void initAutoIncrement() {
		 hotissueDao.initAutoIncrement(lastId+1);
	}
	
	// size
	@Test
	public void size() {
		// size
		int actual = hotissueDao.getCount();
		
		// assert
		assertThat(actual, is(0));
	}
	
	// addAll
	@Test
	public void addAll() {
		// addAll
		int[] actuals = hotissueDao.addAll(hotissues);
		
		// assert
		assertThat(ElixirTestUtils.getCount(actuals), is(hotissueDao.getCount()));
	}
	
	@Test(expected=DataIntegrityViolationException.class)
	public void addAll_dataIntegrityViolation() {
		Hotissue hotissue = HotissueTest.create(0, "999", "hotissue", SectionTest.create(1000, null, null, 0), 0, "imageUrl", null);
		
		// addAll - except
		hotissueDao.addAll(Arrays.asList(new Hotissue[] {hotissue}));
		
	}
	
	// find by hotissueId
	@Test
	public void findByHotissueId() {
		// prepare
		sections = SectionTest.preparedList(new String[]{"id"});
		hotissues = HotissueTest.preparedList(dates, sections, new String[]{"id", "hotissueId", "title", "imageUrl", "section"});		
		hotissueDao.addAll(hotissues);
		
		// find by hotissueId
		List<Hotissue> actuals = new ArrayList<Hotissue>();
		for (Hotissue h : hotissues) {
			Hotissue actual = hotissueDao.findByHotissueId(h.getHotissueId());
			actuals.add(actual);
		}
		
		// assert
		HotissueTest.ASSERTS(actuals, hotissues);
	}	
}

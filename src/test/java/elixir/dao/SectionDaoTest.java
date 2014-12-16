package elixir.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import elixir.config.ElixirConfig;
import elixir.model.Section;
import elixir.model.SectionTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElixirConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class SectionDaoTest {
	
	@Autowired
	private SectionDao sectionDao;
	private List<Section> sections;
	
	
	@Before
	public void setup() {
		sections = SectionTest.preparedList();
	}
	
	// add
	@Test
	public void add() {
		Section actual = null;
		Section expected = SectionTest.create(0, "999", "test", 1);
				
		int actualId = sectionDao.add(expected);
		expected.setId(actualId);
		
		// find by id		
		actual = sectionDao.findById(actualId);
		SectionTest.ASSERT(actual, expected);
		
		// find by sectionId
		actual = sectionDao.findBySectionId(expected.getSectionId());
		SectionTest.ASSERT(actual, expected);
		
		// find by sectionId
		actual = sectionDao.findBySectionName(expected.getSectionName());
		SectionTest.ASSERT(actual, expected);

	}
	
	
	// find by sectionId
	@Test
	public void findById() {
		List<Section> actuals = new ArrayList<Section>();
		
		// exec
		for (Section section : sections) {
			Section actual = sectionDao.findById(section.getId());
			actuals.add(actual);
		}
		
		// assert
		SectionTest.ASSERTS(actuals, sections);
	}

	@Test(expected=EmptyResultDataAccessException.class)
	public void findById_emptyResultDataAccess() {
		List<Section> actuals = new ArrayList<Section>();
		
		// exec - except
		for (Section section : sections) {
			Section actual = sectionDao.findById(section.getId() * 1000);
			actuals.add(actual);
		}
	}
	
	// find by sectionId
	@Test
	public void findBySectionId() {
		List<Section> actuals = new ArrayList<Section>();
		
		// exec
		for (Section section : sections) {
			Section actual = sectionDao.findBySectionId(section.getSectionId());
			actuals.add(actual);
		}
		
		// assert
		SectionTest.ASSERTS(actuals, sections);
	}

	@Test(expected=EmptyResultDataAccessException.class)
	public void findBySectionId_emptyResultDataAccess() {
		List<Section> actuals = new ArrayList<Section>();
		
		// exec - except
		for (Section section : sections) {
			Section actual = sectionDao.findBySectionId(section.getSectionId() + "1");
			actuals.add(actual);
		}
	}
	
	
	// find by sectionName
	@Test
	public void findBySectionName() {
		List<Section> actuals = new ArrayList<Section>();
		
		// exec
		for (Section section : sections) {
			Section actual = sectionDao.findBySectionName(section.getSectionName());
			actuals.add(actual);
		}
		
		// assert
		SectionTest.ASSERTS(actuals, sections);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findBySectionName_emptyResultDataAccess() {
		List<Section> actuals = new ArrayList<Section>();
		
		// exec - except
		for (Section section : sections) {
			Section actual = sectionDao.findBySectionName(section.getSectionName() + "1");
			actuals.add(actual);
		}
	}

}

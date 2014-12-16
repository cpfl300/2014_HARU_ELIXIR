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
import elixir.model.Office;
import elixir.model.OfficeTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElixirConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class OfficeDaoTest {

	@Autowired
	private OfficeDao officeDao;
	private List<Office> offices;
	
	@Before
	public void setup() {
		offices = OfficeTest.preparedList();
	}
	
	// find by officeId
	@Test
	public void findByOfficeId() {
		List<Office> actuals = new ArrayList<Office>();
		
		// exec
		for (Office office : offices) {
			Office actual = officeDao.findByOfficeId(office.getOfficeId());
			actuals.add(actual);
		}
		
		// assert
		OfficeTest.ASSERTS(actuals, offices);		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findByOfficeId_emptyResultDataAccess() {
		List<Office> actuals = new ArrayList<Office>();
		
		// exec - except
		for (Office office : offices) {
			Office actual = officeDao.findByOfficeId(office.getOfficeId() + "1");
			actuals.add(actual);
		}
	}
	
	
	// find by officeName
	@Test
	public void findByOfficeName() {
		List<Office> actuals = new ArrayList<Office>();
		
		// exec
		for (Office office : offices) {
			Office actual = officeDao.findByOfficeName(office.getOfficeName());
			actuals.add(actual);
		}
		
		// assert
		OfficeTest.ASSERTS(actuals, offices);		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findByOfficeName_emptyResultDataAccess() {
		List<Office> actuals = new ArrayList<Office>();
		
		// exec - except
		for (Office office : offices) {
			Office actual = officeDao.findByOfficeName(office.getOfficeName() + "1");
			actuals.add(actual);
		}
	}
	

}

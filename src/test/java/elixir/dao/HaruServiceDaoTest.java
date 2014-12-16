package elixir.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import elixir.config.ElixirConfig;
import elixir.model.HaruService;
import elixir.model.HaruServiceTest;
import elixir.model.ServiceUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElixirConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class HaruServiceDaoTest {
	
	@Autowired
	private HaruServiceDao haruServiceDao;

	
	// add and find
	@Test
	public void addAndFind() {
		HaruService expected = HaruServiceTest.create(0, "20140101", ServiceUnit.MORNING);
		
		// exec - add
		int id = haruServiceDao.add(expected);
		expected.setId(id);
		
		// exec - find by id
		HaruService actual = haruServiceDao.findById(id);
		
		// assert
		HaruServiceTest.ASSERT(actual, expected);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findById_emptyResultDataAccess() {
		//findById - except
		haruServiceDao.findById(1);
	}

}

package elixir.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import elixir.config.ElixirConfig;
import elixir.model.Status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElixirConfig.class, loader=AnnotationConfigContextLoader.class)
@Transactional
public class StatusDaoTest {
	
	@Autowired
	private StatusDao statusDao;
	
	private Status status;
	
	@Before
	public void setup() {
		status = new Status("20141207", true);
	}
	
	@Test
	public void getCount() {
		int initCount = statusDao.getCount();
		assertThat(statusDao.getCount()-initCount, is(0));
	}

	@Test
	public void add() {
		int initCount = statusDao.getCount();
		
		statusDao.add(status);
		
		assertThat(statusDao.getCount()-initCount, is(1));
	}


}

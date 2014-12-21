package elixir.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import elixir.config.ElixirConfig;
import elixir.model.CountMethod;
import elixir.model.CountMethodTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ElixirConfig.class, loader=AnnotationConfigContextLoader.class)
public class CountMethodDaoTest {

	@Autowired
	private CountMethodDao countMethodDao;
	private List<CountMethod> countMethods;
	
	
	@Before
	public void setup() {
		countMethods = CountMethodTest.preparedList();
	}
	
	@Test
	public void findById() {
		List<CountMethod> actuals = new ArrayList<CountMethod>();
		
		for (CountMethod cm : countMethods) {			
			CountMethod actual = countMethodDao.findById(cm.getId());
			actuals.add(actual);
		}
		
		CountMethodTest.ASSERTS(actuals, countMethods);
		
	}

}

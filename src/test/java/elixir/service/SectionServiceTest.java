package elixir.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import elixir.dao.SectionDao;
import elixir.model.Section;
import elixir.model.SectionsTest;


@RunWith(MockitoJUnitRunner.class)
public class SectionServiceTest {
	
	@InjectMocks
	private SectionService sectionService;
	
	@Mock
	private SectionDao sectionDaoMock;
	
	private List<Section> sections;
	
	@Before
	public void setup() {
		sections = SectionsTest.preparedList();
	}
	

	@Test
	public void addAll() {
		// addAll
		sectionService.addAll(sections);
		
		// mock verify
		verify(sectionDaoMock, times(1)).addAll(sections);
	}

}

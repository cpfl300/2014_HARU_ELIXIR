package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SectionTest {
	
	
	// create
	public static Section CREATE(String sectionId, String sectionName) {
		Section section = new Section();
		
		section.setSectionId(sectionId);
		section.setSectionName(sectionName);
		
		return section;
	}
	
	// assert
	public static void ASSERT(Section actual, Section expected) {
		assertThat(actual.getSectionId(), is(expected.getSectionId()));
		assertThat(actual.getSectionName(), is(expected.getSectionName()));
	}

}

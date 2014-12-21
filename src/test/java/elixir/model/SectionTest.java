package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class SectionTest {
	
	
	// assert
	public static void ASSERT(Section actual, Section expected) {
		SectionTest.ASSERT(actual, expected, new String[]{"id", "sectionId", "sectionName", "superId"});
	}
	
	public static void ASSERT(Section actual, Section expected, String[] fieldArr) {
		List<String> fields = Arrays.asList(fieldArr);
		
		if (fields.contains("id") && expected.getId() != 0)
			assertThat(actual.getId(), is(expected.getId()));
		
		if (fields.contains("sectionId"))
			assertThat(actual.getSectionId(), is(expected.getSectionId()));
		
		if (fields.contains("sectionName"))
			assertThat(actual.getSectionName(), is(expected.getSectionName()));
		
		if (fields.contains("superId") && expected.getSuperId() != 0)
			assertThat(actual.getSuperId(), is(expected.getSuperId()));
	}
	
	// create
	public static Section create(int id, String sectionId, String sectionName, int superId) {
		Section section = new Section();
		
		section.setId(id);
		section.setSectionId(sectionId);
		section.setSectionName(sectionName);
		section.setSuperId(superId);
		
		return section;
	}
}

package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

public class SectionTest {
	
	
	// assert
	public static void ASSERT(Section actual, Section expected) {
		assertThat(actual.getSectionId(), is(expected.getSectionId()));
		assertThat(actual.getSectionName(), is(expected.getSectionName()));
	}
	
	public static void ASSERTS(List<Section> actuals, List<Section> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Section actual = actuals.get(i);
			Section expected = expecteds.get(i);
			
			SectionTest.ASSERT(actual, expected);
		}		
	}
	
	// create
	public static Section create(String sectionId, String sectionName) {
		Section section = new Section();
		
		section.setSectionId(sectionId);
		section.setSectionName(sectionName);
		
		return section;
	}
	
	public static List<Section> preparedList() {
		
		return Arrays.asList(new Section[] {
				SectionTest.create("104", "생활/문화"),
				SectionTest.create("227", "지역"),
				SectionTest.create("316", "3단계_16")
		});
	}

}

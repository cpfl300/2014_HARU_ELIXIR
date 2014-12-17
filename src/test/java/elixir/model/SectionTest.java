package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import elixir.test.ElixirTestUtils;

public class SectionTest {
	
	
	// assert
	public static void ASSERT(Section actual, Section expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getSectionId(), is(expected.getSectionId()));
		assertThat(actual.getSectionName(), is(expected.getSectionName()));
		assertThat(actual.getSuperId(), is(expected.getSuperId()));
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
	public static Section create(int id, String sectionId, String sectionName, int superId) {
		Section section = new Section();
		
		section.setId(id);
		section.setSectionId(sectionId);
		section.setSectionName(sectionName);
		section.setSuperId(superId);
		
		return section;
	}
	
	public static List<Section> preparedList() {
		
		return Arrays.asList(new Section[] {
				SectionTest.create(4, "104", "생활/문화", 1),
				SectionTest.create(34, "227", "지역", 3),
				SectionTest.create(91, "316", "3단계_16", 40)
		});
	}
	
	public static List<Section> preparedList(String[] fields){
		List<Section> sections = SectionTest.preparedList();
		
		ElixirTestUtils.initComplementaryFields(sections, fields);
		
		return sections;
	}

}

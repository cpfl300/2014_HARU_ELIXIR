package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OfficeTest {

	// create
	public static Office create(int id, String officeId, String name, String sectionName) {
		Office office = new Office();
		
		office.setId(id);
		office.setOfficeId(officeId);
		office.setOfficeName(name);
		office.setSectionName(sectionName);
		
		return office;
		
	}
	
	// assert
	public static void ASSERT(Office actual, Office expected) {
		assertThat(actual.getOfficeId(), is(expected.getOfficeId()));
		assertThat(actual.getOfficeName(), is(expected.getOfficeName()));
		assertThat(actual.getSectionName(), is(expected.getSectionName()));
	}

}

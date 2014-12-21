package elixir.model;

import java.util.ArrayList;
import java.util.List;

public class SectionsTest {

	public static void ASSERT(List<Section> actuals, List<Section> expecteds) {
		// TODO Auto-generated method stub
		
	}
	
	public static List<List<Section>> preparedList() {
		List<List<Section>> sectionsList = new ArrayList<List<Section>>();
		
		sectionsList.add(SectionTest.preparedList1());
		sectionsList.add(SectionTest.preparedList2());
		sectionsList.add(SectionTest.preparedList3());
		
		return sectionsList;
	}
	
	

}

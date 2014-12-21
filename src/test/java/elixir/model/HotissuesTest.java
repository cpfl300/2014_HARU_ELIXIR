package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import elixir.test.ElixirTestUtils;

public class HotissuesTest {

	public static List<Hotissue> preparedList(List<Date> dates, List<Section> sections, String[] fields){
		List<Hotissue> hotissues = preparedList(dates, sections);
		ElixirTestUtils.initComplementaryFields(hotissues, fields);
		
		return hotissues;
	}

	public static List<Hotissue> preparedList(List<Date> dates, List<Section> sections) {
		
		return Arrays.asList(new Hotissue[]{
				HotissueTest.create(1, "887522", "연애지침서", sections.get(0), 10.0, "imageUrl1", new Timestamp(dates.get(0).getTime())),
				HotissueTest.create(2, "893847", "화제의 판결", sections.get(1), 20.0, "imageUrl2", new Timestamp(dates.get(1).getTime())),
				HotissueTest.create(3, "887553", "따뜻한 세상", sections.get(2), 30.0, "imageUrl3", new Timestamp(dates.get(2).getTime())),
			});
	}

	public static void ASSERTS(List<Hotissue> actuals, List<Hotissue> expecteds, String[] fieldArr) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Hotissue actual = actuals.get(i);
			Hotissue expected = expecteds.get(i);
			
			HotissueTest.ASSERT(actual, expected, fieldArr);
		}
	}

	public static void ASSERTS(List<Hotissue> actuals, List<Hotissue> expecteds) {
		HotissuesTest.ASSERTS(actuals, expecteds, new String[]{"id", "hotissueId", "title", "section", "score", "imageUrl"});
	}

}

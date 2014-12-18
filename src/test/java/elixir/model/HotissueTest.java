package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elixir.test.ElixirTestUtils;

public class HotissueTest {
	
	private static final Logger log = LoggerFactory.getLogger(HotissueTest.class);

	
	@Before
	public void setup() {
		
	}

	
	
	
	// asserter
	public static void ASSERT(Hotissue actual, Hotissue expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getHotissueId(), is(expected.getHotissueId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		if (expected.getSection() != null) SectionTest.ASSERT(actual.getSection(), expected.getSection());
		assertThat(actual.getScore(), is(expected.getScore()));
		assertThat(actual.getImageUrl(), is(expected.getImageUrl()));
	}
	
	public static void ASSERTS(List<Hotissue> actuals, List<Hotissue> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Hotissue actual = actuals.get(i);
			Hotissue expected = expecteds.get(i);
			
			HotissueTest.ASSERT(actual, expected);
		}
	}
	
	// creator
	public static Hotissue create(int id, String hotissueId, String title, Section section, double score, String imageUrl, Timestamp timestamp) {
		Hotissue hotissue = new Hotissue();
		
		hotissue.setId(id);
		hotissue.setHotissueId(hotissueId);
		hotissue.setTitle(title);
		hotissue.setSection(section);
		hotissue.setScore(score);
		hotissue.setImageUrl(imageUrl);
		hotissue.setTimestamp(timestamp);
		
		return hotissue;
	}
	
	public static List<Hotissue> preparedList(List<Date> dates, List<Section> sections) {
		
		return Arrays.asList(new Hotissue[]{
				HotissueTest.create(1, "887522", "연애지침서", sections.get(0), 10.0, "imageUrl1", new Timestamp(dates.get(0).getTime())),
				HotissueTest.create(2, "893847", "화제의 판결", sections.get(1), 20.0, "imageUrl2", new Timestamp(dates.get(1).getTime())),
				HotissueTest.create(3, "887553", "따뜻한 세상", sections.get(2), 30.0, "imageUrl3", new Timestamp(dates.get(2).getTime())),
			});
	}
	
	public static List<Hotissue> preparedList(List<Date> dates, List<Section> sections, String[] fields){
		List<Hotissue> hotissues = HotissueTest.preparedList(dates, sections);
		ElixirTestUtils.initComplementaryFields(hotissues, fields);
		
		return hotissues;
	}
	
}

package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class HotissueTest {

	
	// asserter
	public static void ASSERT(Hotissue actual, Hotissue expected) {
		HotissueTest.ASSERT(actual, expected, new String[]{"id", "hotissueId", "title", "section", "score", "imageUrl"});
	}
	
	public static void ASSERT(Hotissue actual, Hotissue expected, String[] fieldArr) {
		List<String> fields = Arrays.asList(fieldArr);
		
		if (fields.contains("id"))
			assertThat(actual.getId(), is(expected.getId()));
		
		if (fields.contains("hotissueId"))
			assertThat(actual.getHotissueId(), is(expected.getHotissueId()));
		
		if (fields.contains("title"))
			assertThat(actual.getTitle(), is(expected.getTitle()));
		
		if (fields.contains("section") && expected.getSection() != null)
			SectionTest.ASSERT(actual.getSection(), expected.getSection());
		
		if (fields.contains("score"))
			assertThat(actual.getScore(), is(expected.getScore()));
		
		if (fields.contains("imageUrl"))
			assertThat(actual.getImageUrl(), is(expected.getImageUrl()));
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
	
}

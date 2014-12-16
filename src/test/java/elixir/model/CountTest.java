package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;

public class CountTest {
	
	
	// create
	public static Count CREATE(int hitCount, int readCount, int rank, Timestamp timestamp, int countValue, String countUnit) {
		Count count = new Count();
		
		count.setHitCount(hitCount);
		count.setReadCount(readCount);
		count.setRank(rank);
		count.setTimestamp(timestamp);
		count.setCountValue(countValue);
		count.setCountUnit(countUnit);
		
		return count;
	}
	
	// assert
	public static void ASSERT(Count actual, Count expected) {
		assertThat(actual.getHitCount(), is(expected.getHitCount()));
		assertThat(actual.getReadCount(), is(expected.getReadCount()));
		assertThat(actual.getRank(), is(expected.getRank()));
		assertThat(actual.getTimestamp(), is(expected.getTimestamp()));
		assertThat(actual.getCountValue(), is(expected.getCountValue()));
		assertThat(actual.getCountUnit(), is(expected.getCountUnit()));
	}
}

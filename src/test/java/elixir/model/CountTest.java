package elixir.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CountTest {
	
	

	
	// assert
	public static void ASSERT(Count actual, Count expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getHitCount(), is(expected.getHitCount()));
		assertThat(actual.getReadCount(), is(expected.getReadCount()));
		assertThat(actual.getRank(), is(expected.getRank()));
		assertThat(actual.getTimestamp(), is(expected.getTimestamp()));
		assertThat(actual.getCountMethod(), is(expected.getCountMethod()));
	}
	
	public static void ASSERTS(List<Count> actuals, List<Count> expecteds) {
		assertThat(actuals.size(), is(expecteds.size()));
		for (int i=0; i<actuals.size(); i++) {
			Count actual = actuals.get(i);
			Count expected = expecteds.get(i);
			
			CountTest.ASSERT(actual, expected);
		}		
	}
	
	// create
	public static Count create(int id, int hitCount, int readCount, int rank, Timestamp timestamp, CountMethod countMethod) {
		Count count = new Count();
		
		count.setId(id);
		count.setHitCount(hitCount);
		count.setReadCount(readCount);
		count.setRank(rank);
		count.setTimestamp(timestamp);
		count.setCountMethod(countMethod);
		
		return count;
	}
	
	public static List<Count> preparedList(List<Date> dates, List<CountMethod> countMethod) {
				
		return Arrays.asList(new Count[] {
				CountTest.create(1, 10000, 1000, 1, new Timestamp(dates.get(0).getTime()), countMethod.get(0)),
				CountTest.create(2, 20000, 2000, 2, new Timestamp(dates.get(1).getTime()), countMethod.get(1)),
				CountTest.create(3, 30000, 3000, 3, new Timestamp(dates.get(2).getTime()), countMethod.get(2))
		});
	}
}

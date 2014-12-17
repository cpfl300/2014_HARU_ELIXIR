package elixir.test;

public class ElixirTestUtils {
	
	public static int getCount(int[] rows) {
		int size = 0;
		
		for (int row : rows)
			size += row;
		
		return size;
	}
}

package elixir.model;

import java.sql.Timestamp;

public class Count {
	
	private int id;
	private int hitCount;
	private int readCount;
	private int rank;
	private Timestamp timestamp;
	private CountMethod countMethod;
	
	// empty
	public Count() { }
	
	
	// setter getter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public CountMethod getCountMethod() {
		return countMethod;
	}


	public void setCountMethod(CountMethod countMethod) {
		this.countMethod = countMethod;
	}


}

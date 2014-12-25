package elixir.model;

public class Status {

	private int id;
	private String date;
	private boolean afternoon;
	
	// empty
	public Status() { }
	
	public Status(String date, boolean afternoon) {
		this.date = date;
		this.afternoon = afternoon;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public boolean isAfternoon() {
		return afternoon;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setAfternoon(boolean afternoon) {
		this.afternoon = afternoon;
	}

}

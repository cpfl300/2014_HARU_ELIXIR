package elixir.model;

public class Status {

	private String date;
	private boolean afternoon;

	public Status(String date, boolean afternoon) {
		this.date = date;
		this.afternoon = afternoon;
	}

	public String getDate() {
		return date;
	}

	public boolean isAfternoon() {
		return afternoon;
	}

}

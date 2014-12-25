package elixir.model;

import java.util.Calendar;
import java.util.Date;

import elixir.utility.ElixirUtils;

public class Status {
	
	public static final int MORNING = 6;
	public static final int AFTERNOON = 18;

	private int id;
	private Date date;
	private boolean afternoon;
	
	// empty
	public Status() { }
	
	public Status(String date, boolean afternoon) {
		this.date = parse(date);
		this.afternoon = afternoon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return format(date);
	}

	public void setDate(String date) {
		this.date = parse(date);
	}

	public void setAfternoon(boolean afternoon) {
		this.afternoon = afternoon;
	}
	
	public boolean isAfternoon() {
		return afternoon;
	}

	public Status next() {
		
		return this.next(ElixirUtils.now());
	}
	
	Status next(Date now) {
		
		if (getServiceTime() > now.getTime()) {
			return this;
		}
		
		if (afternoon == false) {
			afternoon = true;
			return this;
		}
		
		afternoon = false;
		plusOneDay();
		
		return this;
	}
	
	private void plusOneDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		this.date = calendar.getTime();
	}

	private long getServiceTime() {
		int hour = Status.MORNING;
		if (this.afternoon == true) {
			hour = Status.AFTERNOON;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(this.date);
		calendar.add(Calendar.HOUR, hour);
		
		return calendar.getTimeInMillis();
	}

	private String format(Date date) {
		
		return ElixirUtils.format("yyyyMMdd", date);
	}
	
	private Date parse(String date) {
		
		return ElixirUtils.parse("yyyyMMdd", date);
	}

}

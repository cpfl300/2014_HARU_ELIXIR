package elixir.model;

public class HaruService {
	
	private int id;
	private String date;
	private ServiceUnit serviceUnit;
	
	// empty
	public HaruService() { }

	// setter getter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean getStatus() {
		return serviceUnit.getValue();
	}
	
	public void setStatus(boolean status) {
		this.serviceUnit = ServiceUnit.getEnum(status);
	}

	public void setStatus(ServiceUnit serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

}

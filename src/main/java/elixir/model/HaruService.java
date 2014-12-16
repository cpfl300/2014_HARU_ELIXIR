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

	public ServiceUnit getServiceUnit() {
		return serviceUnit;
	}

	public void setServiceUnit(ServiceUnit serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

}

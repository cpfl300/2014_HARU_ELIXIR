package elixir.model;

public class Office {
	
	private int id;
	private String officeId;
	private String officeName;
	private String sectionName;

	// empty
	public Office() { }
	
	// standard
	public Office(String officeId, String officeName) {
		this.officeId = officeId;
		this.officeName = officeName;
	}

	public Office(String officeId) {
		this(officeId, null);
	}
	

	// setter getter
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}	
	
	public String getOfficeId() {
		return officeId;
	}


	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	
	
	

}

package elixir.model;

public class Section {
	
	private String sectionId;
	private String sectionName;
	
	// empty
	public Section() { }

	public Section(String sectionId) {
		this(sectionId, null);
	}
	
	// standard
	public Section(String sectionId, String sectionName) {
		this.sectionId = sectionId;
		this.sectionName = sectionName;
	}


	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
}

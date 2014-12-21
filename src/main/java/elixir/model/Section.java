package elixir.model;

public class Section {
	
	private int id;
	private String sectionId;
	private String sectionName;
	private int superId;
	
	// empty
	public Section() { }

	// standard
	public Section(String sectionId, String sectionName) {
		this.sectionId = sectionId;
		this.sectionName = sectionName;
	}
	
	public Section(String sectionId) {
		this(sectionId, null);
	}
	
	public Section(int id) {
		this.id = id;
	}

	// setter getter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getSuperId() {
		return superId;
	}

	public void setSuperId(int superId) {
		this.superId = superId;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", sectionId=" + sectionId + ", sectionName=" + sectionName + ", superId=" + superId + "]";
	}
	
	
	
}

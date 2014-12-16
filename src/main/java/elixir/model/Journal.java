package elixir.model;

public class Journal {
	
	private String id;
	private String name;
//	private String section;
	
	
//	public Journal() {
//	}
//
//	public Journal(String name) {
//		this(0, name, null);
//	}
//	
//	public Journal(int id) {
//		this(id, null, null);
//	}
//
//	public Journal(String name, String section) {
//		this(0, name, section);
//	}
//	
//	public Journal(int id, String name, String section) {
//		this.id = id;
//		this.name = name;
//		this.section = section;
//	}
	
	// From NaverArticleList
	// "officeId" : "421",
	// "officeName" : "",
//	public Journal(String officeId, String officeName) {
//		this.officeId = officeId;
//		this.officeName = officeName;
//	}
	
	public Journal(String id, String name) {
		this.id = id;
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}

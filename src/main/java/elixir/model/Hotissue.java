package elixir.model;

import java.sql.Timestamp;


public class Hotissue {
	
	private int id;
	private String hotissueId;
	private String title;
	private Section section;
	private double score;
	private String imageUrl;
	private Timestamp timestamp;
	
	// empty
	public Hotissue() { }

	
	// setter getter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotissueId() {
		return hotissueId;
	}

	public void setHotissueId(String hotissueId) {
		this.hotissueId = hotissueId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


}

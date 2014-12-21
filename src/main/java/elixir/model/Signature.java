package elixir.model;

public class Signature {
	
	private String officeId;
	private String articleId;
	
	public Signature() { }
	
	public Signature(String officeId, String articleId) {
		this.officeId = officeId;
		this.articleId = articleId;
	}
	
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	@Override
	public String toString() {
		return "Signature [officeId=" + officeId + ", articleId=" + articleId + "]";
	}
	
	
}

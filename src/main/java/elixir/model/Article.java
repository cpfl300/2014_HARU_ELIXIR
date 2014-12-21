package elixir.model;

import java.sql.Timestamp;
import java.util.List;


public class Article implements Signable {
	
//	private int id;
//	private Hotissue hotissue;
//	private Journal journal;
//	private Section section;
//	private String title;
//	private String content;
//	private String date;
//	private int hits;
//	private int completedReadingCount;
//	private double score;
//	private String timestamp;
//	private int sequence;
	
	private int id;
	
	// essential
	private String articleId;
	private Office office;
//	private Section section;
	private List<Section> sections;
	private String contributionDate;
	private String contributionTime;
	private String title;

	// nullable
	private String content;
	private String orgUrl;
	private String imageUrl;
	private double score;
	
	// default
	private Timestamp timestamp;
		
	// empty
	public Article() { }
	
	// all - standard
//	public Article(int id, String articleId, Office office, Section section,  
//			String contributionDate, String contributionTime, String title,
//			String content, String orgUrl, String imageUrl, double score,
//			Timestamp timestamp) {
//		this.id = id;
//		this.articleId = articleId;
//		this.office = office;
//		this.section = section;
//		this.contributionDate = contributionDate;
//		this.contributionTime = contributionTime;
//		this.title = title;
//		
//		this.content = content;
//		this.orgUrl = orgUrl;
//		this.imageUrl = imageUrl;
//		
//		this.score = score;
//		this.timestamp = timestamp;
//	}
//	
//	// all - need to wrap
//	public Article(
//			int id, String articleId, String officeId, String sectionId, 
//			String contributionDate, String contributionTime, String title,
//			String content, String orgUrl, String imageUrl, double score,
//			Timestamp timestamp) {
//		
//		this(id, articleId, new Office(officeId), new Section(sectionId), 
//				contributionDate, contributionTime,  title,
//				content, orgUrl, imageUrl, score, timestamp);
//	}


	
	
	

	// From NaverArticleList
	// "articleId" : "0006718568",
	// Journal
	// List<Section>
	// "title" : "'바뀐 삼성 채용'…대학도 학생도 고심",
	// "serviceDate" : "20140124",
	// "serviceTime" : "103000",
	// "imageUrl" : "http://imgnews.naver.net/image/origin/001/2014/01/24/6718568.jpg",
	// "reporter" : "정빛나",

//	public Article(String articleId, Journal journal, String title,
//			String orgUrl, Section section, String serviceDate, String serviceTime, String imageUrl) {
//		
//		this(0, articleId, journal, title, null, orgUrl, section, serviceDate, serviceTime, imageUrl, 0, 0);
//	}
//	

	
	
//	public String getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(String timestamp) {
//		if (timestamp.lastIndexOf('.') != -1) {
//			this.timestamp = usableDateStr(timestamp);
//			return;
//		}
//		
//		this.timestamp = timestamp;
//	}
//
//	
//	private String usableDateStr(String dateStr) {
//		
//		return dateStr.substring(0, dateStr.lastIndexOf("."));
//	}
//
//	
//	public int getSequence() {
//		return sequence;
//	}
//
//	public void setSequence(int sequence) {
//		this.sequence = sequence;
//	}
//	
//	public void clacScore() {
//		this.score = (double) this.completedReadingCount / this.hits;
//		
//	}




//	public static List<Article> asList(List<Hotissue> hotissues) {
//		List<Article> articles = new ArrayList<Article>();
//		
//		for (Hotissue hotissue : hotissues) {
//			articles.addAll(hotissue.getArticles());
//			
//		}
//		
//		return articles;
//	}
//
//	public static List<Article> asListWithSequenceIncludeTimestamp(List<Hotissue> hotissues, String timestamp) {
//		List<Article> articles = new ArrayList<Article>();
//		int sequence = 1;
//		
//		for (Hotissue hotissue : hotissues) {
//			for (Article a : hotissue.getArticles()) {
//				articles.add(new Article(a.getId(), timestamp, sequence++));
//			}
//		}
//		
//		return articles;
//	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSection(List<Section> sections) {
		this.sections = sections;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrgUrl() {
		return orgUrl;
	}

	public void setOrgUrl(String orgUrl) {
		this.orgUrl = orgUrl;
	}

	public String getContributionDate() {
		return contributionDate;
	}

	public void setContributionDate(String contributionDate) {
		this.contributionDate = contributionDate;
	}

	public String getContributionTime() {
		return contributionTime;
	}

	public void setContributionTime(String contributionTime) {
		this.contributionTime = contributionTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", articleId=" + articleId + ", office=" + office + ", sections=" + sections + ", contributionDate="
				+ contributionDate + ", contributionTime=" + contributionTime + ", title=" + title + ", content=" + content + ", orgUrl=" + orgUrl
				+ ", imageUrl=" + imageUrl + ", score=" + score + ", timestamp=" + timestamp + "]";
	}

	@Override
	public Signature sign() {
		Signature signature = null;
		
		try {
			signature = new Signature();
			
			signature.setArticleId(articleId);
			signature.setOfficeId(getOffice().getOfficeId());
		} catch (NullPointerException e) {
			
			throw new SignatureFailureException("article fail to sign", e);
		}
		
		return signature;
	}

	
	
	
	
	
}

package elixir.model;

import java.util.ArrayList;
import java.util.List;

import refinery.model.NaverSection;


public class Article {
	
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
	
	private String articleId;
	private Journal journal;
	private List<Section> sections;
	private String title;
	private String imageUrl;
	private String serviceDate;
	private String serviceTime;
	private int hitCount;
	private int readCount;
	
	// From NaverArticleList
	// "articleId" : "0006718568",
	// Journal
	// List<Section>
	// "title" : "'바뀐 삼성 채용'…대학도 학생도 고심",
	// "serviceDate" : "20140124",
	// "serviceTime" : "103000",
	// "imageUrl" : "http://imgnews.naver.net/image/origin/001/2014/01/24/6718568.jpg",
	// "reporter" : "정빛나",
	public Article(
			String articleId, Journal journal, List<Section> sections,
			String title, String imageUrl, String serviceDate, String serviceTime,
			int hitCount, int readCount) {
		
			this.articleId = articleId;
			this.journal = journal;
			this.sections = sections;
			this.title = title;
			this.imageUrl = imageUrl;
			this.serviceDate = serviceDate;
			this.serviceTime = serviceTime;
			this.hitCount = hitCount;
			this.readCount = readCount;
	}
	
	
	
//	public Article() {
//
//	}
//	
//	// update score
//	public Article(int id, double score) {
//		this.id = id;
//		this.score = score;
//	}
//	
//	
//	// half_day
//	public Article(int id, int sequence, String timestamp) {
//		this.id = id;
//		this.sequence = sequence;
//		this.timestamp = timestamp;
//	}
//	
//	// self dependency
//	// asListWithSequenceIncludeTimestamp
//	public Article(int id, String timestamp, int sequence) {
//		this.id = id;
//		this.timestamp = timestamp;
//		this.sequence = sequence;
//	}
//	
//	// standard
//	public Article(int id, Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
//			int completedReadingCount) {
//		this(id, hotissue, journal, section, title, date, content, hits, completedReadingCount, 0);
//	}
//	
//	public Article(Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
//			int completedReadingCount) {
//		this(0, hotissue, journal, section, title, date, content, hits, completedReadingCount, 0);
//	}
//	
//	public Article(Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
//			int completedReadingCount, double score) {
//		this(0, hotissue, journal, section, title, date, content, hits, completedReadingCount, score);
//	}
	
	// standard
//	public Article(int id, Hotissue hotissue, Journal journal, Section section, String title, String date, String content, int hits,
//			int completedReadingCount, double score) {
//		this.id = id;
//		this.hotissue = hotissue;
//		this.journal = journal;
//		this.section = section;
//		this.title = title;
//		this.date = date;
//		this.content = content;
//		this.hits = hits;
//		this.completedReadingCount = completedReadingCount;
//		this.score = score;
//	}
//	
//	public Article(Hotissue hotissue, Journal journal, Section section, String title, String date) {
//		this(0, hotissue, journal, section, title, date, null, 0, 0, 0);
//	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Hotissue getHotissue() {
		return hotissue;
	}

	public void setHotissue(Hotissue hotissue) {
		this.hotissue = hotissue;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		if (date.lastIndexOf('.') != -1) {
			this.date = usableDateStr(date);
			return;
		}
		
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getCompletedReadingCount() {
		return completedReadingCount;
	}

	public void setCompletedReadingCount(int completedReadingCount) {
		this.completedReadingCount = completedReadingCount;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		if (timestamp.lastIndexOf('.') != -1) {
			this.timestamp = usableDateStr(timestamp);
			return;
		}
		
		this.timestamp = timestamp;
	}

	
	private String usableDateStr(String dateStr) {
		
		return dateStr.substring(0, dateStr.lastIndexOf("."));
	}

	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public void clacScore() {
		this.score = (double) this.completedReadingCount / this.hits;
		
	}
	
	


	
	
	
	public static List<Article> asList(List<Hotissue> hotissues) {
		List<Article> articles = new ArrayList<Article>();
		
		for (Hotissue hotissue : hotissues) {
			articles.addAll(hotissue.getArticles());
			
		}
		
		return articles;
	}

	public static List<Article> asListWithSequenceIncludeTimestamp(List<Hotissue> hotissues, String timestamp) {
		List<Article> articles = new ArrayList<Article>();
		int sequence = 1;
		
		for (Hotissue hotissue : hotissues) {
			for (Article a : hotissue.getArticles()) {
				articles.add(new Article(a.getId(), timestamp, sequence++));
			}
		}
		
		return articles;
	}
	
}

package elixir.model;


public class Hotissue {
	
	private String hotissueId;
	private String title;
	
	// empth
	public Hotissue() { }


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

//	public static List<Hotissue> orderByHotissue(List<Article> articles) {
//		// 자칫 많아질 수 있는 key검색을 O(1)로 계산위해 map 사용
//		Map<Hotissue, Hotissue> hotissueMap = new HashMap<Hotissue, Hotissue>();
//		
//		for (Article article : articles) {
//			Hotissue hotissue = article.getHotissue();
//			if (!hotissueMap.containsKey(hotissue)) {
//				hotissueMap.put(hotissue, null);
//			}
//			
//			hotissue.addArticle(article);
//		}
//		
//		return new ArrayList<Hotissue>(hotissueMap.keySet());
//	}
	
	
}

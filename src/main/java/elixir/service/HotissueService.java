package elixir.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elixir.dao.HotissueDao;
import elixir.model.Hotissue;

@Service
public class HotissueService {
	
	private static final Logger log = LoggerFactory.getLogger(HotissueService.class);
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Autowired
	private ArticleService articleService;
	
	public void addAll(List<Hotissue> hotissues) {
		//hotissueDao.addAll(hotissues);
	}

	
//	@Transactional
//	public int add(Hotissue hotissue) {
//		
//		int id = hotissue.hashCode();
//		hotissue.setId(id);
//		
//		try {
//			hotissueDao.findById(id);
//			
//		} catch (EmptyResultDataAccessException e) {
//			hotissueDao.add(hotissue);
//
//		}
//		
//		return id;
//			
//	}
//	
//	public Set<Integer> addHotissues(List<Hotissue> hotissues) {
//		
//		setId(hotissues);
//		int[] updateState = hotissueDao.addHotissues(hotissues);
//
//		return extractSucceedHotissue(updateState, hotissues);
//	}
//	
//	
//	private Set<Integer> extractSucceedHotissue(int[] updateState, List<Hotissue> hotissues) {
//		Set<Integer> succeed = new HashSet<Integer>();
//		for (int i=0; i<updateState.length; i++) {
//			if (updateState[i] == 0) continue;
//			succeed.add(hotissues.get(i).getId());
//		}
//		
//		return succeed;
//	}
//
//	public Hotissue getById(int id) {
//		Hotissue result = null;
//
//		try {
//			result = hotissueDao.findById(id);
//		} catch (EmptyResultDataAccessException e) {
//			// do nothing
//		}
//	
//		return result;
//	}
//
//


	


//	@Transactional
//	public int delete(int id) {
//			
//		try {		
//			
//			return hotissueDao.delete(id);
//			
//		} catch (DataIntegrityViolationException e) {
//			// do-nothing
//			return 0;
//		}
//
//	}
	
	
//	@Transactional
//	public int calcScore(String from, String to) {
//
//		List<Article> articles = articleService.getArticlesBetweenDates(from, to);
//		List<Hotissue> hotissues = Hotissue.orderByHotissue(articles);
//		
//		for (Hotissue hotissue : hotissues) {			
//			hotissue.calcScore();
//		}
//		
//		return getCount(hotissueDao.updateScores(hotissues));
//	}
	
//	public List<Hotissue> getByServiceDate(Date date) {
//		String[] dates = ElixirUtils.getServiceFormattedDatesByDate(date);
//			
//		return this.hotissueDao.getBetweenServiceDates(dates[0], dates[1]);
//	}
//	
//
//	public List<Hotissue> getWithArticlesByOrderedScore(int size) {
//		
//		return this.hotissueDao.getWithArticlesByOrderedScore(size);
//	}



	
}

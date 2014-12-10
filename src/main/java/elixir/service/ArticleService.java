package elixir.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import elixir.dao.ArticleDao;
import elixir.dao.JournalDao;
import elixir.dao.SectionDao;
import elixir.model.Article;
import elixir.model.Hotissue;
import elixir.model.Journal;
import elixir.model.Section;
import elixir.utility.ElixirUtils;

@Service
public class ArticleService {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
	
	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private HotissueService hotissueService;
	
	@Autowired
	private JournalDao journalDao;
	
	@Autowired
	private SectionDao sectionDao;

	@Transactional
	public int add(Article article) {
		setJournalAndSection(article);
		hotissueService.add(article.getHotissue());
		
		int id = article.hashCode();
		article.setId(id);
		
		try {			
			articleDao.add(article);
		} catch (DuplicateKeyException e) {
			// do-nothing
		}
		
		return id;
	}
	
	@Transactional
	public int addArticles(List<Article> articles) {
		List<Hotissue> hotissues = new ArrayList<Hotissue>();
		
		Iterator<Article> ir = articles.iterator();
		while(ir.hasNext()) {
			Article article = ir.next();
			setJournalAndSection(article);
			hotissues.add(article.getHotissue());
		}
		
		hotissueService.addHotissues(hotissues);
		
		int[] affectedRows = articleDao.addArticles(articles);
		
		return getCount(affectedRows);
		
	}
	

	public boolean has(int id) {
		
		try {
			articleDao.findById(id);
			
			return true;
			
		} catch(EmptyResultDataAccessException e) {
			
			return false;
		}
		
		
	}

//	@Transactional
//	public int delete(int id) {
//		Hotissue hotissue = articleDao.findById(id).getHotissue();
//		
//		int affectedRow = articleDao.deleteById(id);
//		hotissueService.delete(hotissue.getId());
//		
//		return affectedRow;
//
//	}
	
	@Transactional
	public int calcScore(String from, String to) {		
		List<Article> articles = articleDao.findBetweenDates(new String[] {from, to});
		Iterator<Article> ir = articles.iterator();
		while (ir.hasNext()) {
			ir.next().clacScore();
		}
		
		int[] rowState = articleDao.updateScores(articles);
		
		return getCount(rowState);
	}
	
	public List<Article> getArticlesByServiceDate(Date date) {
		String[] dates = ElixirUtils.getServiceFormattedDatesByDate(date);
		List<Article> articles = articleDao.findBetweenDatesAtHalfDay(dates);
		
		Iterator<Article> ir = articles.iterator();
		while(ir.hasNext()) {
			Article article = ir.next();
			setJournalAndSection(article);
		}
		
		return articles;
	}
	
	public Article getBySequenceAndServiceDate(int sequence, Date date) {
		String[] dates = ElixirUtils.getServiceFormattedDatesByDate(date);
		
		Article article = articleDao.findBySequenceBetweenDatesAtHalfDay(dates, sequence);
		setJournalAndSection(article);

		return article;
	}

	

	
	public List<Article> getByOrderedScore(int size) {
		
		return articleDao.findByScoreOrderFromOneTo(size);
	}

	public List<Article> getArticlesBetweenDates(String from, String to) {
		
		return articleDao.findBetweenDates(new String[] {from, to});
	}
	
	///////////
	
	
	public List<Article> getHalfDay() {
		Date serviceDate = ElixirUtils.nextServiceDate(ElixirUtils.getNow());
		String[] dates = ElixirUtils.getServiceFormattedDatesByDate(serviceDate);
		
		List<Article> articles = articleDao.findBetweenDatesAtHalfDay(dates);
		
		setJournalAndSectionAtArticles(articles);
		
		return articles;
	}
	

	public Article getHalfDayBySequence(int sequence) {
		Date serviceDate = ElixirUtils.nextServiceDate(ElixirUtils.getNow());
		String[] dates = ElixirUtils.getServiceFormattedDatesByDate(serviceDate);
		
		Article article = articleDao.findBySequenceBetweenDatesAtHalfDay(dates, sequence);
		setJournalAndSection(article);
		
		return article;
	}



	
	//////////
	
	private void setJournalAndSectionAtArticles(List<Article> articles) {
		
		for (Article a : articles) {
			setJournalAndSection(a);
		}
		
	}
	
	
	private void setJournalAndSection(Article article) {
		Journal journal = article.getJournal();
		Section section = article.getSection();
		
		Journal fulfilledJournal;
		Section fulfilledSection;
		
		if (journal.getName() != null) {
			fulfilledJournal = journalDao.getByName(journal.getName()); 
		} else {
			fulfilledJournal = journalDao.get(journal.getId());
		}
		
		if (section.getMinor() != null) {
			fulfilledSection = sectionDao.getByMinor(section.getMinor());
		} else {
			fulfilledSection = sectionDao.get(section.getId());
		}
		
		article.setJournal(fulfilledJournal);
		article.setSection(fulfilledSection);
	}
	
	private int getCount(int[] rows) {
		int count = 0;
		
		for (int row : rows) {
			count += row;
		}
		
		return count;
	}



}

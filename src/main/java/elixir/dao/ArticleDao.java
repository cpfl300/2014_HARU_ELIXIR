package elixir.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import elixir.model.Article;


@Repository
public class ArticleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int size() {
		
		return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM articles");
	}


	public int[] addAll(List<Article> articles) {
		int[] updateCounts = this.jdbcTemplate.batchUpdate(
			"INSERT INTO articles (offices_id, article_id, title, content, org_url, contribution_date, contribution_time, image_url) " +
			"VALUES (?,?,?,?,?,?,?,?)",
			new BatchPreparedStatementSetter() {
	
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Article article = articles.get(i);
					
					ps.setInt(1, article.getOffice().getId());
					ps.setString(2, article.getArticleId());
					ps.setString(3, article.getTitle());
					ps.setString(4, article.getContent());
					ps.setString(5, article.getOrgUrl());
					ps.setString(6, article.getContributionDate());
					ps.setString(7, article.getContributionTime());
					ps.setString(8, article.getImageUrl());
				}
	
				@Override
				public int getBatchSize() {
					
					return articles.size();
				}
				
			});

		return updateCounts;
	}


	public int updateContent(String articleId, String content) {
		
		return this.jdbcTemplate.update("UPDATE articles SET content = ? WHERE article_id = ?",
				content, articleId);
			
	}

	

//	private RowMapper<Article> articleMapper = (rs, rowNum) -> {		
//		Article article = new Article();
//		Hotissue hotissue = new Hotissue(rs.getInt("hotissues_id"));
//		Journal journal = new Journal(rs.getInt("journals_id"));
//		Section section = new Section(rs.getInt("minor_sections_id"));
//		
//		article.setHotissue(hotissue);
//		article.setJournal(journal);
//		article.setSection(section);
//		
//		article.setId(rs.getInt("id"));
//		article.setTitle(rs.getString("title"));
//		article.setDate(rs.getString("date"));
//		article.setContent(rs.getString("content"));
//		article.setHits(rs.getInt("hits"));
//		article.setCompletedReadingCount(rs.getInt("completed_reading_count"));
//		article.setScore(rs.getDouble("score"));
//		article.setTimestamp(rs.getString("timestamp"));		
//		
//		return article;
//	};
//	
//	private RowMapper<Article> articleForHalfDayMapper = (rs, rowNum) -> {		
//		Article article = new Article();
//		Hotissue hotissue = new Hotissue(rs.getInt("hotissues_id"));
//		Journal journal = new Journal(rs.getInt("journals_id"));
//		Section section = new Section(rs.getInt("minor_sections_id"));
//		
//		article.setHotissue(hotissue);
//		article.setJournal(journal);
//		article.setSection(section);
//		
//		article.setId(rs.getInt("id"));
//		article.setTitle(rs.getString("title"));
//		article.setDate(rs.getString("date"));
//		article.setContent(rs.getString("content"));
//		article.setHits(rs.getInt("hits"));
//		article.setCompletedReadingCount(rs.getInt("completed_reading_count"));
//		article.setScore(rs.getDouble("score"));
//		article.setTimestamp(rs.getString("timestamp"));
//		
//		article.setSequence(rs.getInt("sequence"));
//		
//		return article;
//	};
//
//	public int getCount() {
//		
//		return this.jdbcTemplate.queryForInt("select count(*) from articles");
//		
//	}
//
//	public int deleteAll() {
//		
//		return this.jdbcTemplate.update("delete from articles");
//
//	}
//
//	public void add(Article article) {
//		
//		this.jdbcTemplate.update(
//				"insert into articles(id, hotissues_id, title, journals_id, minor_sections_id, date, content, hits, completed_reading_count, score) values (?,?,?,?,?,?,?,?,?,?)",
//				article.getId(),
//				article.getHotissue().getId(),
//				article.getTitle(),
//				article.getJournal().getId(),
//				article.getSection().getId(),
//				article.getDate(),
//				article.getContent(),
//				article.getHits(),
//				article.getCompletedReadingCount(),
//				article.getScore()
//		);
//		
//	}
//
//	public Article findById(int id) {
//		
//		return this.jdbcTemplate.queryForObject (
//					"SELECT * FROM articles WHERE id = ?",
//					new Object[]{id},
//					this.articleMapper
//				);
//	}
//
//	public int deleteById(int id) {
//		
//		return this.jdbcTemplate.update(
//					"DELETE FROM articles WHERE id = ?",
//					new Object[] {id}
//				);
//		
//	}
//
//	public int[] addArticles(final List<Article> articles) {
//		
//		int[] updateCounts = this.jdbcTemplate.batchUpdate(
//					"INSERT IGNORE INTO articles(id, hotissues_id, title, journals_id, minor_sections_id, date, content, hits, completed_reading_count, score) VALUES (?,?,?,?,?,?,?,?,?,?)",
//					new BatchPreparedStatementSetter() {
//	
//						@Override
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							Article article = articles.get(i);
//							ps.setInt(1, article.getId());
//							ps.setInt(2, article.getHotissue().getId());
//							ps.setString(3, article.getTitle());
//							ps.setInt(4, article.getJournal().getId());
//							ps.setInt(5, article.getSection().getId());
//							ps.setString(6, article.getDate());
//							ps.setString(7, article.getContent());
//							ps.setInt(8, article.getHits());
//							ps.setInt(9, article.getCompletedReadingCount());
//							ps.setDouble(10, article.getScore());
//						}
//	
//						@Override
//						public int getBatchSize() {
//							
//							return articles.size();
//						}
//						
//					}
//				);
//		
//		return updateCounts;
//		
//	}
//
//	public int[] updateScores(final List<Article> articles) {
//		
//		return this.jdbcTemplate.batchUpdate(
//					"UPDATE articles SET score = ? WHERE id = ?",
//					new BatchPreparedStatementSetter() {
//
//						@Override
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							Article article = articles.get(i);
//							ps.setDouble(1, article.getScore());
//							ps.setInt(2, article.getId());
//						}
//
//						@Override
//						public int getBatchSize() {
//							
//							return articles.size();
//						}
//						
//					}
//				);
//	}
//
//	public List<Article> findBetweenDates(String[] dates) {
//		
//		return this.jdbcTemplate.query(
//					"SELECT * FROM articles WHERE (date BETWEEN ? AND ?)",
//					new Object[] {dates[0], dates[1]},
//					this.articleMapper
//				);
//	}
//
//	public List<Article> findByScoreOrderFromOneTo(int size) {
//		
//		return this.jdbcTemplate.query(
//					"SELECT * FROM articles ORDER BY score DESC LIMIT ?",
//					new Object[] {size},
//					this.articleMapper
//				);
//				
//	}
//
//
//	public int getCountAtHalfDay() {
//		
//		return this.jdbcTemplate.queryForInt("SELECT count(*) FROM half_day");
//	}
//
//	public int deleteAllAtHalfDay() {
//		
//		return this.jdbcTemplate.update("DELETE FROM half_day");
//		
//	}
//
//	public int addAtHalfDay(Article article) {
//		KeyHolder holder = new GeneratedKeyHolder();
//
//		this.jdbcTemplate.update(new PreparedStatementCreator() {           
//
//		                @Override
//		                public PreparedStatement createPreparedStatement(Connection conn)
//		                        throws SQLException {
//		                    PreparedStatement ps = conn.prepareStatement(
//		                    		"INSERT INTO half_day (timestamp, sequence, articles_id) VALUES (?, ?, ?)",
//		                    		Statement.RETURN_GENERATED_KEYS);
//		                    ps.setString(1, article.getTimestamp());
//							ps.setInt(2, article.getSequence());
//							ps.setInt(3, article.getId());
//		                    
//		                    return ps;
//		                }
//		            }, holder);
//
//		return holder.getKey().intValue();
//		
//	}
//
//	public int[] addArticlesAtHalfDay(final List<Article> articles) {
//		
//		return this.jdbcTemplate.batchUpdate(
//					"INSERT IGNORE INTO half_day (timestamp, sequence, articles_id) VALUES (?, ?, ?)",
//					new BatchPreparedStatementSetter() {
//
//						@Override
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							Article article = articles.get(i);
//							ps.setString(1, article.getTimestamp());
//							ps.setInt(2, article.getSequence());
//							ps.setInt(3, article.getId());
//						}
//
//						@Override
//						public int getBatchSize() {
//							
//							return articles.size();
//						}
//						
//					}
//				
//				);
//	}
//
//	public Article findByIdAtHalfDay(int id) {
//		
//		return this.jdbcTemplate.queryForObject(
//				"SELECT "
//					+ "articles.id, articles.title, articles.date, articles.content, articles.timestamp, "
//					+ "articles.journals_id, articles.hotissues_id, articles.minor_sections_id, "
//					+ "articles.hits, articles.completed_reading_count, articles.score, "
//					+ "half_day.sequence AS sequence "
//					+ "FROM (SELECT * FROM half_day WHERE id = ?) AS half_day "
//					+ "INNER JOIN articles "
//					+ "ON half_day.articles_id = articles.id",
//				new Object[] { id },
//				this.articleForHalfDayMapper
//			);
//	}
//	
//	public List<Article> findBetweenDatesAtHalfDay(String[] dates) {
//		
//		return this.jdbcTemplate.query(
//					"SELECT "
//					+ "articles.id, articles.title, articles.date, articles.content, articles.timestamp, "
//					+ "articles.journals_id, articles.hotissues_id, articles.minor_sections_id, "
//					+ "articles.hits, articles.completed_reading_count, articles.score, "
//					+ "half_day.sequence AS sequence "
//					+ "FROM (SELECT * FROM half_day WHERE timestamp BETWEEN ? AND ?) AS half_day "
//					+ "INNER JOIN articles "
//					+ "ON half_day.articles_id = articles.id "
//					+ "ORDER BY half_day.sequence",
//					
//					new Object[] {dates[0], dates[1]},
//					this.articleForHalfDayMapper
//				);
//	}
//
//	public Article findBySequenceBetweenDatesAtHalfDay(String[] dates,  int sequence) {
//		
//		return this.jdbcTemplate.queryForObject(
//				"SELECT "
//				+ "articles.id, articles.title, articles.date, articles.content, articles.timestamp, "
//				+ "articles.journals_id, articles.hotissues_id, articles.minor_sections_id, "
//				+ "articles.hits, articles.completed_reading_count, articles.score, "
//				+ "half_day.sequence AS sequence "
//				+ "FROM (SELECT * FROM half_day WHERE timestamp BETWEEN ? AND ? AND sequence = ?) AS half_day "
//				+ "INNER JOIN articles "
//				+ "ON half_day.articles_id = articles.id "
//				+ "ORDER BY half_day.sequence",
//				
//				new Object[] {dates[0], dates[1], sequence},
//				this.articleForHalfDayMapper
//			);
//	}

}

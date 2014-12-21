package elixir.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CountDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int size() {
		
		return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM counts");
	}

}

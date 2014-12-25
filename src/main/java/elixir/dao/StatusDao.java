package elixir.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import elixir.model.Status;

@Repository
public class StatusDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void add(Status status) {
		
		this.jdbcTemplate.update(
				"INSERT INTO status (date, afternoon) VALUES (?,?)",
				status.getDate(),
				status.isAfternoon());
	}

	public int getCount() {
		
		return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM status");
	}

}

package elixir.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import elixir.model.Status;

@Repository
public class StatusDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Status> statusMapper = (rs, rowNum) -> {
		Status status = new Status();
		
		status.setId(rs.getInt("id"));
		status.setDate(rs.getString("date"));
		status.setAfternoon(rs.getBoolean("afternoon"));
		
		return status;
	};

	public void add(Status status) {
		
		this.jdbcTemplate.update(
				"INSERT INTO status (date, afternoon) VALUES (?,?)",
				status.getDate(),
				status.isAfternoon());
	}

	public int getCount() {
		
		return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM status");
	}

	public Status getLast() {
		
		return this.jdbcTemplate.queryForObject(
					"SELECT * FROM status WHERE id = LAST_INSERT_ID()",
					this.statusMapper);
	}

}

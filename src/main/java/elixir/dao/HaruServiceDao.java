package elixir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import elixir.model.HaruService;

@Repository
public class HaruServiceDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<HaruService> haruServiceMapper = (rs, rowNum) -> {
		HaruService haruService = new HaruService();
		
		haruService.setId(rs.getInt("id"));
		haruService.setDate(rs.getString("date"));
		haruService.setStatus(rs.getBoolean("status"));
		
		return haruService;
		
	};

	public int add(HaruService haruService) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		
		this.jdbcTemplate.update(
				new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
						
						PreparedStatement ps = conn.prepareStatement(
								"INSERT INTO services (date, status) VALUES (?,?)",
								Statement.RETURN_GENERATED_KEYS);
						
						ps.setString(1, haruService.getDate());
						ps.setBoolean(2, haruService.getStatus());
								
						return ps;
					}
					
				}, holder);
		
		return holder.getKey().intValue();
		
	}

	public HaruService findById(int id) {
		
		return this.jdbcTemplate.queryForObject(
					"SELECT * FROM services WHERE id = ?",
					new Object[]{id},
					this.haruServiceMapper				
				);
	}

}

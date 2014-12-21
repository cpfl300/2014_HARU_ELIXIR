package elixir.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import elixir.model.CountMethod;
import elixir.model.CountUnit;

@Repository
public class CountMethodDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<CountMethod> countMethodMapper = (rs, rowNum) -> {
		CountMethod countMethod = new CountMethod();
		
		countMethod.setId(rs.getInt("id"));
		countMethod.setValue(rs.getInt("value"));
		countMethod.setUnit(CountUnit.getEnum(rs.getString("unit")));
		
		return countMethod;
		
	};

	public CountMethod findById(int id) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT count_methods.id AS id, count_methods.value AS value, count_method_units.unit AS unit " +
				"FROM count_methods INNER JOIN count_method_units " + 
				"ON count_methods.count_method_units_id = count_method_units.id " +
				"WHERE count_methods.id = ?",
				new Object[]{id},
				this.countMethodMapper);
		
		
	}

}

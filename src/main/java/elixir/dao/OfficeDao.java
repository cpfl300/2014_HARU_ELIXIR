package elixir.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import elixir.model.Office;

@Repository
public class OfficeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Office> officeMapper = (rs, rowNum) -> {
		Office office = new Office();
		
		office.setId(rs.getInt("id"));
		office.setOfficeId(rs.getString("office_id"));
		office.setOfficeName(rs.getString("office_name"));
		office.setSectionName(rs.getString("section_name"));
		
		return office;
	};

	public Office findByOfficeId(String officeId) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT offices.id AS id, offices.office_id AS office_id, offices.name AS office_name, office_sections.name AS section_name " +
				"FROM offices INNER JOIN office_sections ON offices.office_sections_id = office_sections.id " +
				"WHERE offices.office_id = ?",
				new Object[]{officeId},
				this.officeMapper		
			);
	}

	public Office findByOfficeName(String officeName) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT offices.id AS id, offices.office_id AS office_id, offices.name AS office_name, office_sections.name AS section_name " +
				"FROM offices INNER JOIN office_sections ON offices.office_sections_id = office_sections.id " +
				"WHERE offices.name = ?",
				new Object[]{officeName},
				this.officeMapper		
			);
	}
	
	
	
}

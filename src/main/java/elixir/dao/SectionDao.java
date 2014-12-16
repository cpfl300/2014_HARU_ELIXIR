package elixir.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import elixir.model.Section;


@Repository
public class SectionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Section> sectionMapper = (rs, rowNum) -> {
		Section section = new Section();
		
		section.setId(rs.getInt("id"));
		section.setSectionId(rs.getString("section_id"));
		section.setSectionName(rs.getString("section_name"));
		
		return section;
		
	};


	public Section findBySectionId(String sectionId) {
		
		return this.jdbcTemplate.queryForObject(
					"SELECT id, section_id, name AS section_name FROM sections WHERE section_id = ?",
					new Object[]{sectionId},
					this.sectionMapper
				);
	}
	

	public Section findBySectionName(String sectionName) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT id, section_id, name AS section_name FROM sections WHERE name = ?",
				new Object[]{sectionName},
				this.sectionMapper
			);
	}
	
}

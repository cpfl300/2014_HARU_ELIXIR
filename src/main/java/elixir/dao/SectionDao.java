package elixir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
		section.setSuperId(rs.getInt("super_id"));
		
		return section;
		
	};

	
	// find
	public Section findById(int id) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT id, section_id, name AS section_name, super_sections_id AS super_id " +
				"FROM sections WHERE id = ?",
				new Object[]{id},
				this.sectionMapper
			);
	}
	
	
	public Section findBySectionId(String sectionId) {
		
		return this.jdbcTemplate.queryForObject(
					"SELECT id, section_id, name AS section_name, super_sections_id AS super_id " +
					"FROM sections WHERE section_id = ?",
					new Object[]{sectionId},
					this.sectionMapper
				);
	}
	

	public Section findBySectionName(String sectionName) {
		
		return this.jdbcTemplate.queryForObject(
				"SELECT id, section_id, name AS section_name, super_sections_id AS super_id " +
				"FROM sections WHERE name = ?",
				new Object[]{sectionName},
				this.sectionMapper
			);
	}

	// add
	public int add(Section section) {
		KeyHolder holder = new GeneratedKeyHolder();

		this.jdbcTemplate.update(
				new PreparedStatementCreator() {           

	                @Override
	                public PreparedStatement createPreparedStatement(Connection conn)
	                        throws SQLException {
	                    PreparedStatement ps = conn.prepareStatement(
	                    		"INSERT INTO sections (section_id, name, super_sections_id) VALUES (?,?,?)",
	                    		Statement.RETURN_GENERATED_KEYS);
	                    ps.setString(1, section.getSectionId());
						ps.setString(2, section.getSectionName());
						ps.setInt(3, section.getSuperId());
	                    
	                    return ps;
	                }
	                
	            }, holder);

		return holder.getKey().intValue();

	}
	
	public int[] addAll(List<Section> sections) {
		
		return this.jdbcTemplate.batchUpdate(
				"INSERT INTO sections (section_id, name, super_sections_id) VALUES (?,?,?)",
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Section section = sections.get(i);
						
						ps.setString(1, section.getSectionId());
						ps.setString(2, section.getSectionName());
						ps.setInt(3, section.getSuperId());
						
					}

					@Override
					public int getBatchSize() {
						
						return sections.size();
					}
					
				});
	}


	
	// delete
	public void deleteAll() {
		this.jdbcTemplate.update("DELETE FROM sections");
	}

	
	// size
	public int size() {
		
		return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM sections");
	}
	
}

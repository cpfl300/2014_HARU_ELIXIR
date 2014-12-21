package elixir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elixir.dao.SectionDao;
import elixir.model.Section;

@Service
public class SectionService {
	
	@Autowired
	private SectionDao sectionDao;

	public void addAll(List<Section> sections) {
		sectionDao.addAll(sections);
	}

}

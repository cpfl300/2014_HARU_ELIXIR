package elixir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elixir.dao.StatusDao;
import elixir.model.Status;

@Service
public class StatusService {
	
	@Autowired
	private StatusDao statusDao;

	public Status next() {
		// TODO Auto-generated method stub
		Status haruStatus = new Status("20141207", true);
		
		return haruStatus;
	}

	public void add(Status haruStatus) {
		
		statusDao.add(haruStatus);
		
	}

}

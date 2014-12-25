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
		Status status = new Status("20141207", true);
		
		return status;
	}

	public void add(Status haruStatus) {
		
		statusDao.add(haruStatus);
		
	}

	public Status getLastStatus() {
		
		Status status = statusDao.getLast();
		
		return status;
	}
	
	

}

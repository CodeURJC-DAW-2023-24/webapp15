package es.codeurjc.webapp15.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.TicketRepository;

@Service
public class TicketService {
    @Autowired
	private TicketRepository repository;

	public Optional<Ticket> findById(long id) {
		return repository.findById(id);
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Ticket> findAll() {
		return repository.findAll();
	}
	public Page<Ticket> findAllPage(PageRequest page) {
        return repository.findAll(page);
    }

	public void save(Ticket ticket) {
		repository.save(ticket);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

    public List<Ticket> findByUser(User user){
        return repository.findByUser(user);
    }
    public List<Concert> findByTicket(List<Ticket> ticket_list){
        return repository.findByTicket(ticket_list);
    }
	public Page<Ticket> findByUserId(Long id,PageRequest page){
        return repository.findByUserId(id,page);
    }
}
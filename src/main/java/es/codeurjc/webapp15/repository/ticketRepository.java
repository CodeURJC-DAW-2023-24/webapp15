package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;

import java.util.List;


public interface TicketRepository extends  JpaRepository<Ticket,Long>{

    List<Ticket> findByUser(User user);
}

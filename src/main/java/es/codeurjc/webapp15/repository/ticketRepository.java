package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Ticket;

public interface TicketRepository extends  JpaRepository<Ticket,Long>{
    
}

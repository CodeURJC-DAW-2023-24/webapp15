package es.codeurjc.webapp15.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    @Query("select t from Ticket t join fetch t.concert where t.user.id = :userId")
    Page<Ticket> findByUserId(@Param("userId") Long userId, PageRequest page);

    List<Ticket> findByUser(User user);

    @Query("SELECT t.concert FROM Ticket t WHERE t IN :ticket_list")
    List<Concert> findByTicket(List<Ticket> ticket_list);
}
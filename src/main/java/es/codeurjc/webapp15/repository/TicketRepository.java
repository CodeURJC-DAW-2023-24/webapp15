package es.codeurjc.webapp15.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.webapp15.model.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    @Query("select t from Ticket t join fetch t.concert where t.user.id = :userId")
    Page<Ticket> findByUserId(@Param("userId") Long userId, PageRequest page);
}
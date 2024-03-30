package es.codeurjc.webapp15.controller.restController;

import java.net.URI;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.ConcertService;
import es.codeurjc.webapp15.service.TicketService;
import es.codeurjc.webapp15.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    TicketService ticketService;

    @Autowired
    ConcertService concertService;
    
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<Object> getTickets(@RequestParam("page") int page, HttpServletRequest request) {

        String principal = request.getUserPrincipal().getName();
        Optional<User> user = userService.findByEmail(principal);
        Page<Ticket> tickets = ticketService.findByUserId(user.get().getId(), PageRequest.of(page, 6));

        if (tickets.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(tickets.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTicket(@PathVariable Long id) {

        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        ticket.get().getConcert(); // Ensure concert details are fetched
        ticket.get().getUser();
        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable long id) {
        Ticket ticket = ticketService.findById(id).get();
        if (ticket != null) {
            ticketService.delete(id);
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    @Operation(summary = "Create a new ticket")
    @ApiResponse(responseCode = "201", description = "Ticket created")
    @ApiResponse(responseCode = "400", description = "Ticket not created")
    @ApiResponse(responseCode = "403", description = "User not authorized")
    public ResponseEntity<Ticket> createTicket(@RequestParam("num_ticket") Integer num_ticket, @RequestParam("id_user") long id,@RequestParam("user") HttpServletRequest request) {
        
        Optional<Concert> concert = concertService.findById(id);
        if (concert.isPresent()){
            Integer left = concert.get().getNum_tickets();
            left = left - num_ticket;
            concert.get().setNum_tickets(left);
            concertService.save(concert.get());
            String principal = request.getUserPrincipal().getName();
            Optional<User> user = userService.findByEmail(principal);
            if (user.isPresent()) {
                Ticket ticket = new Ticket();
                ticket.setConcert(concert.get());
                ticket.setUser(user.get());
                ticket.setNum_ticket(num_ticket);
                ticketService.save(ticket);
                return ResponseEntity.created(URI.create("/api/concerts/" + ticket.getId())).body(ticket);
            }      
        }
            return ResponseEntity.notFound().build();
    }

    
}

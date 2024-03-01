package es.codeurjc.webapp15.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.ArtistRepository;
import es.codeurjc.webapp15.repository.ConcertRepository;
import es.codeurjc.webapp15.repository.UserRepository;
import es.codeurjc.webapp15.service.UserSession;
import es.codeurjc.webapp15.repository.TicketRepository;

@Controller
public class TicketController {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserSession session;

    @GetMapping("/payment/{id}")
    public String proccesPayment(Model model, @PathVariable long id){
        Optional<Concert> concert = concertRepository.findById(id);
        if (concert.isPresent()){
            model.addAttribute("concert", concert.get());
        }
        
        return "payment";
    }

    @PostMapping("/payment/{id}")
    public String paymentComplete(Model model, @RequestParam Integer num_ticket, @PathVariable long id){
        Optional<Concert> concert = concertRepository.findById(id);
        if (concert.isPresent()){
            Integer left = concert.get().getNum_tickets();
            left = left - num_ticket;
            concert.get().setNum_tickets(left);
            concertRepository.save(concert.get());
            User user = session.getUser();
            Ticket ticket = new Ticket();
            ticket.setConcert(concert.get());
            ticket.setUser(user);
            ticket.setNum_ticket(num_ticket);
            ticketRepository.save(ticket);            
        }
        return "redirect:/";
    }
}

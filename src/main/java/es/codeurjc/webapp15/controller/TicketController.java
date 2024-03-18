package es.codeurjc.webapp15.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.ConcertRepository;
import jakarta.servlet.http.HttpServletRequest;
import es.codeurjc.webapp15.repository.TicketRepository;
import es.codeurjc.webapp15.repository.UserRepository;

@Controller
public class TicketController {

    @ModelAttribute("user")
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("user", request.isUserInRole("USER"));
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
        } else {
            model.addAttribute("logged", false);
        }
    }

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/payment/{id}")
    public String processPayment(Model model, @PathVariable long id){
        Optional<Concert> concert = concertRepository.findById(id);
        if (concert.isPresent()){
            model.addAttribute("concert", concert.get());
        }
        
        return "payment";
    }

    @PostMapping("/payment/{id}")
    public String paymentComplete(Model model, @RequestParam Integer num_ticket, @PathVariable long id, HttpServletRequest request){

        Optional<Concert> concert = concertRepository.findById(id);
        if (concert.isPresent()){
            Integer left = concert.get().getNum_tickets();
            left = left - num_ticket;
            concert.get().setNum_tickets(left);
            concertRepository.save(concert.get()); //aqui no se como guardar el concierto 
            String principal = request.getUserPrincipal().getName();
            User user = userRepository.findByEmail(principal).orElseThrow();
            Ticket ticket = new Ticket();
            ticket.setConcert(concert.get());
            ticket.setUser(user);
            ticket.setNum_ticket(num_ticket);
            ticketRepository.save(ticket);            
        }
        return "redirect:/";
    }
}

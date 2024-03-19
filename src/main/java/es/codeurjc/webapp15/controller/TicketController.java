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
import es.codeurjc.webapp15.service.ConcertService;
import es.codeurjc.webapp15.service.TicketService;
import es.codeurjc.webapp15.service.UserService;
import es.codeurjc.webapp15.service.UserSession;

import jakarta.servlet.http.HttpServletRequest;

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
    private ConcertService concertService;

    @Autowired
    private TicketService TicketService;

    @Autowired
    private UserService userService;

    @GetMapping("/payment/{id}")
    public String processPayment(Model model, @PathVariable long id){
        Optional<Concert> concert = concertService.findById(id);
        if (concert.isPresent()){
            model.addAttribute("concert", concert.get());
        }
        
        return "payment";
    }

    @PostMapping("/payment/{id}")
    public String paymentComplete(Model model, @RequestParam Integer num_ticket, @PathVariable long id, HttpServletRequest request){

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
                TicketService.save(ticket);
            }      
        }
        return "redirect:/";
    }
}

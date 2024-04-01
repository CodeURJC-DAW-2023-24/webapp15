package es.codeurjc.webapp15.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.ArtistService;
import es.codeurjc.webapp15.service.ConcertService;
import es.codeurjc.webapp15.service.TicketService;
import es.codeurjc.webapp15.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            if (user.isPresent()){
                if (user.get().isRole("USER")){
                    model.addAttribute("user", true);
                }
                if (user.get().isRole("ADMIN")){
                    model.addAttribute("admin", true);
                }
            }
            model.addAttribute("logged", true);
            model.addAttribute("user", principal.getName());
        } else {
            model.addAttribute("logged", false);
        }
    }

    private final int pageSize = 4;
    
    @GetMapping("/")
    public String indexController(Model model, HttpServletRequest request) {

        Page<Artist> artistList = artistService.findAllPage(PageRequest.of(0, 10));

        Artist mainArtist = artistList.getContent().getFirst();
        List<Artist> secondaryArtists = artistList.getContent().subList(0, 4);
        List<Artist> recommendedArtists;

        Principal principal = request.getUserPrincipal();

        if(principal != null) {
            Optional<User> user = userService.findByEmail(principal.getName());
            recommendedArtists = getRecomendArtists(user.get());
            model.addAttribute("user", user);
            if (recommendedArtists.isEmpty()){
                recommendedArtists = artistList.getContent().subList(0, 4);
            }
        } else {
            recommendedArtists = artistList.getContent().subList(0, 4);
            model.addAttribute("logged", false);
        }
      
        model.addAttribute("mainArtist", mainArtist);
        model.addAttribute("secondaryArtists", secondaryArtists);
        model.addAttribute("recommendedArtists", recommendedArtists);

        return "index";
    }

    
    @GetMapping("/more-artists")
    public String moreArtists(Model model, @RequestParam("page") int page) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Artist> pageQuery = artistService.findAllPage(pageable);
        Logger.getAnonymousLogger().info(pageQuery.getContent().get(0).getName());
            
        model.addAttribute("artists", pageQuery.getContent());

        boolean hasNext = artistService.findAllPage(PageRequest.of(page+1, pageSize)).hasContent();
        model.addAttribute("hasNext", hasNext);
            
        return "artist-list";
    }

    
    public List<Artist> getRecomendArtists(User user){

        List<Ticket> ticket_list = ticketService.findByUser(user);
        List<Concert> concert_list = ticketService.findByTicket(ticket_list);
        List<Artist> artist_list = concertService.findByConcert(concert_list);
        return artist_list;
    }

    @GetMapping("/amount-of-concerts-by-month")
    public ResponseEntity<Object> concertsPerMonth(@RequestParam Optional<Long> months) {

        if (months.isEmpty())
            months = Optional.of(Long.valueOf(6));
        else if (months.get() > 24){
            months = Optional.of(Long.valueOf(24));
        }

        return ResponseEntity.ok(concertService.countConcertsByMonthInRange(months.get()));

    }
}
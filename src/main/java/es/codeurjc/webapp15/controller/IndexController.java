package es.codeurjc.webapp15.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import es.codeurjc.webapp15.repository.ArtistRepository;
import es.codeurjc.webapp15.repository.TicketRepository;
import es.codeurjc.webapp15.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import es.codeurjc.webapp15.repository.ConcertRepository;
import es.codeurjc.webapp15.repository.TicketRepository;
import es.codeurjc.webapp15.service.ConcertService;
import es.codeurjc.webapp15.service.UserSession;


@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artists;

    @Autowired
    private ConcertRepository concerts;

    @Autowired
    private TicketRepository tickets;

    @ModelAttribute("user")
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional <User> user = userRepository.findByEmail(principal.getName());
            if (user.isPresent()){
                if (user.get().isRole("USER")){
                    model.addAttribute("user", true);
                }
                if (user.get().isRole("ADMIN")){
                    model.addAttribute("admin", true);
                }
            }
            model.addAttribute("logged", true);
            model.addAttribute("user", principal.getName()); //aqui coge el email
        } else {
            model.addAttribute("logged", false);
        }
    }
    @Autowired
    private UserSession session;

    @Autowired
    private ConcertService concertService;

    private final int pageSize = 4;
    
    @GetMapping("/")
    public String indexController(Model model, HttpServletRequest request) {

        Page<Artist> artistList = artists.findAll(PageRequest.of(0, 10));

        Artist mainArtist = artistList.getContent().getFirst();
        List<Artist> secondaryArtists = artistList.getContent().subList(0, 4);
        List<Artist> recommendedArtists;

        Principal principal = request.getUserPrincipal();

        if(principal != null) {
            Optional<User> user = userRepository.findByEmail(principal.getName());
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

    
    @GetMapping("/moreArtists")
    public ResponseEntity<Object> moreArtists(@RequestParam("page") int page) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Artist> pageQuery = artists.findAll(pageable);

        if (pageQuery.hasContent()) {

            Map<String, Object> map = new HashMap<>();
             
            map.put("content", htmlBuilder(pageQuery.getContent()));

            boolean hasNext = artists.findAll(PageRequest.of(page+1, pageSize)).hasContent();
            map.put("hasNext", hasNext);
                
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.noContent().build();
    }
        
    private String htmlBuilder(List<Artist> artistList) {
        StringBuilder htmlBuilder = new StringBuilder();
        for (Artist artist : artistList) {
            htmlBuilder.append("<li class=\"Artist\">");
            htmlBuilder.append("<a href=\"" + artist.getURI() + "\" class=\"link1\">");
            htmlBuilder.append("<div class=\"infoArtist\">");
            htmlBuilder.append("<img src=\"" + artist.getImageFile() + "\">");
            htmlBuilder.append("</div>");
            htmlBuilder.append("<h4 class=\"text1Artist\">" + artist.getInfo() + "</h4>");
            htmlBuilder.append("<h3 class=\"name1Artist\">" + artist.getName() + "</h3>");
            htmlBuilder.append("</a>") ;  
            htmlBuilder.append("</li>");
        }

        return htmlBuilder.toString();
    }

    // TODO Do it with a SQL query
    public List<Artist> getRecomendArtists(User user){
        List<Ticket> ticket_list = tickets.findByUser(user);
        List<Concert> concert_list = tickets.findByTicket(ticket_list);
        List<Artist> artist_list = concerts.findByConcert(concert_list);
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
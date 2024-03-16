package es.codeurjc.webapp15.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artists;

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
    public ResponseEntity<String> moreArtists(@RequestParam("existingCount") int existingCount) {
        
        if((existingCount+1) < artists.findAll().size()){
            int pageSize = 4; 
            int pageNumber = existingCount / pageSize;
            int offset = (pageNumber * pageSize) + 1; // First id element array

            List<Artist> allArtists = artists.findAll();

            List<Artist> moreArtists = null;
            if(offset+pageSize <= allArtists.size()){
                moreArtists = allArtists.subList(offset, offset+pageSize);
            }
            if(offset+pageSize > allArtists.size()){
                moreArtists = allArtists.subList(offset, allArtists.size());
            }
            
            String html = "";
            for (Artist artist : moreArtists) { //aqui no se que pasa
                html += "<li class=\"Artist\">";
                html += "<a href=\"" + artist.getURI() + "\" class=\"link1\">";
                html += "<div class=\"infoArtist\">";
                html += "<img src=\"" + artist.getImageFile() + "\">";
                html += "</div>";
                html += "<h4 class=\"text1Artist\">" + artist.getInfo() + "</h4>";
                html += "<h3 class=\"name1Artist\">" + artist.getName() + "</h3>";
                html += "</a>";
                html += "</li>";
            }

            return ResponseEntity.ok(html);
        }
        return ResponseEntity.noContent().build();
    } 

    public List<Artist> getRecomendArtists(User user){
        List<Ticket> ticket_list = tickets.findByUser(user);
        Set<Artist> artist_set = new HashSet<>();
        for (Ticket ticket : ticket_list) {
            Concert concert = ticket.getConcert();
            Artist artist = concert.getArtist();
            artist_set.add(artist);
        }
        List<Artist> artist_list;
        artist_list = List.copyOf(artist_set);
        return artist_list;
    }
}
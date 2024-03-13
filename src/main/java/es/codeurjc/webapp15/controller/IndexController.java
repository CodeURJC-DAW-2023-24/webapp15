package es.codeurjc.webapp15.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.ArtistService;
import es.codeurjc.webapp15.service.ConcertService;
import es.codeurjc.webapp15.service.TicketService;
import es.codeurjc.webapp15.service.UserSession;


@Controller
public class IndexController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private UserSession session;

    @Autowired
    private ConcertService concertService;

    private final int pageSize = 4;
    
    @GetMapping("/")
    public String indexController(Model model) {

        Page<Artist> artistList = artistService.findAllPage(PageRequest.of(0, 10));

        Artist mainArtist = artistList.getContent().getFirst();
        List<Artist> secondaryArtists = artistList.getContent().subList(0, 4);
        List<Artist> recommendedArtists;

        User user = session.getUser();
        if (user != null){
            recommendedArtists = getRecomendArtists(user);
            if (recommendedArtists.isEmpty()){
                recommendedArtists = artistList.getContent().subList(0, 4);
            }
        } else {
            recommendedArtists = artistList.getContent().subList(0, 4);
        }

        model.addAttribute("mainArtist", mainArtist);
        model.addAttribute("secondaryArtists", secondaryArtists);
        model.addAttribute("recommendedArtists", recommendedArtists);

        return "index";
    }

    
    @GetMapping("/moreArtists")
    public ResponseEntity<Object> moreArtists(@RequestParam("page") int page) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Artist> pageQuery = artistService.findAllPage(pageable);

        if (pageQuery.hasContent()) {

            Map<String, Object> map = new HashMap<>();
             
            map.put("content", htmlBuilder(pageQuery.getContent()));

            boolean hasNext = artistService.findAllPage(PageRequest.of(page+1, pageSize)).hasContent();
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
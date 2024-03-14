package es.codeurjc.webapp15.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.repository.ConcertRepository;
import es.codeurjc.webapp15.service.ConcertService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SearchController {

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
    private ConcertRepository concerts;

    @Autowired
	private ConcertService concertService;

    private final int pageSize = 6;
    
    @GetMapping("/search")
    public String searchController(Model model) {
        Page<Concert> concert = concerts.findAll(PageRequest.of(0, 6,Sort.by("datetime")));
        List<Concert> concertList = concert.getContent().subList(0,6);

        model.addAttribute("concerts", concertList);
        return "search";
    }
    @DeleteMapping("/search/{id}")
    public ResponseEntity<String> deleteConcert(@PathVariable Long id) {
        concertService.delete(id);
        return new ResponseEntity<>("Concierto eliminado correctamente", HttpStatus.OK);
    }

    @GetMapping("/concert-list-data")
    public ResponseEntity<Object> getConcertListData() {
        Map<String, Object> map = new HashMap<>();

        List<String> locations = concerts.findLocations();
        map.put("locations", locations);

        List<String> artists = concerts.findArtists();
        map.put("artists", artists);

        return ResponseEntity.ok(map);
    }


    @GetMapping("/get-concerts")
    public ResponseEntity<Object> getConcerts(@RequestParam("locations") String[] locations,
                                              @RequestParam("artists") String[] artists,
                                              @RequestParam("page") int page) {

        List<String> locationList = formatJSONArrayToList(locations);
        List<String> artistList = formatJSONArrayToList(artists);

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Concert> pageQuery = concertService.findConcerts(pageable, locationList, artistList, null, null, null, null);
        if (pageQuery.hasContent()) {

            Map<String, Object> map = new HashMap<>();
             
            map.put("content", htmlBuilder(pageQuery.getContent()));

            boolean hasNext = concertService.findConcerts(PageRequest.of(page+1, pageSize), locationList, artistList, null, null, null, null).hasContent();
            map.put("hasNext", hasNext);
                
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.noContent().build();
    }


    private List<String> formatJSONArrayToList(String[] arr) {

        List<String> list = new ArrayList<>(Arrays.asList(arr));
        for (int i = 0; i < list.size(); i++) {
            String e = list.get(i);
            e = e.replace("[", "");
            e = e.replace("]", "");
            e = e.replace("\"", "");
            if (e.isEmpty()) {
                list.remove(i);
                i--;
                continue;
            }
            list.set(i, e);
        }

        return list;
    }

    private String htmlBuilder (List<Concert> concertList) {
        StringBuilder htmlBuilder = new StringBuilder();

        for (Concert concert : concertList) {
            htmlBuilder.append("<article class=\"event-article\">");
            htmlBuilder.append("<time>");
            htmlBuilder.append("<span class=\"day\">" + concert.getDay() + "</span>");
            htmlBuilder.append("<span class=\"month\">" + concert.getMonth() + "</span>");
            htmlBuilder.append("</time>");
            htmlBuilder.append("<div class=\"event-info\">");
            htmlBuilder.append("<h1><a>" + concert.getArtist().getName() + "</a></h1>");
            htmlBuilder.append("<p class=\"date-info\">");
            htmlBuilder.append("<span class=\"weekday\">" + concert.getWeekday() + "</span>");
            htmlBuilder.append("<span> - </span>");
            htmlBuilder.append("<span class=\"hour\">" + concert.getHour() + "</span>");
            htmlBuilder.append("</p>");
            htmlBuilder.append("<p class=\"venue-info\">");
            htmlBuilder.append("<span class=\"city\">" + concert.getPlace() + "</span>");
            htmlBuilder.append("</p>");
            htmlBuilder.append("</div>");
            htmlBuilder.append("<button onclick=\"location.href =\'/payment/" + concert.getId().toString() + "\'\">");
            htmlBuilder.append("<span>Entradas</span>");
            htmlBuilder.append("<img src=\"/image/point-right.png\" width=\"19px\">");
            htmlBuilder.append("</button>");
            /*if (globalControllerAdvice.globalAdminModel(model, request)) { //esto no vale porque se ha cambiado el model admin
                
                htmlBuilder.append("<button class=\"delete-btn\" data-id=\"" + concert.getId() + "\">");
                htmlBuilder.append("<span>Eliminar</span>");
                htmlBuilder.append("<img src=\"image/point-right.png\" width=\"19px\">");
                htmlBuilder.append("</button>");
           
            } */
            htmlBuilder.append("</article>");
        }

        return htmlBuilder.toString();
    }
}
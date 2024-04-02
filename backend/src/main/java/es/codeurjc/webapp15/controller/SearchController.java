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
	private ConcertService concertService;

    private final int pageSize = 6;
    
    @GetMapping("/search")
    public String searchController(Model model) {
        Page<Concert> concert = concertService.findAllPage(PageRequest.of(0, 6,Sort.by("datetime")));
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

        List<String> locations = concertService.findLocations();
        map.put("locations", locations);

        List<String> artists = concertService.findArtists();
        map.put("artists", artists);

        return ResponseEntity.ok(map);
    }


    @GetMapping("/get-concerts")
    public String getConcerts(Model model, HttpServletRequest request,
                                @RequestParam("locations") String[] locations,
                                @RequestParam("artists") String[] artists,
                                @RequestParam("page") int page) {

        List<String> locationList = formatJSONArrayToList(locations);
        List<String> artistList = formatJSONArrayToList(artists);

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Concert> pageQuery = concertService.findConcerts(pageable, locationList, artistList, null, null, null, null);

        model.addAttribute("concerts", pageQuery.getContent());

        boolean hasNext = concertService.findConcerts(PageRequest.of(page+1, pageSize), locationList, artistList, null, null, null, null).hasContent();
        model.addAttribute("hasNext", hasNext);

        model.addAttribute("admin", request.isUserInRole("ADMIN"));
            
        return "concert-list";
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
}
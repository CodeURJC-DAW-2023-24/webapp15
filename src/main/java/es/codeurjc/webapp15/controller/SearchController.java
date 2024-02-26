package es.codeurjc.webapp15.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.repository.ConcertRepository;
import es.codeurjc.webapp15.service.ConcertService;


@Controller
public class SearchController {

    @Autowired
    private ConcertRepository concerts;

    @Autowired
	private ConcertService concertService;
    
    @GetMapping("/search")
    public String searchController(Model model) {

        List<Concert> concertList = concerts.findAll(Sort.by("datetime"));

        model.addAttribute("concerts", concertList);
        return "search";
    }
    @DeleteMapping("/search/{id}")
    public ResponseEntity<String> deleteConcert(@PathVariable Long id) {
        concertService.delete(id);
        return new ResponseEntity<>("Concierto eliminado correctamente", HttpStatus.OK);
    }
}

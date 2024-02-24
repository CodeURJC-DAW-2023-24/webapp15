package es.codeurjc.webapp15.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.repository.ConcertRepository;


@Controller
public class SearchController {

    @Autowired
    private ConcertRepository concerts;
    
    @GetMapping("/search")
    public String searchController(Model model) {

        List<Concert> concertList = concerts.findAll(Sort.by("datetime"));

        model.addAttribute("concerts", concertList);
        return "search";
    }
    
}

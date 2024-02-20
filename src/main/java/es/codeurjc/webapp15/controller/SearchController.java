package es.codeurjc.webapp15.controller;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;


@Controller
public class SearchController {
    
    @GetMapping("/search")
    public String searchController(Model model) {

        model.addAttribute("concerts", sampleConcertList);
        return "search";
    }

    private List<Concert> sampleConcertList = new ArrayList<>() {{
        add(new Concert(Long.valueOf(123), LocalDateTime.now() ,"Vallecas", "aaaaa", Long.valueOf(999), Integer.valueOf(1000), Float.valueOf(40.5f), "aaaaaaa", Genre.Pop));
        add(new Concert(Long.valueOf(23), LocalDateTime.now() ,"Madrid", "aaaaa", Long.valueOf(823), Integer.valueOf(500), Float.valueOf(30), "bbbbbb", Genre.Pop));
        add(new Concert(Long.valueOf(543), LocalDateTime.now() ,"Barcelona", "aaaaa", Long.valueOf(543), Integer.valueOf(1000), Float.valueOf(10), "ccccccccc", Genre.Rock));
        add(new Concert(Long.valueOf(2134), LocalDateTime.now() ,"Villaverde", "aaaaa", Long.valueOf(32), Integer.valueOf(100), Float.valueOf(90), "dddddddd", Genre.Rock));
        add(new Concert(Long.valueOf(6788), LocalDateTime.now() ,"Usera", "aaaaa", Long.valueOf(8768), Integer.valueOf(20000), Float.valueOf(12), "eeeeeeeee", Genre.Techno));
        add(new Concert(Long.valueOf(689), LocalDateTime.now() ,"Carabanchel", "aaaaa", Long.valueOf(2342), Integer.valueOf(1000), Float.valueOf(87), "ffffffff", Genre.Techno));
        add(new Concert(Long.valueOf(923), LocalDateTime.now() ,"Leganés", "aaaaa", Long.valueOf(9328), Integer.valueOf(2000), Float.valueOf(56), "gggggggggg", Genre.Pop));
        add(new Concert(Long.valueOf(145), LocalDateTime.now() ,"Móstoles", "aaaaa", Long.valueOf(111), Integer.valueOf(900), Float.valueOf(23), "hhhhhhhhhh", Genre.Rock));
        add(new Concert(Long.valueOf(4567), LocalDateTime.now() ,"Fuenlabrada", "aaaaa", Long.valueOf(222), Integer.valueOf(280), Float.valueOf(55), "iiiiiiiii", Genre.Techno));
        add(new Concert(Long.valueOf(120), LocalDateTime.now() ,"Getafe", "aaaaa", Long.valueOf(235), Integer.valueOf(230), Float.valueOf(10), "jjjjjjjjjjj", Genre.Pop));
        add(new Concert(Long.valueOf(92123), LocalDateTime.now() ,"Alcorcón", "aaaaa", Long.valueOf(432432), Integer.valueOf(110), Float.valueOf(80), "kkkkkkkk", Genre.Rock));
        add(new Concert(Long.valueOf(87), LocalDateTime.now() ,"Humanes", "aaaaa", Long.valueOf(9999), Integer.valueOf(100000), Float.valueOf(100), "lllllllll", Genre.Techno));
    }};
    
}

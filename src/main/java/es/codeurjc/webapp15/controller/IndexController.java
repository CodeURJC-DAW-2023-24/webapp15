package es.codeurjc.webapp15.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;


@Controller
public class IndexController {
    
    @GetMapping("/")
    public String indexController(Model model) {

        model.addAttribute("artist", sampleList);
        model.addAttribute("suggestions", sampleList2);
        return "index";
    }

    @GetMapping("/registro")
    public String registro(Model model) {

        return "registro";
    }

    private List<Artist> sampleList = new ArrayList<>() {{
        add(new Artist(Long.valueOf(239187), "name1", "dsaoijsdaoifdjosdfhfsdiu", false, sampleConcertList));
    }};

    private List<Artist> sampleList2 = new ArrayList<>() {{
        add(new Artist(Long.valueOf(242339187), "name3", "ifrhoweriuhoifhu", false, sampleConcertList));
        add(new Artist(Long.valueOf(32874921), "name4", "sdsaihsdfidsuhah", false, sampleConcertList));
    }};

    private List<Concert> sampleConcertList = new ArrayList<>() {{
        add(new Concert(Long.valueOf(123), LocalDateTime.now(),"Vallecas", Integer.valueOf(1000), Float.valueOf(40.5f), "aaaaaaa", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(23), LocalDateTime.now() ,"Madrid", Integer.valueOf(500), Float.valueOf(30), "bbbbbb", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(543), LocalDateTime.now() ,"Barcelona", Integer.valueOf(1000), Float.valueOf(10), "ccccccccc", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(2134), LocalDateTime.now() ,"Villaverde", Integer.valueOf(100), Float.valueOf(90), "dddddddd", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(6788), LocalDateTime.now() ,"Usera", Integer.valueOf(20000), Float.valueOf(12), "eeeeeeeee", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(689), LocalDateTime.now() ,"Carabanchel",Integer.valueOf(1000), Float.valueOf(87), "ffffffff", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(923), LocalDateTime.now() ,"Leganés", Integer.valueOf(2000), Float.valueOf(56), "gggggggggg", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(145), LocalDateTime.now() ,"Móstoles", Integer.valueOf(900), Float.valueOf(23), "hhhhhhhhhh", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(4567), LocalDateTime.now() ,"Fuenlabrada", Integer.valueOf(280), Float.valueOf(55), "iiiiiiiii", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(120), LocalDateTime.now() ,"Getafe", Integer.valueOf(230), Float.valueOf(10), "jjjjjjjjjjj", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(92123), LocalDateTime.now() ,"Alcorcón", Integer.valueOf(110), Float.valueOf(80), "kkkkkkkk", new Artist(), new Genre()));
        add(new Concert(Long.valueOf(87), LocalDateTime.now() ,"Humanes", Integer.valueOf(100000), Float.valueOf(100), "lllllllll", new Artist(), new Genre()));
    }};
    
}
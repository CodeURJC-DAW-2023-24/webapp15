package es.codeurjc.webapp15.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ArtistController {

    @GetMapping("/artist/{artistName}")
    public String artistController (Model model, @PathVariable String artistName) {

        Artist artist = sampleArtistMap.get(artistName);
        if (artist == null) {
            return "error";
        }

        model.addAttribute("artist", artist);
        model.addAttribute("concerts2", artist.getConcerts());
        return "info_artist";
    }

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

    private Map<String, Artist> sampleArtistMap = new HashMap<>() {{
        put("shakira", new Artist(Long.valueOf(1), "Shakira", "ABCDEFGHIJK", false, sampleConcertList));
        put("taylor-swift", new Artist(Long.valueOf(2), "Taylor Swift", "AAAAAAAAAAAAAAAAAAAAAAAAA", false, sampleConcertList));
        put("kayne-west", new Artist(Long.valueOf(3), "Kayne West", "BBBBBBBBBBBBBBBBBBBB", false, sampleConcertList));
    }};
}
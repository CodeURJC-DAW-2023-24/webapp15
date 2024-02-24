package es.codeurjc.webapp15.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;
import es.codeurjc.webapp15.repository.ArtistRepository;
import es.codeurjc.webapp15.repository.ConcertRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class ArtistController {

    @Autowired
    private ArtistRepository artists;
    @Autowired
    private ConcertRepository concerts;  

    @GetMapping("/artist/{artistName}")
    public String artistController (Model model, @PathVariable String artistName) {

        artistName = artistName.toLowerCase().replace('-', ' ');
        List<Artist> artistsList = artists.findByNameIgnoreCase(artistName);

        if (artistsList.size() == 0) {
            return "error";
        }

        Artist artist = artistsList.getFirst();
        List<Concert> concertList = concerts.findByArtistName(artist.getName());
        model.addAttribute("artist", artist);
        model.addAttribute("concerts", concertList);
        return "info_artist";
    }

}
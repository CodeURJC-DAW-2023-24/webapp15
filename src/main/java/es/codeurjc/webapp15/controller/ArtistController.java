package es.codeurjc.webapp15.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.repository.ArtistRepository;
import es.codeurjc.webapp15.repository.ConcertRepository;

import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class ArtistController {

    @Autowired
    private ArtistRepository artists;
    @Autowired
    private ConcertRepository concerts;  

    @GetMapping("/artist/{artistName}")
    public String artistController (Model model, @PathVariable String artistName) {

        artistName = artistName.toLowerCase().replace('-', ' ');
        Artist artist = artists.findFirstByNameIgnoreCase(artistName);

        if (artist == null) {
            return "error";
        }

        Page<Concert> concertList = concerts.findByArtistName(artist.getName(), PageRequest.of(0, 10));
        model.addAttribute("artist", artist);
        model.addAttribute("concerts", concertList.getContent());
        return "info_artist";
    }

}
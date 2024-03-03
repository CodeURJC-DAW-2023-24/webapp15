package es.codeurjc.webapp15.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
        return "info-artist";
    }

    @GetMapping("/artist/{artistName}/image")
    public ResponseEntity<Object> getArtistImage(@PathVariable String artistName) throws SQLException {
        
        artistName = artistName.toLowerCase().replace('-', ' ');
        Artist artist = artists.findFirstByNameIgnoreCase(artistName);

        if (artist == null)
            return ResponseEntity.notFound().build();

        Resource file = new InputStreamResource(artist.getImageFile().getBinaryStream());

        return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
        .contentLength(artist.getImageFile().length())
        .body(file);
    }
    

}
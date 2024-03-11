package es.codeurjc.webapp15.controller.restController;

import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.service.ArtistService;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;




@RestController
@RequestMapping("/api/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("")
    public ResponseEntity<Object> getArtists(@RequestParam int page, @RequestParam int size) {
        
        size = Math.min(size, 20);
        
        Page<Artist> concerts = artistService.findArtists(PageRequest.of(page, size));

        // Redirect to error page
        if (concerts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(concerts.getContent(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getArtist(@PathVariable int id) {
        
        Optional<Artist> artist = artistService.findById(id);

        if (artist.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(artist.get(), HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        try {
            artistService.save(artist);
            // Location doesn't return the expected value
            URI location = fromCurrentRequest().path("artists/{id}").buildAndExpand(artist.getId()).toUri();
            return ResponseEntity.created(location).body(artist);
        }
        catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    
    
    
    
}

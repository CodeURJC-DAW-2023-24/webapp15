package es.codeurjc.webapp15.controller.restController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.service.ConcertService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/concerts")
public class ConcertRestController {
    
    @Autowired
    private ConcertService concertService;

    @GetMapping("")
    public ResponseEntity<Object> getConcerts(@RequestParam int page, @RequestParam int size) {

        size = Math.min(size, 20);
        
        Page<Concert> concerts = concertService.findConcerts(PageRequest.of(page, size), null, null, null, null, null, null);

        // Redirect to error page
        if (concerts.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(concerts.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getConcert(@PathVariable int id) {
        
        Optional<Concert> concert = concertService.findById(id);

        if (concert.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(concert.get(), HttpStatus.OK);
    }
    
    
}

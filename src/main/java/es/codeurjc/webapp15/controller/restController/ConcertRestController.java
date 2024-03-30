package es.codeurjc.webapp15.controller.restController;

import java.io.IOException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import es.codeurjc.webapp15.model.Concert;

import es.codeurjc.webapp15.service.ConcertService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/concerts")
public class ConcertRestController {
    

    int pageSize = 6; 

    @Autowired
    private ConcertService concertService;

    // View search concerts
    @GetMapping("")
    @Operation(summary = "Search concerts")
    @ApiResponse(responseCode = "200", description = "Concerts retrieved")
    public ResponseEntity<List<Concert>> getConcerts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "locations", defaultValue = "") String[] locations,
                                                        @RequestParam(value = "artists", defaultValue = "") String[] artists) {
        List<String> locationList = formatJSONArrayToList(locations);
        List<String> artistList = formatJSONArrayToList(artists);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("datetime"));
        Page<Concert> concerts = concertService.findConcerts(pageable,locationList,artistList,null,null,null,null);
        if (concerts.isEmpty()){
            System.out.println(concerts);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        return new ResponseEntity<>(concerts.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getConcert(@PathVariable int id) {
        
        Optional<Concert> concert = concertService.findById(id);

        if (concert.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(concert.get(), HttpStatus.OK);
    }

    private List<String> formatJSONArrayToList(String[] arr) {
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        list.removeAll(Arrays.asList("", null));
        return list;
    }

    @PostMapping("")
    @Operation(summary = "Create a new concert")
    @ApiResponse(responseCode = "201", description = "Concert created")
    @ApiResponse(responseCode = "400", description = "Concert not created")
    @ApiResponse(responseCode = "403", description = "User not authorized")
    public ResponseEntity<Concert> createConcert(@RequestBody Concert concert) throws IOException, SQLException {
        if((concert.getArtist() == null) || (concert.getGenre()==null) || (concert.getDatetime()==null)|| (concert.getNum_tickets()==null)|| (concert.getPlace()==null)|| (concert.getPrice()==null)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        concertService.save(concert);
        return new ResponseEntity<>(concert,HttpStatus.OK);
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Concert> deleteConcert(@PathVariable long id) {
        Concert concert = concertService.findById(id).get();
        if (concert != null) {
            concertService.delete(id);
            return ResponseEntity.ok(concert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
package es.codeurjc.webapp15.controller.restController;

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
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/concerts")
public class ConcertRestController {
    

    int pageSize = 6; 

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
    
      // View search concerts
    @GetMapping("/get-concerts")
    @Operation(summary = "Search concerts")
    @ApiResponse(responseCode = "200", description = "Concerts retrieved")
    public ResponseEntity<List<Concert>> searchConcerts(@RequestParam(value = "page", defaultValue = "0") int page,
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

    private List<String> formatJSONArrayToList(String[] arr) {
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        list.removeAll(Arrays.asList("", null));
        return list;
    }
}

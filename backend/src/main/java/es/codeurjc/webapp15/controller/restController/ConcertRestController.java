package es.codeurjc.webapp15.controller.restController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.service.ArtistService;
import es.codeurjc.webapp15.service.AuthService;
import es.codeurjc.webapp15.service.ConcertService;
import es.codeurjc.webapp15.service.GenreService;
import es.codeurjc.webapp15.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

class NewConcert {
    public Long artistId;
    public String genre;
    public String info;
    public LocalDateTime datetime;
    public Integer num_tickets;
    public String place;
    public Float price;
}

@RestController
@RequestMapping("/api/concerts")
public class ConcertRestController {
    

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AuthService authService;

    // View search concerts
    @Operation(summary = "Get a page of concerts, sorted by datetime")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "Found the page",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Concert.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Invalid id supplied",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "404",
    description = "Page not found",
    content = @Content
    )
    })
    @GetMapping("")
    public ResponseEntity<Page<Concert>> getConcerts(@RequestParam(value = "page", defaultValue = "0") int page,@RequestParam(value = "size", defaultValue = "6") int size,
                                                        @RequestParam(value = "locations", defaultValue = "") String[] locations,
                                                        @RequestParam(value = "artists", defaultValue = "") String[] artists,
                                                        @RequestParam Optional<LocalDateTime> before,
                                                        @RequestParam Optional<LocalDateTime> after,
                                                        @RequestParam(value = "showPast") Optional<Boolean> showPastOpt,
                                                        @RequestParam Optional<Float> priceLowerThan,
                                                        @RequestParam Optional<Float> priceHigherThan,
                                                        HttpServletRequest request) {
        List<String> locationList = formatJSONArrayToList(locations);
        List<String> artistList = formatJSONArrayToList(artists);

        Pageable pageable = PageRequest.of(page, size, Sort.by("datetime"));

        boolean showPast = false;
        if (authService.sessionIsRole("ADMIN", request)) {
            showPast = showPastOpt.isPresent() ? showPastOpt.get() : false;
        }
        
        Page<Concert> concerts = concertService.findConcerts(pageable, locationList, artistList, before, after, showPast, priceLowerThan, priceHigherThan);
        if (concerts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        return new ResponseEntity<>(concerts, HttpStatus.OK);
    }

    @Operation(summary = "Get a concert by id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "Found the page",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Concert.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Invalid id supplied",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "404",
    description = "Concert not found",
    content = @Content
    )
    })
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
    @Operation(summary = "Create a concert")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "201",
    description = "Concert created corectly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Concert.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Concert not created",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "401",
    description = "User not identified",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    )
    })
    public ResponseEntity<Object> createConcert(@RequestBody NewConcert concertBody, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!user.get().isRole("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Logger.getAnonymousLogger().info(concertBody.place);
        Optional<Artist> artist = artistService.findById(concertBody.artistId);
        if (artist.isEmpty()) {
            return ResponseEntity.badRequest().body("Artist not found");
        }
        
        Optional<Genre> genre = genreService.findByGenreType(concertBody.genre);
        if (genre.isEmpty()) {
            return ResponseEntity.badRequest().body("Genre not found");
        }

        Concert newConcert = new Concert(concertBody.datetime, concertBody.place, concertBody.num_tickets, concertBody.price, concertBody.info, artist.get(), genre.get());

        concertService.save(newConcert);
        return ResponseEntity.ok().body(newConcert);
        
    }

    @Operation(summary = "Update a concert by its id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "Concert updated correctly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Concert.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Concert not updated",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "401",
    description = "User not authenticated",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateConcert(@PathVariable Long id, @RequestBody NewConcert concertBody, HttpServletRequest request) {

        if (!userService.exist(id)) {
            return ResponseEntity.notFound().build();
        }

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!user.get().isRole("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Logger.getAnonymousLogger().info(concertBody.place);
        Optional<Artist> artist = artistService.findById(concertBody.artistId);
        if (artist.isEmpty()) {
            return ResponseEntity.badRequest().body("Artist not found");
        }
        
        Optional<Genre> genre = genreService.findByGenreType(concertBody.genre);
        if (genre.isEmpty()) {
            return ResponseEntity.badRequest().body("Genre not found");
        }

        Concert newConcert = new Concert(concertBody.datetime, concertBody.place, concertBody.num_tickets, concertBody.price, concertBody.info, artist.get(), genre.get());
        newConcert.setId(id);

        concertService.save(newConcert);
        return ResponseEntity.ok().body(newConcert);
        
    }
    
    @Operation(summary = "Delete a concert by id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "Concert created corectly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Concert.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Concert not deleted",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "401",
    description = "User not authenticated",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Concert> deleteConcert(@PathVariable long id, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!user.get().isRole("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Concert> concert = concertService.findById(id);
        if (concert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        concertService.delete(id);
        return ResponseEntity.ok(concert.get());
    }
}
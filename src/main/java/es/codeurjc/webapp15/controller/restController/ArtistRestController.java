package es.codeurjc.webapp15.controller.restController;

import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.URI;
import java.sql.SQLException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    @Operation(summary = "Get a page of artist")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "Found the page",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Artist.class)
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
    public ResponseEntity<Object> getArtists(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        
        size = Math.min(size, 20);
        
        Page<Artist> concerts = artistService.findArtists(PageRequest.of(page, size));

        // Redirect to error page
        if (concerts.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Query couldn't find any item");
        
        return ResponseEntity.ok(concerts.getContent());

    }

    @Operation(summary = "Get an artist by its id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "Found the artist",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Artist.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Invalid id supplied",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "404",
    description = "Artist not found",
    content = @Content
    )
    })

    @GetMapping("/{id}")
    public ResponseEntity<Object> getArtist(@PathVariable int id) {
        
        Optional<Artist> artist = artistService.findById(id);

        if (artist.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return ResponseEntity.ok(artist.get());

    }
    @Operation(summary = "Create an artist")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "201",
    description = "Artist created correctly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Artist.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Artist not created",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    )
    })
    @PostMapping("")
    public ResponseEntity<Object> createArtist(@RequestBody Artist artist) {

        if((artist.getName()==null) || (artist.getInfo()==null)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Artist name or Artist info cannot be null");
        }
        else if(artistService.findFirstByNameIgnoreCase(artist.getName())!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Artist exists");
        }
        else{            
            artistService.save(artist);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(artist.getId()).toUri();
            return ResponseEntity.created(location).body(artist);
        }

    }

    @Operation(summary = "Update an artist by its id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "200",
    description = "Artist updated corectly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Artist.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Artist not updated",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Artist> putArtist(@PathVariable Long id, @RequestBody Artist updatedArtist) throws SQLException {

        if (artistService.exist(id)) {
            
            // Mantain current image
            Artist storedArtist = artistService.findById(id).get();
            if (storedArtist.getImageFile() != null) {
                updatedArtist.setImageFile(BlobProxy.generateProxy(storedArtist.getImageFile().getBinaryStream(), storedArtist.getImageFile().length()));
            }

            updatedArtist.setId(id);
            artistService.save(updatedArtist);

            return new ResponseEntity<>(updatedArtist, HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete an artist by its id")
    @ApiResponses(value = {
    @ApiResponse(
    responseCode = "204",
    description = "Artist deleted corectly",
    content = {@Content(
    mediaType = "application/json",
    schema = @Schema(implementation=Artist.class)
    )}
    ),
    @ApiResponse(
    responseCode = "400",
    description = "Artist not updated",
    content = @Content
    ),
    @ApiResponse(
    responseCode = "403",
    description = "User not authorized",
    content = @Content
    )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id) {

        try {
            artistService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

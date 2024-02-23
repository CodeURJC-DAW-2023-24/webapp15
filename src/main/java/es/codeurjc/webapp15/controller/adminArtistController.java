package es.codeurjc.webapp15.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.text.*;



import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;

import es.codeurjc.webapp15.service.ArtistService;



@Controller
public class adminArtistController {

    @Autowired
	private ArtistService artistService;

    @GetMapping("/createArtist")
	public String createArtistController(Model model) {

		return "createArtist";
	}
    
    @PostMapping("/createArtist")
	public String newArtist(Model model,  @RequestParam MultipartFile Image,String Name, String Info) throws IOException {


        Artist artist = new Artist();
		if (!Image.isEmpty()) {
			artist.setImageFile(BlobProxy.generateProxy(Image.getInputStream(), Image.getSize()));
	    	artist.setImage(true);
		}
       
        artist.setName(Name);
        artist.setInfo(Info);
		artistService.save(artist);
        
		return "redirect:/";
	}

    
    
}


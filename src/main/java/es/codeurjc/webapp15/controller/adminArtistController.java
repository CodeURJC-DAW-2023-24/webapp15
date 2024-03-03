package es.codeurjc.webapp15.controller;

import java.io.IOException;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.service.ArtistService;



@Controller
public class AdminArtistController {

    @Autowired
	private ArtistService artistService;

    @GetMapping("/create-artist")
	public String createArtistController(Model model) {

		return "create-artist";
	}
    
    @PostMapping("/create-artist")
	public String newArtist(Model model,  @RequestParam MultipartFile Image,String Name, String Info) throws IOException {


        Artist artist = new Artist();
		if (!Image.isEmpty()) {
			artist.setImageFile(BlobProxy.generateProxy(Image.getInputStream(), Image.getSize()));
	    	// artist.setImage(true);
		}
       
        artist.setName(Name);
        artist.setInfo(Info);
		artistService.save(artist);
        
		return "redirect:/";
	}

    
    
}


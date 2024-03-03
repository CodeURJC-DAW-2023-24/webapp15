package es.codeurjc.webapp15.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;

import es.codeurjc.webapp15.service.ArtistService;
import es.codeurjc.webapp15.service.GenreService;
import es.codeurjc.webapp15.service.ConcertService;

@Controller
public class AdminConcertController {
    

     @Autowired
	private ArtistService artistService;

    @Autowired
	private GenreService genreService;

    @Autowired
	private ConcertService concertService;



    @GetMapping("/createConcert")
	public String createConcertController(Model model) {
        
        model.addAttribute("artists", artistService.findAll());
        model.addAttribute("genres", genreService.findAll());

		return "createConcert";
	}

    @PostMapping("/createConcert")
	public String newConcert(Model model,@RequestParam String place,Long artist,String date,String time,Integer num_ticket,Float price,Long genre, String info) throws IOException {
		Concert concert = new Concert();
		concert.setPlace(place);
        Artist artist1 = artistService.findById(artist).get();
       
        concert.setArtist(artist1);
		String dateTimeString = date + "T" + time;
    
   
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		concert.setDatetime(dateTime);
		concert.setNum_tickets(num_ticket);
		concert.setPrice(price);
       
        Genre genre1 = genreService.findById(genre).get();
		concert.setGenre(genre1);
		concert.setInfo(info);

		concertService.save(concert);
        
		return "redirect:/";
	}

}


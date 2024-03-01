package es.codeurjc.webapp15.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;
import es.codeurjc.webapp15.repository.ArtistRepository;
import es.codeurjc.webapp15.repository.ConcertRepository;
import es.codeurjc.webapp15.repository.GenreRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DatabaseInitializer {
    
    @Autowired
    private ArtistRepository artists;

    @Autowired
    private GenreRepository genres;

    @Autowired
    private ConcertRepository concerts;

    @PostConstruct
    public void init() {

        // Sample artists

        Artist artist1 = new Artist("Shakira", "ABCDEFGHIJK", false);
        artists.save(artist1);

        Artist artist2 = new Artist("Taylor Swift", "AAAAAAAAAAAAAAAAAAAAAAAAA", false);
        artists.save(artist2)
        ;
        Artist artist3 = new Artist("Kayne West", "BBBBBBBBBBBBBBBBBBBB", false);
        artists.save(artist3);

        Artist artist4 = new Artist("Billie Eilish", "LLLLLLLLLLLLLLLLLLLLLLL", false);
        artists.save(artist4);

        Artist artist5 = new Artist("Quevedo", "quedate", false);
        artists.save(artist5)
        ;
        Artist artist6 = new Artist("Metallica", "BBBBBBBBBBBBBBBBBBBB", false);
        artists.save(artist6);

        Artist artist7 = new Artist("Adele", "sahisdvhisduhfsduh", false);
        artists.save(artist7);

        Artist artist8 = new Artist("Ariana Grande", "AAAAAAAAAAAAAAAAAAAAAAAAA", false);
        artists.save(artist8)
        ;
        Artist artist9 = new Artist("Beyoncé", "666666666666666666666", false);
        artists.save(artist9);

        // Sample genres

        Genre genre1 = new Genre("Pop");
        genres.save(genre1);

        Genre genre2 = new Genre("Rock");
        genres.save(genre2);


        // Sample concerts

        Concert concert1 = new Concert(LocalDateTime.of(2024, 03, 01, 19, 30, 00),"Palencia", Integer.valueOf(1000), Float.valueOf(40.5f), "aaaaaaa", artist1, genre1);
        concerts.save(concert1);

        Concert concert2 = new Concert(LocalDateTime.of(2024, 03, 12, 21, 15, 00),"Barcelona", Integer.valueOf(500), Float.valueOf(32f), "aaaaaaa", artist1, genre2);
        concerts.save(concert2);
        
        Concert concert3 = new Concert(LocalDateTime.of(2024, 03, 03, 20, 00, 00),"Madrid", Integer.valueOf(2000), Float.valueOf(82.5f), "aaaaaaa", artist2, genre1);
        concerts.save(concert3);
        
        Concert concert4 = new Concert(LocalDateTime.of(2024, 03, 04, 20, 30, 00),"Valencia", Integer.valueOf(5000), Float.valueOf(12.25f), "aaaaaaa", artist2, genre2);
        concerts.save(concert4);
        
        Concert concert5 = new Concert(LocalDateTime.of(2024, 03, 05, 21, 30, 00),"Bilbao", Integer.valueOf(50), Float.valueOf(4.9f), "aaaaaaa", artist3, genre1);
        concerts.save(concert5);
        
        Concert concert6 = new Concert(LocalDateTime.of(2024, 03, 06, 22, 00, 00),"Sevilla", Integer.valueOf(80000), Float.valueOf(120f), "aaaaaaa", artist3, genre2);
        concerts.save(concert6);

        Concert concert7 = new Concert(LocalDateTime.of(2024, 03, 06, 22, 00, 00),"Sevilla", Integer.valueOf(80000), Float.valueOf(120f), "aaaaaaa", artist3, genre2);
        concerts.save(concert7);

        Concert concert8 = new Concert(LocalDateTime.of(2024, 03, 06, 22, 00, 00),"Sevilla", Integer.valueOf(80000), Float.valueOf(120f), "aaaaaaa", artist3, genre2);
        concerts.save(concert8);

        Concert concert9 = new Concert(LocalDateTime.of(2024, 03, 06, 22, 00, 00),"Sevilla", Integer.valueOf(80000), Float.valueOf(120f), "aaaaaaa", artist3, genre2);
        concerts.save(concert9);

    }
}

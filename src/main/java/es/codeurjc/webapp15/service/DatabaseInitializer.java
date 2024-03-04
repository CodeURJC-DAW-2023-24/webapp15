package es.codeurjc.webapp15.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import es.codeurjc.webapp15.model.Genre;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.ArtistRepository;
import es.codeurjc.webapp15.repository.ConcertRepository;
import es.codeurjc.webapp15.repository.GenreRepository;
import es.codeurjc.webapp15.repository.TicketRepository;
import es.codeurjc.webapp15.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Service
public class DatabaseInitializer {
    
    @Autowired
    private ArtistRepository artists;

    @Autowired
    private GenreRepository genres;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ConcertRepository concerts;

    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "/src/main/resources/images/");


    @PostConstruct
    public void init() throws IOException {

        // Sample artists

        Artist artist1 = new Artist("Shakira", "ABCDEFGHIJK");
        Path imagePath = IMAGES_FOLDER.resolve("shakira.jpg");
        artist1.setImageFile(BlobProxy.generateProxy(new FileInputStream(imagePath.toFile()), 0));
        artists.save(artist1);

        Artist artist2 = new Artist("Taylor Swift", "AAAAAAAAAAAAAAAAAAAAAAAAA");
        Path imagePath2 = IMAGES_FOLDER.resolve("taylor_swift.jpg");
        artist2.setImageFile(BlobProxy.generateProxy(new FileInputStream(imagePath2.toFile()), 0));
        artists.save(artist2);

        Artist artist3 = new Artist("Kayne West", "BBBBBBBBBBBBBBBBBBBB");
        artists.save(artist3);

        Artist artist4 = new Artist("Billie Eilish", "LLLLLLLLLLLLLLLLLLLLLLL");
        artists.save(artist4);

        Artist artist5 = new Artist("Quevedo", "quedate");
        artists.save(artist5);

        Artist artist6 = new Artist("Metallica", "BBBBBBBBBBBBBBBBBBBB");
        artists.save(artist6);

        Artist artist7 = new Artist("Adele", "sahisdvhisduhfsduh");
        Path imagePath7 = IMAGES_FOLDER.resolve("adele.webp");
        artist7.setImageFile(BlobProxy.generateProxy(new FileInputStream(imagePath7.toFile()), 0));
        artists.save(artist7);

        Artist artist8 = new Artist("Ariana Grande", "AAAAAAAAAAAAAAAAAAAAAAAAA");
        Path imagePath8 = IMAGES_FOLDER.resolve("ArianaGrande.webp");
        artist8.setImageFile(BlobProxy.generateProxy(new FileInputStream(imagePath8.toFile()), 0));
        artists.save(artist8);

        Artist artist9 = new Artist("Beyoncé", "666666666666666666666");
        Path imagePath9 = IMAGES_FOLDER.resolve("beyonce.jpg");
        artist9.setImageFile(BlobProxy.generateProxy(new FileInputStream(imagePath9.toFile()), 0));
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


        // Users Example

        User admin = new User("admin", "admin", "ADMIN");
        admin.setEmail("admin@admin.com");
        usersRepository.save(admin);

        User user = new User("user", "user", "USER");
        user.setEmail("user@user.com");
        usersRepository.save(user);

        // Ticket Examples
        Ticket ticket1 = new Ticket();
        ticket1.setConcert(concert1);
        ticket1.setUser(user);
        ticket1.setNum_ticket(5);
        ticketRepository.save(ticket1);   

        Ticket ticket2 = new Ticket();
        ticket2.setConcert(concert2);
        ticket2.setUser(user);
        ticket2.setNum_ticket(3);
        ticketRepository.save(ticket2);   

        Ticket ticket3 = new Ticket();
        ticket3.setConcert(concert3);
        ticket3.setUser(user);
        ticket3.setNum_ticket(5);
        ticketRepository.save(ticket3);

        Ticket ticket4 = new Ticket();
        ticket4.setConcert(concert2);
        ticket4.setUser(user);
        ticket4.setNum_ticket(5);
        ticketRepository.save(ticket4);   
        
        Ticket ticket5 = new Ticket();
        ticket5.setConcert(concert1);
        ticket5.setUser(user);
        ticket5.setNum_ticket(2);
        ticketRepository.save(ticket5);

        Ticket ticket6 = new Ticket();
        ticket6.setConcert(concert1);
        ticket6.setUser(user);
        ticket6.setNum_ticket(2);
        ticketRepository.save(ticket6);
        
        Ticket ticket7 = new Ticket();
        ticket7.setConcert(concert1);
        ticket7.setUser(user);
        ticket7.setNum_ticket(2);
        ticketRepository.save(ticket7);   
    }
}
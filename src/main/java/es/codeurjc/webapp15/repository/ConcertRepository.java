package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.codeurjc.webapp15.model.Artist;
import es.codeurjc.webapp15.model.Concert;
import java.util.List;


public interface ConcertRepository extends JpaRepository<Concert,Long> {

    @Query("select c from Concert c where lower(c.artist.name) like lower(concat('%', ?1,'%')) order by c.datetime")
    List<Concert> findByArtistName(String name);
}


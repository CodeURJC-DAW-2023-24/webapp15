package es.codeurjc.webapp15.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.codeurjc.webapp15.model.Concert;


public interface ConcertRepository extends JpaRepository<Concert,Long> {

    @Query("SELECT c FROM Concert c WHERE lower(c.artist.name) like lower(concat('%', ?1,'%')) ORDER BY c.datetime")
    Page<Concert> findByArtistName(String name, Pageable page);

    @Query("SELECT DISTINCT c.place FROM Concert c")
    List<String> findLocations();

    @Query("SELECT DISTINCT c.artist.name FROM Concert c")
    List<String> findArtists();
}


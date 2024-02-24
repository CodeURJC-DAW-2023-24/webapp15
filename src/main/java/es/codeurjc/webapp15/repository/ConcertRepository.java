package es.codeurjc.webapp15.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.codeurjc.webapp15.model.Concert;


public interface ConcertRepository extends JpaRepository<Concert,Long> {

    @Query("select c from Concert c where lower(c.artist.name) like lower(concat('%', ?1,'%')) order by c.datetime")
    Page<Concert> findByArtistName(String name, Pageable page);
}


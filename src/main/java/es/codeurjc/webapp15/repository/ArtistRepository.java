package es.codeurjc.webapp15.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Artist;


public interface ArtistRepository extends JpaRepository<Artist,Long>{

    Artist findFirstByNameIgnoreCase(String name);

    Page<Artist> findAll(Pageable page);
}

package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Artist;


public interface ArtistRepository extends JpaRepository<Artist,Long>{

    Artist findFirstByNameIgnoreCase(String name);

}

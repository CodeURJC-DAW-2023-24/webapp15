package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Artist;
import java.util.List;


public interface ArtistRepository extends JpaRepository<Artist,Long>{

    List<Artist> findByNameIgnoreCase(String name);

}

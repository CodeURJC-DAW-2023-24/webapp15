package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Genre;

public interface genreRepository extends JpaRepository<Genre,Long>{
    
}

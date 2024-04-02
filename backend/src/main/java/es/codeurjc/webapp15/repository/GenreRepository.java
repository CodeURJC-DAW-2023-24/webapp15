package es.codeurjc.webapp15.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Genre;

public interface GenreRepository extends JpaRepository<Genre,Long>{
    Optional<Genre> findByGenreType(String type);
}

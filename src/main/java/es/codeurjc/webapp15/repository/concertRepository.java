package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.Concert;

public interface concertRepository extends JpaRepository<Concert,Long> {
    
}


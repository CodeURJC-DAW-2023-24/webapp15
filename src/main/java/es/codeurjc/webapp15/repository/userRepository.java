package es.codeurjc.webapp15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.codeurjc.webapp15.model.User;



public interface UserRepository extends JpaRepository<User,Long> {
    
}

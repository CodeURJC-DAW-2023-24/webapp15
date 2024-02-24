package es.codeurjc.webapp15.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp15.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String Name);
    List<User> findByEmail(String Email);
}

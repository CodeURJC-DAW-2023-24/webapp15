package es.codeurjc.webapp15.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.repository.ArtistRepository;
import jakarta.persistence.EntityManager;
import es.codeurjc.webapp15.model.Artist;

@Service
public class ArtistService {
    @Autowired
	private ArtistRepository repository;

	@Autowired
    private EntityManager entityManager;

	public Page<Artist> findArtists(Pageable page) {
		return repository.findAll(page);
	}

	public Optional<Artist> findById(long id) {
		return repository.findById(id);
	}

	@Query("SELECT a FROM Artist a")
	public Artist getFirst() {
		return (Artist) entityManager.createQuery("SELECT a FROM Artist a").setMaxResults(1).getSingleResult();
	}
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Artist> findAll() {
		return repository.findAll();
	}

	public void save(Artist artist) {
		repository.save(artist);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}
}

package es.codeurjc.webapp15.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.repository.ArtistRepository;
import es.codeurjc.webapp15.model.Artist;

@Service
public class ArtistService {
    @Autowired
	private ArtistRepository repository;

	public Optional<Artist> findById(long id) {
		return repository.findById(id);
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

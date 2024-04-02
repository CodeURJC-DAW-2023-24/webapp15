package es.codeurjc.webapp15.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.repository.GenreRepository;
import es.codeurjc.webapp15.model.Genre;

@Service
public class GenreService{
    
    @Autowired
	private GenreRepository repository;

	public Optional<Genre> findById(long id) {
		return repository.findById(id);
	}

    public Optional<Genre> findByGenreType(String type) {
        return repository.findByGenreType(type);
    }
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<Genre> findAll() {
		return repository.findAll();
	}

	public void save(Genre genre) {
		repository.save(genre);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}
}

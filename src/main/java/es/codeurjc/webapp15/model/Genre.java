package es.codeurjc.webapp15.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Genre{

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private String genre_type;

    @OneToMany(mappedBy="genre")
    private List<Concert> concerts;

    public Genre(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre_type() {
        return genre_type;
    }

    public void setGenre_type(String genre_type) {
        this.genre_type = genre_type;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }

    
}
package es.codeurjc.webapp15.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Genre{

    // /**
    //  * InnerGenre
    //  */
    // public interface InnerGenre {}

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(MinimalView.class)
	private Long id;

    @JsonView(MinimalView.class)
    private String genreType;

    @OneToMany(mappedBy="genre")
    @JsonIgnore
    private List<Concert> concerts;

    public Genre(){}

    public Genre(String genreType){

        this.genreType = genreType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenreType() {
        return genreType;
    }

    public void setGenreType(String genreType) {
        this.genreType = genreType;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }

    
}

package es.codeurjc.webapp15.model;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Artist {

    // /**
    //  * InnerArtist
    //  */
    // public interface InnerArtist {}
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(MinimalView.class)
	private Long id;

    @JsonView(MinimalView.class)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String info;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    public Artist() {}

    public Artist(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id_artist) {
        this.id = id_artist;
    }

    public String getName() {
        return name;
    }

    @JsonView(MinimalView.class)
    public String getURI() {
        return name.toLowerCase().replace(' ', '-');
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }
}

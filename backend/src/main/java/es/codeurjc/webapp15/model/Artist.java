package es.codeurjc.webapp15.model;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
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

    @Column(unique = true)
    @JsonView(MinimalView.class)
    private String uri;

    @Column(columnDefinition = "TEXT")
    private String info;

    @Lob
    @JsonIgnore
    private Blob imageFile;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Concert> concerts;

    public Artist() {}

    public Artist(String name, String info) {
        this.name = name;
        setUri();
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

    public String getUri() {
        return uri;
    }

    @JsonIgnore
    public String getUriFromName(String name) {
        return name.toLowerCase().replace(' ', '-');
    }

    private void setUri() {
        this.uri = name.toLowerCase().replace(' ', '-');
    }

    public void setName(String name) {
        this.name = name;
        setUri();
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

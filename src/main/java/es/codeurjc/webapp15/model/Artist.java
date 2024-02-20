package es.codeurjc.webapp15.model;

import java.util.List;
import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Artist {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_artist;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String info;
    
    @Lob
    private Blob imageFile;
    private boolean image;

    @OneToMany(mappedBy = "artist")
    private List<Concert> concerts;

    public Artist(){}

        public Artist(Long id_artist, String name, String info, boolean image, List<Concert> concerts) {
        this.id_artist = id_artist;
        this.name = name;
        this.info = info;
        //this.imageFile = imageFile;
        this.image = image;
        this.concerts = concerts;
    }

    public Long getId_artist() {
        return id_artist;
    }

    public void setId_artist(Long id_artist) {
        this.id_artist = id_artist;
    }

    public String getName() {
        return name;
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

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }
}

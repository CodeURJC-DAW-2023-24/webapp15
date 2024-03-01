package es.codeurjc.webapp15.model;

import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Artist {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String info;
    
    //private boolean image;

    @Lob
    private Blob imageFile;


    // @OneToMany(mappedBy = "artist")
    // private List<Concert> concerts;

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

    // public boolean isImage() {
    //     return image;
    // }

    // public void setImage(boolean image) {
    //     this.image = image;
    // }

    // public List<Concert> getConcerts() {
    //     return concerts;
    // }

    // public void setConcerts(List<Concert> concerts) {
    //     this.concerts = concerts;
    // }
    
}

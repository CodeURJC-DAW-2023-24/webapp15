package es.codeurjc.webapp15.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Concert {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_concert;

    private LocalDateTime datetime;
    private String place;
    private String num_tickets;
    private String price;

    @Column(columnDefinition = "TEXT")
    private String info;
    
    @ManyToOne
    private Artist artist;

    public Concert(){}

    public Long getId_concert() {
        return id_concert;
    }

    public void setId_concert(Long id_concert) {
        this.id_concert = id_concert;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNum_tickets() {
        return num_tickets;
    }

    public void setNum_tickets(String num_tickets) {
        this.num_tickets = num_tickets;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Artist getId_artist() {
        return artist;
    }

    public void setId_artist(Artist artist) {
        this.artist = artist;
    }
}
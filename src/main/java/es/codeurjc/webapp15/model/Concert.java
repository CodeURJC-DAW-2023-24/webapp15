package es.codeurjc.webapp15.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

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
    private Integer num_tickets;
    private Float price;

    @Column(columnDefinition = "TEXT")
    private String info;
    
    @ManyToOne
    private Artist artist;

    private Genre genre_type;

    public Concert(Long id_concert, LocalDateTime datetime, String place, Integer num_tickets, Float price, String info,
            Artist artist, Genre genre_type) {
        this.id_concert = id_concert;
        this.datetime = datetime;
        this.place = place;
        this.num_tickets = num_tickets;
        this.price = price;
        this.info = info;
        this.artist = artist;
        this.genre_type = genre_type;
    }



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

    public Integer getNum_tickets() {
        return num_tickets;
    }

    public void setNum_tickets(Integer num_tickets) {
        this.num_tickets = num_tickets;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
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

    public Genre getGenreType() {
        return genre_type;
    }

    public void setGenreType(Genre genre_type) {
        this.genre_type = genre_type;
    }

    public String getHour() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "HH:mm" );
        return datetime.format(f);
    }

    public String getWeekday() {
        return datetime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();
    }

    public int getDay() {
        return datetime.getDayOfMonth();
    }

    public String getMonth() {
        return datetime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }
    
}

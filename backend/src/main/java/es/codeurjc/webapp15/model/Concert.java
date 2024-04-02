package es.codeurjc.webapp15.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Concert {

    // /**
    //  * InnerConcert
    //  */
    // public interface InnerConcert {}
    public interface ConcertComplete extends MinimalView {}
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    private LocalDateTime datetime;
    private String place;
    private Integer num_tickets;
    private Float price;

    @Column(columnDefinition = "TEXT")
    private String info;
    
    @ManyToOne
    @JsonView(MinimalView.class)
    private Artist artist;

    @ManyToOne
    @JsonView(MinimalView.class)
    private Genre genre;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.REMOVE)
    @JsonView(ConcertComplete.class)
    @JsonIgnore
    private List<Ticket> tickets;

    public Concert(){}

    public Concert(LocalDateTime datetime, String place, Integer num_tickets, Float price, String info,
            Artist artist, Genre genre) {
        this.datetime = datetime;
        this.place = place;
        this.num_tickets = num_tickets;
        this.price = price;
        this.info = info;
        this.artist = artist;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id_concert) {
        this.id = id_concert;
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

    @JsonIgnore
    public Artist getId_artist() {
        return artist;
    }

    public void setId_artist(Artist artist) {
        this.artist = artist;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @JsonIgnore
    public String getHour() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "HH:mm" );
        return datetime.format(f);
    }

    @JsonIgnore
    public String getWeekday() {
        return datetime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();
    }

    @JsonIgnore
    public int getDay() {
        return datetime.getDayOfMonth();
    }

    @JsonIgnore
    public String getMonth() {
        return datetime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }
    
}

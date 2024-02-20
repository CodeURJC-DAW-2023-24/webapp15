package es.codeurjc.webapp15.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Concert {
    private Long id = null;
    private LocalDateTime datetime;
    private String city;
    private String venue;
    private Long id_artist;
    private Integer num_tickets;
    private Float price;
    private String info;
    // Change this to a enum
    private Genre genre_type;


    public Concert(Long id, LocalDateTime datetime, String city, String venue, Long id_artist, Integer num_tickets,
            Float price, String info, Genre genre_type) {
        this.id = id;
        this.datetime = datetime;
        this.city = city;
        this.venue = venue;
        this.id_artist = id_artist;
        this.num_tickets = num_tickets;
        this.price = price;
        this.info = info;
        this.genre_type = genre_type;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getVenue() {
        return venue;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }
    public Long getIdArtist() {
        return id_artist;
    }
    public void setIdArtist(Long id_artist) {
        this.id_artist = id_artist;
    }
    public Integer getNumTickets() {
        return num_tickets;
    }
    public void setNumTickets(Integer num_tickets) {
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

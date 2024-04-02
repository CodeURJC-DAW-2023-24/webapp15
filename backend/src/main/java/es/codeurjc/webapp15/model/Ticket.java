package es.codeurjc.webapp15.model;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket {

    public interface TicketComplete extends MinimalView {}
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(MinimalView.class)
	private Long id;

    @ManyToOne
    @JsonView(MinimalView.class)
    private User user;

    @ManyToOne
    @JsonView(MinimalView.class)
    private Concert concert;

    @JsonView(MinimalView.class)
    private Integer num_ticket;

    public Integer getNum_ticket() {
        return num_ticket;
    }

    public void setNum_ticket(Integer num_ticket) {
        this.num_ticket = num_ticket;
    }

    public Ticket(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id_ticket) {
        this.id = id_ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    
}

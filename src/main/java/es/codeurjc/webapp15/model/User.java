package es.codeurjc.webapp15.model;

import java.util.List;

import org.springframework.web.context.annotation.SessionScope;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity(name = "UserTable")
@SessionScope
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String password;

	@Column(unique = true)
    private String email;

	@Lob
	private Blob img_user;
	private boolean image;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	@OneToMany(mappedBy="user", fetch = FetchType.EAGER)
	private List<Ticket> tickets;

	public User() {
	}

	public User(String name, String password, String... roles) {
		this.name = name;
		this.password = password;
		this.roles = List.of(roles);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Long getId() {
		return id;
	}
	
	public Blob getImg_user() {
		return img_user;
	}

	public void setImg_user(Blob img_user) {
		this.img_user = img_user;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Boolean isRole(String rol) {
		return this.roles.contains(rol);
	}

	public void addRole(String role) {
		this.roles.add(role);
	}

}


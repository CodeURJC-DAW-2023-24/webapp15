package es.codeurjc.webapp15.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import es.codeurjc.webapp15.model.User;

@Component
@SessionScope
public class UserSession {

    private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
    
}

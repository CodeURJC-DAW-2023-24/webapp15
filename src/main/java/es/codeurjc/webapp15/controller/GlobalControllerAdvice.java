package es.codeurjc.webapp15.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.model.Ticket;
import es.codeurjc.webapp15.service.UserSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserSession session;

    @ModelAttribute("user")
    public User globalUserModel() {
        // This ensures that the `user` object is available in all templates
        // if the user is logged in.
        return session.getUser();
    }

    @ModelAttribute("admin")
    public Boolean globalAdminModel(){
        User admin = session.getUser();
        if (admin != null) {
            if (admin.isRole("ADMIN")) {
                return true;
            }
        }
        return false;
    }

    @ModelAttribute("tickets")
    public List<Ticket> globalTicketsModel() {
        User user = session.getUser();
        if (user != null) {
            return user.getTickets();
        }
        return null;
    }
}

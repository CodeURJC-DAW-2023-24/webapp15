package es.codeurjc.webapp15.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class loginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/loginerror")
    public String loginerror(){
        return "error";
    }
}

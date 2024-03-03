package es.codeurjc.webapp15.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.UserRepository;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override   
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()){
                List<GrantedAuthority> roles = new ArrayList<>();
                for (String role: user.get().getRoles()){
                roles.add(new SimpleGrantedAuthority("ROLE_"+ role));
                }
                return new org.springframework.security.core.userdetails.User(user.get().getName(), user.get().getEncodedPassword(), roles);
            }
            return null; 
     }
}

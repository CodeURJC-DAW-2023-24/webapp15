package es.codeurjc.webapp15.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    /*@Value("${security.user}")
    private String username;

    @Value("${security.encodedPassword}")
    private String encodedPassword;*/

    @Autowired
    public RepositoryUserDetailsService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

         authProvider.setUserDetailsService(userDetailService);
         authProvider.setPasswordEncoder(passwordEncoder());
         
         return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authenticationProvider(authenticationProvider());
        http
            .authorizeHttpRequests(authorize -> authorize
                    //Public PAGES
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/search*").permitAll()
                    .requestMatchers("/signup").permitAll()
                    .requestMatchers("/artist/*").permitAll()
                    .requestMatchers("/user**").permitAll()
                    .requestMatchers("/css/*", "/js/", "/images/**").permitAll()
                    //Private PAGES
                    .requestMatchers("/profile").hasAnyRole("USER")
                    .requestMatchers("/payment/*").hasAnyRole("USER")
                    .requestMatchers("/createArtist").hasAnyRole("ADMIN")
                    .requestMatchers("/createConcert").hasAnyRole("ADMIN")
            )

            .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .failureUrl("/error")
                    .defaultSuccessUrl("/")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            );
    
        // Disable CSRF at the moment
        http.csrf(csrf -> csrf.disable());
        
        return http.build();
    }
}

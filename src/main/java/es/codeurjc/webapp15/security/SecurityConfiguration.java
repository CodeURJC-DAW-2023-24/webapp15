package es.codeurjc.webapp15.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

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
                    .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/user/new").permitAll()
                    .requestMatchers("/error/**").permitAll()
                    .requestMatchers("/index/**").permitAll()
                    .requestMatchers("/info_artist/**").permitAll()
                    .requestMatchers("/login/**").permitAll()
                    .requestMatchers("/signup/**").permitAll()
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
        //http.csrf(csrf -> csrf.disable());
        
        return http.build();
    }
}

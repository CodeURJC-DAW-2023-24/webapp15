package es.codeurjc.webapp15.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                    .username("email")
                    .password(passwordEncoder().encode("pass"))
                    .roles("USER")
                    .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

         authProvider.setUserDetailsService(userDetailsService());
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
            //Private PAGES
                    .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .failureUrl("/error")
                    .defaultSuccessUrl("/perfil")
                    .permitAll()
            );
    
        // Disable CSRF at the moment
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

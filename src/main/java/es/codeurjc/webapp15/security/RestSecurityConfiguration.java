package es.codeurjc.webapp15.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.codeurjc.webapp15.security.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class RestSecurityConfiguration{
    
    @Autowired
    private RepositoryUserDetailsService userDetailsService;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
   @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}
    
   @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
    
    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authenticationProvider(authenticationProvider());
		
		http
			.authorizeHttpRequests(authorize -> authorize
                    // PRIVATE ENDPOINTS poner todos los apis 
                    .requestMatchers(HttpMethod.POST,"/api/artists/").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/api/artists/**").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE,"/api/artists/**").hasRole("ADMIN")

					.requestMatchers(HttpMethod.POST,"/api/concerts").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/api/concerts**").hasRole("USER")
                    .requestMatchers(HttpMethod.DELETE,"/api/concerts**").hasRole("ADMIN")

					//pago

					//busqueda

					// PUBLIC ENDPOINTS
					.anyRequest().permitAll()
			);
        // Disable Form login Authentication
        http.formLogin(formLogin -> formLogin.disable());

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf(csrf -> csrf.disable());

        // Disable Basic Authentication
        http.httpBasic(httpBasic -> httpBasic.disable());

        // Stateless session
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Add JWT Token filter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
        
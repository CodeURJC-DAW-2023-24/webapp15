package es.codeurjc.webapp15.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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

import es.codeurjc.webapp15.security.jwt.UnauthorizedHandlerJwt;
import es.codeurjc.webapp15.security.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public RepositoryUserDetailsService userDetailService;

    @Autowired
    private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apifilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .securityMatcher("/api/**")
                .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

        http
                .authorizeHttpRequests(authorize -> authorize

                        // PRIVATE ENDPOINTS
                        .requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/tickets/**").hasAnyRole("USER","ADMIN")
                        
                        .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/artists/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/logout").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/concerts").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/artists").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/tickets").hasAnyRole("USER","ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/api/tickets/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/artists/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/concerts/**").hasAnyRole("ADMIN")

                        // PUBLIC ENDPOINTS
                        .anyRequest().permitAll());

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

    @Bean
    @Order(2)
    public SecurityFilterChain webfilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .authorizeHttpRequests(authorize -> authorize
                        // Public PAGES
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/search*").permitAll()
                        .requestMatchers("/signup").permitAll()
                        .requestMatchers("/artist/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/user/new").permitAll()
                        .requestMatchers("/error/**").permitAll()
                        .requestMatchers("/index/**").permitAll()
                        .requestMatchers("/info_artist/**").permitAll()
                        .requestMatchers("concert-list-data", "get-concerts", "amount-of-concerts-by-month").permitAll()
                        .requestMatchers("more-artists", "more-tickets").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/signup/**").permitAll()
                        //.requestMatchers("api/**").permitAll()
                        // Private PAGES
                        .requestMatchers("/profile", "/user/**").hasAnyRole("USER")
                        .requestMatchers("/payment/*").hasAnyRole("USER")
                        .requestMatchers("/create-artist").hasAnyRole("ADMIN")
                        .requestMatchers("/create-concert").hasAnyRole("ADMIN")
                        .requestMatchers("/search/**").hasAnyRole("ADMIN")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs**").hasAnyRole("ADMIN"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/error")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }
}

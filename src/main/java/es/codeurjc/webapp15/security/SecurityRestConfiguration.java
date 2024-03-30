package es.codeurjc.webapp15.security;


import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;

@Controller
@EnableWebSecurity
public class SecurityRestConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    // @Autowired
    // private UserService userService;
   /*  @Autowired
    private EncoderConfiguration passwordEncoder;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;*/

    //Expose AuthenticationManager as a Bean to be used in other services
    //@Bean
    //@Override
    //public AuthenticationManager authenticationManagerBean() throws Exception {
    //    return super.authenticationManagerBean();
    //}
    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());

        return authProvider;
    }*/

    @Bean
    public AuthenticationManager authenticationRestManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    /*@Bean
    public FilterRegistrationBean<JwtRequestFilter> jwtRequestFilterRegistration() {
        FilterRegistrationBean<JwtRequestFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtRequestFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }*/
    @Bean
    @Order(1)
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/concerts/**").permitAll()
                        .requestMatchers("/api/concerts").permitAll()
                        .requestMatchers("/api/artists/**").permitAll()
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers("/api/login/**").permitAll()
                        .requestMatchers("/api/logout").permitAll()
                        .requestMatchers("/api/tickets/**").permitAll()

                )
                .httpBasic().disable()
                .formLogin().disable();
        // Disable csrf only for the api
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
package com.example.hackathon;

import com.example.hackathon.service.MyUserDetailsService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                // Permit access to static resources and public pages
                .requestMatchers("/css/**", "/js/**", "/assets/**", "/static/**").permitAll()
                .requestMatchers("/", "/api/auth/login", "/api/auth/register", "/api/auth/perform_register").permitAll()

                // Role-based restrictions
                .requestMatchers("/api/projects/dashboard").hasRole("CLIENT") // Only accessible by CLIENT
                .requestMatchers("/api/projects").hasRole("FREELANCER") // Only accessible by FREELANCER

                // Authenticated access for other resources
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/api/auth/login")
                .loginProcessingUrl("/api/auth/perform_login")
                .successHandler((request, response, authentication) -> {
                    // Redirect based on role
                    String redirectUrl = authentication.getAuthorities().stream()
                        .map(auth -> auth.getAuthority())
                        .filter(role -> role.equals("ROLE_FREELANCER") || role.equals("ROLE_CLIENT"))
                        .findFirst()
                        .map(role -> {
                            if (role.equals("ROLE_FREELANCER")) {
                                return "/api/projects"; // Freelancer dashboard
                            } else if (role.equals("ROLE_CLIENT")) {
                                return "/api/projects/dashboard"; // Client dashboard
                            }
                            return null;
                        }).orElse("/api/auth/login"); // Fallback to login page if no role matched

                    response.sendRedirect(redirectUrl);
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout") // Define the logout URL
                .logoutSuccessUrl("/") // Redirect to the home page after logout
                .clearAuthentication(true) // Clear authentication information
                .invalidateHttpSession(true) // Invalidate the session to remove the user session
                .deleteCookies("JSESSIONID") // Remove session cookie
                .permitAll()
            )
            .exceptionHandling()
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                // Redirect to custom access-denied page
                response.sendRedirect("/api/auth/access-denied");
            });

        return http.build();
    }


    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

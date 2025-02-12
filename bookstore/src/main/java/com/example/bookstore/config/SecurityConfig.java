package com.example.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                // Allow access to login page and static assets without authentication
                                                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                                                // Only ADMIN can access delete functionality
                                                .requestMatchers("/delete/**").hasRole("ADMIN")
                                                // All other URLs require authentication
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/booklist", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll());
                return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
                PasswordEncoder encoder = passwordEncoder();

                UserDetails user = User.builder()
                                .username("user")
                                .password(encoder.encode("password"))
                                .roles("USER")
                                .build();

                UserDetails admin = User.builder()
                                .username("admin")
                                .password(encoder.encode("adminpassword"))
                                .roles("ADMIN")
                                .build();

                return new InMemoryUserDetailsManager(user, admin);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}

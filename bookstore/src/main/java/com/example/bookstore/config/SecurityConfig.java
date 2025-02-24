package com.example.bookstore.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                // permit login and static resource access
                                                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                                                // restrict delete functionality to ADMIN users only
                                                .requestMatchers("/delete/**").hasRole("ADMIN")
                                                // require authentication for all other URLs
                                                .anyRequest().authenticated())
                                // enable HTTP Basic authentication for REST API calls
                                .httpBasic(withDefaults())
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
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}

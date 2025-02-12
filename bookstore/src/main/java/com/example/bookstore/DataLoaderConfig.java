package com.example.bookstore;

import com.example.bookstore.domain.AppUser;
import com.example.bookstore.domain.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoaderConfig {

    @Bean
    public CommandLineRunner dataLoader(AppUserRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() == 0) {
                // Create a user with role USER
                repository.save(new AppUser("user", encoder.encode("password"), "user@example.com", "USER"));

                // Create an admin with role ADMIN
                repository.save(new AppUser("admin", encoder.encode("adminpassword"), "admin@example.com", "ADMIN"));
            }
        };
    }
}

package com.example.bookstore;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            repository.save(new Book("The Hobbit", "J.R.R. Tolkien", 1937, "978-0547928227", 10.99));
            repository.save(new Book("1984", "George Orwell", 1949, "978-0451524935", 9.99));
            repository.save(new Book("Pride and Prejudice", "Jane Austen", 1813, "978-1503290563", 8.99));
        };
    }

}

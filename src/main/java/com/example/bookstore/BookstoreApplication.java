package com.example.bookstore;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return (args) -> {
            // Insert categories if not present
            if (categoryRepository.count() == 0) {
                Category fiction = new Category("Fiction");
                Category nonFiction = new Category("Non-Fiction");
                categoryRepository.save(fiction);
                categoryRepository.save(nonFiction);

                // Insert books with category
                bookRepository
                        .save(new Book("A Farewell to Arms", "Ernest Hemingway", 1929, "1232323-21", 10.99, fiction));
                bookRepository.save(new Book("Animal Farm", "George Orwell", 1945, "2212343-5", 8.99, fiction));
            }
        };
    }
}

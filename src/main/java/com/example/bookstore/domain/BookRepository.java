package com.example.bookstore.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

// Repository for Book entity
@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book, Long> {
    // Find books where title contains the search term (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);
}

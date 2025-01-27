package com.example.bookstore.domain;

import org.springframework.data.repository.CrudRepository;

// Repository for Book entity
public interface BookRepository extends CrudRepository<Book, Long> {
}

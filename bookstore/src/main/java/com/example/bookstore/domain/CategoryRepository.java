package com.example.bookstore.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Repository for Category entity
@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {
}

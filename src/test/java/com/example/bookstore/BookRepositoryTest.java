package com.example.bookstore;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateAndDeleteBook() {
        // Create and save a category
        Category category = new Category();
        category.setName("Test Category");
        categoryRepository.save(category);

        // Create and save a new book
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123456789");
        book.setPublicationYear(2025);
        book.setCategory(category);
        Book savedBook = bookRepository.save(book);
        assertThat(savedBook.getId()).isNotNull();

        // Delete the book and check it was removed
        bookRepository.delete(savedBook);
        assertThat(bookRepository.findById(savedBook.getId())).isEmpty();
    }

    @Test
    public void testSearchBooksByTitle() {
        // Create and save a book with a specific title
        Book book = new Book();
        book.setTitle("Spring Testing Book");
        book.setAuthor("Tester");
        // Set additional fields as necessary
        bookRepository.save(book);

        // Use the repository’s search method
        List<Book> result = bookRepository.findByTitleContainingIgnoreCase("Testing");
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getTitle()).contains("Testing");
    }
}

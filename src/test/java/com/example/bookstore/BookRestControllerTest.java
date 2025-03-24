package com.example.bookstore;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testGetAllBooks() {
        // Add a test book so that there is at least one book in the repository
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        bookRepository.save(book);

        // Use basic auth to get past the security layer
        Book[] books = this.restTemplate
                .withBasicAuth("user", "password")
                .getForObject("http://localhost:" + port + "/api/books", Book[].class);
        assertThat(books).isNotEmpty();
    }

    @Test
    public void testSearchBooks() {
        // Add a test book with a unique title
        Book book = new Book();
        book.setTitle("UniqueTitleForSearch");
        book.setAuthor("Test Author");
        bookRepository.save(book);

        // Search by title using the REST API with basic auth
        Book[] books = this.restTemplate
                .withBasicAuth("user", "password")
                .getForObject("http://localhost:" + port + "/api/books/search?title=UniqueTitleForSearch",
                        Book[].class);
        assertThat(books).isNotEmpty();
        assertThat(books[0].getTitle()).contains("UniqueTitleForSearch");
    }
}

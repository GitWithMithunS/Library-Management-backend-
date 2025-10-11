package com.library.BookServices.service;

import com.library.BookServices.model.Book;
import com.library.BookServices.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleBook = new Book(1, "Clean Code", "Robert C. Martin", 10);
    }

    @Test
    void test_add_new_book_success() {
        when(bookRepository.save(sampleBook)).thenReturn(sampleBook);

        Book savedBook = bookService.addBook(sampleBook);

        assertNotNull(savedBook);
        assertEquals("Clean Code", savedBook.getTitle());
        verify(bookRepository, times(1)).save(sampleBook);
    }

    @Test
    void test_get_book_by_isbn_found() {
        when(bookRepository.findById(String.valueOf(1)))
                .thenReturn(Optional.of(sampleBook));

        Book foundBook = bookService.getBookByIsbn(1);

        assertNotNull(foundBook);
        assertEquals(1, foundBook.getIsbn());
    }

    @Test
    void test_get_book_by_isbn_not_found() {
        when(bookRepository.findById(String.valueOf(999)))
                .thenReturn(Optional.empty());

        Book foundBook = bookService.getBookByIsbn(999);

        assertNull(foundBook);
    }

    @Test
    void test_reduce_quantity_success() {
        // Simulate reducing book quantity by 2
        sampleBook.setQuantity(sampleBook.getQuantity() - 2);

        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook);

        Book updatedBook = bookService.addBook(sampleBook);

        assertEquals(8, updatedBook.getQuantity());
        verify(bookRepository, times(1)).save(sampleBook);
    }
}

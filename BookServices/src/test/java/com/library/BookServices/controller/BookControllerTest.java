package com.library.BookServices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.BookServices.model.Book;
import com.library.BookServices.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_controller_add_book() throws Exception {
        Book book = new Book(1, "Java Basics", "James Gosling", 5);
        Mockito.when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Book added successfully")));
    }

    @Test
    void test_controller_get_book_ok() throws Exception {
        Book book = new Book(1, "Java Basics", "James Gosling", 5);
        Mockito.when(bookService.getBookByIsbn(1)).thenReturn(book);

        mockMvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    @Test
    void test_controller_get_book_404() throws Exception {
        Mockito.when(bookService.getBookByIsbn(999)).thenReturn(null);

        mockMvc.perform(get("/book/999"))
                .andExpect(status().isNotFound());
    }
}

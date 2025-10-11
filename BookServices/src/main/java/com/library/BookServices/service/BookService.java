package com.library.BookServices.service;

import com.library.BookServices.model.Book;
import com.library.BookServices.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookByIsbn(int isbn) {
        return bookRepository.findById(String.valueOf(isbn)).orElse(null);
    }
}

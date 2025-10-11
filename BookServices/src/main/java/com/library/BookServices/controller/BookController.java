package com.library.BookServices.controller;

import com.library.BookServices.model.Book;
import com.library.BookServices.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book){
        Book book1 = bookService.addBook(book);
        System.out.println("Book added successfully");

        return new ResponseEntity<>(book1 + "Book added successfully",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBook(){
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<?> retrieveBookByIsbn(@PathVariable int isbn){
        Book book = bookService.getBookByIsbn(isbn);
        if(book == null){
            return new ResponseEntity<>("Book not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book , HttpStatus.OK);
    }

    @PostMapping("/reduce/{isbn}")
    public ResponseEntity<?> reduceBookQuantity(@PathVariable int isbn , @RequestParam int quantity) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book == null) {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
        if (book.getQuantity() <= 0) {
            return new ResponseEntity<>("Book out of stock", HttpStatus.BAD_REQUEST);
        }

        if (book.getQuantity() < quantity) {
            return new ResponseEntity<>("Not enough stock available", HttpStatus.BAD_REQUEST);
        }

        // Reduce stock
        book.setQuantity(book.getQuantity() - quantity);
        bookService.addBook(book); // Save updated book record

        return new ResponseEntity<>(
                "Book quantity reduced by " + quantity + " successfully. Remaining: " + book.getQuantity() + book,
                HttpStatus.OK
        );
    }


}

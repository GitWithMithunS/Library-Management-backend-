package com.library.BorrowingServices.controller;

import com.library.BorrowingServices.model.Borrowing;
import com.library.BorrowingServices.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    // POST /borrow
    @PostMapping
    public ResponseEntity<?> borrowBook(@RequestBody Borrowing borrowing) {
        boolean success = borrowingService.borrowBook(borrowing);
        if (success) {
            return new ResponseEntity<>("Book borrowed successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to borrow book. Check Book Service / availability.",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // GET /borrow
    @GetMapping
    public ResponseEntity<?> getAllBorrowings() {
        List<Borrowing> borrowList = borrowingService.getAllBorrowings();
        if (borrowList.isEmpty()) {
            return new ResponseEntity<>("No borrow records found", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(borrowList, HttpStatus.OK);
    }
}

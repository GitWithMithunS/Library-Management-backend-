package com.library.BorrowingServices.service;

import com.library.BorrowingServices.model.Borrowing;
import com.library.BorrowingServices.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private RestTemplate restTemplate;


    public boolean borrowBook(Borrowing borrowing) {
        String url = "http://book-service/book/reduce/" + borrowing.getIsbn() +
                "?quantity=" + borrowing.getQuantity();

        try {
            restTemplate.postForEntity(url, null, String.class);
            borrowingRepository.save(borrowing);
            return true;
        } catch (Exception e) {
            System.out.println("Error contacting Book Service: " + e.getMessage());
            return false;
        }
    }

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }
}

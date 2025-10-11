package com.library.BorrowingServices.service;

import com.library.BorrowingServices.model.Borrowing;
import com.library.BorrowingServices.repository.BorrowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowingServiceTest {

    @Mock
    private BorrowingRepository borrowingRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BorrowingService borrowingService;

    private Borrowing sampleBorrowing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleBorrowing = new Borrowing(1, "Mithun", 1, 2);
    }

    @Test
    void test_borrow_book_success() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("Success", HttpStatus.CREATED));
        when(borrowingRepository.save(sampleBorrowing)).thenReturn(sampleBorrowing);

        boolean result = borrowingService.borrowBook(sampleBorrowing);

        assertTrue(result);
        verify(borrowingRepository, times(1)).save(sampleBorrowing);
    }

    @Test
    void test_borrow_book_service_down() {
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenThrow(new RuntimeException("Service Unavailable"));

        boolean result = borrowingService.borrowBook(sampleBorrowing);

        assertFalse(result);
    }
}

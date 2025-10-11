package com.library.BorrowingServices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.BorrowingServices.model.Borrowing;
import com.library.BorrowingServices.service.BorrowingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowingController.class)
public class BorrowingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingService borrowingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_controller_borrow_book_success() throws Exception {
        Borrowing borrowing = new Borrowing(1, "Mithun", 1, 2);
        Mockito.when(borrowingService.borrowBook(any(Borrowing.class))).thenReturn(true);

        mockMvc.perform(post("/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowing)))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Book borrowed successfully")));
    }

    @Test
    void test_controller_borrow_book_bad_request() throws Exception {
        Borrowing borrowing = new Borrowing(1, "Mithun", 1, 2);
        Mockito.when(borrowingService.borrowBook(any(Borrowing.class))).thenReturn(false);

        mockMvc.perform(post("/borrow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowing)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test_controller_get_all_borrowings_no_content() throws Exception {
        Mockito.when(borrowingService.getAllBorrowings()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/borrow"))
                .andExpect(status().isNoContent());
    }

    @Test
    void test_controller_get_all_borrowings_ok() throws Exception {
        List<Borrowing> list = List.of(new Borrowing(1, "Mithun", 1, 2));
        Mockito.when(borrowingService.getAllBorrowings()).thenReturn(list);

        mockMvc.perform(get("/borrow"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].borrowerName").value("Mithun"));
    }
}

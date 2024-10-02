package com.likelion.lionlib.controller;

import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.dto.request.BookRequest;
import com.likelion.lionlib.dto.response.BookResponse;
import com.likelion.lionlib.dto.request.LoanRequest;
import com.likelion.lionlib.dto.response.LoanResponse;
import com.likelion.lionlib.service.BookService;
import com.likelion.lionlib.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LibraryController {
    private final BookService bookService;
    private final LoanService loanService;

    // 도서 등록
    @PostMapping("/books")
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        log.info("Request POST a book: {}", bookRequest);
        BookResponse savedBook = bookService.addBook(bookRequest);
        log.info("Response POST a book: {}", savedBook);
        return ResponseEntity.ok(savedBook);
    }

    // 도서 정보 조회
    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable(name = "bookId") Long bookId) {
        log.info("Request GET a book with ID: {}", bookId);
        BookResponse book = bookService.getBook(bookId);
        log.info("Response GET a book: {}", book);
        return ResponseEntity.ok(book);
    }

    // 도서 목록 조회
    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info("Request GET all books");
        List<BookResponse> books = bookService.getAllBooks();
        log.info("Response GET all books: {}", books);
        return ResponseEntity.ok(books);
    }

    // 도서 정보 수정
    @PutMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable(name = "bookId") Long bookId, @RequestBody BookRequest bookRequest) {
        log.info("Request PUT update book with ID: {} and data: {}", bookId, bookRequest);
        BookResponse updatedBook = bookService.updateBook(bookId, bookRequest);
        log.info("Response PUT update book: {}", updatedBook);
        return ResponseEntity.ok(updatedBook);
    }

    // 도서 삭제
    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "bookId") Long bookId) {
        log.info("Request DELETE book with ID: {}", bookId);
        bookService.deleteBook(bookId);
        log.info("Response DELETE book with ID: {}", bookId);
        return ResponseEntity.noContent().build();
    }

    // 도서 대출 등록
    @PostMapping("/loans")
    public ResponseEntity<LoanResponse> addLoan(@RequestBody LoanRequest loanRequest, @AuthenticationPrincipal  CustomUserDetails customUserDetails) {
        log.info("Request POST a loan: {}", loanRequest);
        LoanResponse savedLoan = loanService.addLoan(loanRequest, customUserDetails);
        log.info("Response POST a loan: {}", savedLoan);
        return ResponseEntity.ok(savedLoan);
    }

    // 대출 정보 조회
    @GetMapping("/loans/{loanId}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable(name = "loanId") Long loanId) {
        log.info("Request GET a loan with ID: {}", loanId);
        LoanResponse loan = loanService.getLoan(loanId);
        log.info("Response GET a loan: {}", loan);
        return ResponseEntity.ok(loan);
    }

    // 대출 상태 수정
    @PutMapping("/loans/{loanId}")
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable(name = "loanId") Long loanId, @RequestBody LoanRequest loanRequest) {
        log.info("Request PUT update loan status with ID: {} and data: {}", loanId, loanRequest);
        LoanResponse updatedLoan = loanService.updateStatus(loanId, loanRequest);
        log.info("Response PUT update loan status: {}", updatedLoan);
        return ResponseEntity.ok(updatedLoan);
    }

    // 사용자의 대출 목록 조회
    @GetMapping("/members/loans")
    public ResponseEntity<List<LoanResponse>> getLoansByMemberId(@AuthenticationPrincipal  CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getId();
        log.info("Request GET loans for member with ID: {}", memberId);
        List<LoanResponse> loans = loanService.getLoansByMemberId(memberId);
        log.info("Response GET loans for member: {}", loans);
        return ResponseEntity.ok(loans);
    }
}
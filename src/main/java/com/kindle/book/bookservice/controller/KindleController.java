package com.kindle.book.bookservice.controller;

import com.kindle.book.bookservice.model.Book;
import com.kindle.book.bookservice.service.KindleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class KindleController {

    private final KindleService kindleService;

    public KindleController(KindleService kindleService) {
        this.kindleService = kindleService;
    }

    @GetMapping("/books/{userId}")
    public Flux<Book> getBooks(@PathVariable String userId) {
        System.out.println("inside controller with " + userId);
        return kindleService.viewBooksFor(userId);
    }

    @PostMapping("book/{bookId}")
    public Flux<Book> addBook(@PathVariable String bookId) {
        System.out.println("inside controller with " + bookId);
        return kindleService.addBook(bookId);
    }
}

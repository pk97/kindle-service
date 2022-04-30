package com.kindle.book.bookservice.service;

import com.kindle.book.bookservice.model.Book;
import com.kindle.book.bookservice.model.User;
import com.kindle.book.bookservice.respository.BookRepository;
import com.kindle.book.bookservice.respository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KindleServiceTest {
    private BookRepository bookRepository = mock(BookRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    void  beforeAll() {
        when(bookRepository.findAll()).thenReturn(Flux.just(
                new Book("b1", "harry potter-1", 500, null),
                new Book("b2", "harry potter-2", 501, null),
                new Book("b3", "harry potter-3", 502, null),
                new Book("b4", "harry potter-4", 503, null),
                new Book("b5", "harry potter-5", 504, null)
        ));

        when(userRepository.findAll()).thenReturn(Flux.just(
                new User("a1",  new ArrayList<>(Arrays.asList(
                        new Book("b1", "harry potter-1", 500, LocalDate.now().minusDays(12)),
                        new Book("b2", "harry potter-2", 501, LocalDate.now().minusDays(10)),
                        new Book("b3", "harry potter-3", 502, LocalDate.now().plusDays(5))
                )))));
    }

    @Test
    void shouldDisplayAllBooksForGivenUserIdThatAreNotExpired() {
        KindleService kindleService = new KindleService(bookRepository, userRepository);

        Flux<Book> a1 = kindleService.viewBooksFor("a1").log();

        StepVerifier.create(a1)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldCheckOutABookBasedOnSubscription() {

        KindleService kindleService = new KindleService(bookRepository, userRepository);

        Flux<Book> log = kindleService.checkout("b1", "a1").log();

        StepVerifier.create(log)
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void shouldNotCheckOutABookIfGivenBookisNotPresent() {

        KindleService kindleService = new KindleService(bookRepository, userRepository);

        Flux<Book> log = kindleService.checkout("b100", "a1").log();

        StepVerifier.create(log)
                .expectNextCount(0)
                .verifyComplete();

    }

    @Test
    void shouldNotCheckOutABookIfGivenUserisNotPresent() {

        KindleService kindleService = new KindleService(bookRepository, userRepository);

        Flux<Book> log = kindleService.checkout("b1", "a1111").log();

        StepVerifier.create(log)
                .expectNextCount(0)
                .verifyComplete();

    }
}
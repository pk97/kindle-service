package com.kindle.book.bookservice.service;


import com.kindle.book.bookservice.model.Book;
import com.kindle.book.bookservice.model.User;
import com.kindle.book.bookservice.respository.BookRepository;
import com.kindle.book.bookservice.respository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@Service
public class KindleService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    public KindleService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Flux<Book> viewBooksFor(String userId) {
        return userRepository.findAll()
                .filter(user -> user.userId.equals(userId))
                .flatMap(filteredUser -> {
                    System.out.println("inside service");
                    return Flux.fromIterable(filteredUser.books);})
                .filter(e -> e.expiryDate.isAfter(LocalDate.now()));
    }

    private Mono<User> getUser(String userId) {
        return userRepository.findAll()
                .filter(user -> user.userId.equals(userId))
               .next();
    }

    private Mono<Book> getBook(String bookId) {
        return bookRepository.findAll()
                .filter(book -> book.bookId.equals(bookId))
               .next();
    }


    public Flux<Book> checkout(String bookId, String userId) {
        return Flux.zip(getUser(userId), getBook(bookId))
                .map(
                        it -> {
                            Book book = it.getT2();
                            book.expiryDate = LocalDate.now().plusDays(10);
                            it.getT1().books.add(book);
                            return it.getT2();
                        }
                );
    }


}




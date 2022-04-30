package com.kindle.book.bookservice.respository;

import com.kindle.book.bookservice.model.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
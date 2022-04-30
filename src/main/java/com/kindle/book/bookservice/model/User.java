package com.kindle.book.bookservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("User")
public class User {
    public User(String userId, List<Book> books) {
        this.userId = userId;
        this.books = books;
    }

    public String userId;
    public List<Book> books;
}
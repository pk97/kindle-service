package com.kindle.book.bookservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("Book")
public class Book {
    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public Book(String bookId, String name, int price, LocalDate expiryDate) {
        this.bookId = bookId;
        this.name = name;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public String bookId;
    private String name;
    private int price;
    public LocalDate expiryDate;
}

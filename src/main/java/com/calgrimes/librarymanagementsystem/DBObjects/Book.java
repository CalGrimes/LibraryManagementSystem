package com.calgrimes.librarymanagementsystem.DBObjects;

import com.google.gson.Gson;

public class Book {

    private int book_id;

    private String book_name;

    private String book_genre;

    private double book_price;


    public Book() {

    }

    public Book(int book_id, String book_name, String book_genre, double book_price) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_genre = book_genre;
        this.book_price = book_price;

    }

}

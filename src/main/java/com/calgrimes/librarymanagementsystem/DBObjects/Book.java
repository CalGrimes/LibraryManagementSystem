package com.calgrimes.librarymanagementsystem.DBObjects;

import com.google.gson.Gson;

public class Book {

    private int bookId;

    private String bookTitle;

    private String authorFirst;

    private String authorLast;

    private String bookGenre;

    private double bookPrice;


    public Book() {

    }

    public Book(int bookId, String bookTitle, String authorFirst, String authorLast, String bookGenre, double bookPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.authorFirst = authorFirst;
        this.authorLast = authorLast;
        this.bookGenre = bookGenre;
        this.bookPrice = bookPrice;

    }

    public int getBookId()
    {
        return bookId;
    }
    public String getBookTitle()
    {
        return bookTitle;
    }
    public String getAuthorFirst()
    {
        return authorFirst;
    }
    public String getAuthorLast()
    {
        return authorLast;
    }
    public String getBookGenre()
    {
        return bookGenre;
    }
    public double getBookPrice()
    {
        return bookPrice;
    }









}

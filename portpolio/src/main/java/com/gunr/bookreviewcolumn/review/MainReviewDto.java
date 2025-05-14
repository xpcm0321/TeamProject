package com.gunr.bookreviewcolumn.review;

import java.time.LocalDateTime;

public class MainReviewDto {
	private Long id;
    private String review_title;
    private int rating;
    private String bookTitle;
    private String bookAuthor;
    private String bookImage;
    private LocalDateTime review_date;

    public MainReviewDto(Long id, String review_title, int rating, String bookTitle, String bookAuthor, String bookImage, LocalDateTime review_date) {
        this.id = id;
        this.review_title = review_title;
        this.rating = rating;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookImage = bookImage;
        this.review_date = review_date;
    }

    public Long getId() { return id; }
    public String getReview_title() { return review_title; }
    public int getRating() { return rating; }
    public String getBookTitle() { return bookTitle; }
    public String getBookAuthor() { return bookAuthor; }
    public String getBookImage() { return bookImage; }
    public LocalDateTime getReview_date() { return review_date; }
}

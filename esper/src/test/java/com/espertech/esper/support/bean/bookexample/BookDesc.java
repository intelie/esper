package com.espertech.esper.support.bean.bookexample;

public class BookDesc
{
    private final String bookId;
    private final String title;
    private final String author;
    private final Review[] reviews;

    public BookDesc(String bookId, String title, String author, Review[] reviews)
    {
        this.author = author;
        this.bookId = bookId;
        this.title = title;
        this.reviews = reviews;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getBookId()
    {
        return bookId;
    }

    public String getTitle()
    {
        return title;
    }

    public Review[] getReviews()
    {
        return reviews;
    }
}

package com.espertech.esper.support.bean.bookexample;

public class Review
{
    private int reviewId;
    private String comment;

    public Review(int reviewId, String comment)
    {
        this.reviewId = reviewId;
        this.comment = comment;
    }

    public int getReviewId()
    {
        return reviewId;
    }

    public String getComment()
    {
        return comment;
    }
}

package com.espertech.esper.support.bean.bookexample;

public class GameDesc
{
    private final String gameId;
    private final String title;
    private final String publisher;
    private final Review[] reviews;

    public GameDesc(String gameId, String title, String publisher, Review[] reviews)
    {
        this.publisher = publisher;
        this.gameId = gameId;
        this.title = title;
        this.reviews = reviews;
    }

    public String getGameId()
    {
        return gameId;
    }

    public String getTitle()
    {
        return title;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public Review[] getReviews()
    {
        return reviews;
    }
}

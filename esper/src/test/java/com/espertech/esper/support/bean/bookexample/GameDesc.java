/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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

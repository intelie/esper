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

public class BookDesc
{
    private final String bookId;
    private final String title;
    private final String author;
    private final double price;
    private final Review[] reviews;

    public BookDesc(String bookId, String title, String author, double price, Review[] reviews)
    {
        this.author = author;
        this.bookId = bookId;
        this.title = title;
        this.price = price;
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

    public double getPrice() {
        return price;
    }
}

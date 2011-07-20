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

package com.espertech.esper.support.bean;

public class SupportTradeEvent
{
    private int id;
    private String userId;
    private String ccypair;
    private String direction;
    private int amount;

    public SupportTradeEvent(int id, String userId, String ccypair, String direction)
    {
        this.id = id;
        this.userId = userId;
        this.ccypair = ccypair;
        this.direction = direction;
    }

    public SupportTradeEvent(int id, String userId, int amount)
    {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
    }

    public int getId()
    {
        return id;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getCcypair()
    {
        return ccypair;
    }

    public String getDirection()
    {
        return direction;
    }

    public int getAmount()
    {
        return amount;
    }

    public String toString()
    {
        return "id=" + id +
               " userId=" + userId +
               " ccypair=" + ccypair +
               " direction=" + direction;
    }
}

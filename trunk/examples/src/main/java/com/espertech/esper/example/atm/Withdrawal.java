/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.atm;

public class Withdrawal
{
    private long accountNumber;
    private int amount;
    private long timestamp;

    public Withdrawal(long accountNumber, int amount)
    {
        this.accountNumber = accountNumber;
        this.amount = amount;
        timestamp = System.currentTimeMillis();
    }

    public long getAccountNumber()
    {
        return accountNumber;
    }

    public int getAmount()
    {
        return amount;
    }

    public long getTimestamp()
    {
        return timestamp;
    }
}

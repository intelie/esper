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

package com.espertech.esper.support.util;

import java.util.List;
import java.util.Collections;

public class OccuranceBucket
{
    private final long low;
    private final long high;
    private final int countEntry;
    private final int countTotal;
    private List<OccuranceBucket> innerBuckets;

    public OccuranceBucket(long low, long high, int countEntry, int countTotal)
    {
        this.low = low;
        this.high = high;
        this.countEntry = countEntry;
        this.countTotal = countTotal;
        innerBuckets = Collections.EMPTY_LIST;
    }

    public void setInnerBuckets(List<OccuranceBucket> innerBuckets)
    {
        this.innerBuckets = innerBuckets;
    }

    public long getLow()
    {
        return low;
    }

    public long getHigh()
    {
        return high;
    }

    public int getCountEntry()
    {
        return countEntry;
    }

    public int getCountTotal()
    {
        return countTotal;
    }

    public List<OccuranceBucket> getInnerBuckets()
    {
        return innerBuckets;
    }
}

/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.rowregex;

/**
 * Getter that provides an index at runtime.
 */
public class RegexPartitionStateRandomAccessGetter
{
    private final int[] randomAccessIndexesRequested;
    private final int maxPriorIndex;
    private final boolean isUnbound;

    private RegexPartitionStateRandomAccess randomAccess;

    public RegexPartitionStateRandomAccessGetter(int[] randomAccessIndexesRequested, boolean isUnbound)
    {
        this.randomAccessIndexesRequested = randomAccessIndexesRequested;
        this.isUnbound = isUnbound;

        // Determine the maximum prior index to retain
        int maxPriorIndex = 0;
        for (Integer priorIndex : randomAccessIndexesRequested)
        {
            if (priorIndex > maxPriorIndex)
            {
                maxPriorIndex = priorIndex;
            }
        }
        this.maxPriorIndex = maxPriorIndex;
    }

    public int getMaxPriorIndex()
    {
        return maxPriorIndex;
    }

    public int[] getIndexesRequested()
    {
        return randomAccessIndexesRequested;
    }

    public int getIndexesRequestedLen()
    {
        return randomAccessIndexesRequested.length;
    }

    public boolean isUnbound()
    {
        return isUnbound;
    }

    /**
     * Returns the index for access.
     * @return index
     */
    public RegexPartitionStateRandomAccess getAccessor()
    {
        return randomAccess;
    }

    public void setRandomAccess(RegexPartitionStateRandomAccess randomAccess)
    {
        this.randomAccess = randomAccess;
    }
}
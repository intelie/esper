/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.exec.SortedTableLookupStrategy;
import com.espertech.esper.epl.join.exec.JoinExecTableLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Map;

/**
 * Plan to perform an indexed table lookup.
 */
public class SortedTableLookupPlan extends TableLookupPlan
{
    private RangeKeyDesc rangeKeyPair;

    /**
     * Ctor.
     * @param lookupStream - stream that generates event to look up for
     * @param indexedStream - stream to index table lookup
     * @param indexNum - index number for the table containing the full unindexed contents
     */
    public SortedTableLookupPlan(int lookupStream, int indexedStream, String indexNum, RangeKeyDesc rangeKeyPair)
    {
        super(lookupStream, indexedStream, indexNum);
        this.rangeKeyPair = rangeKeyPair;
    }

    public JoinExecTableLookupStrategy makeStrategy(Map<String,EventTable>[] indexesPerStream, EventType[] eventTypes)
    {
        PropertySortedEventTable index = (PropertySortedEventTable) indexesPerStream[this.getIndexedStream()].get(this.getIndexNum());
        return new SortedTableLookupStrategy(eventTypes[this.getLookupStream()], rangeKeyPair, index);
    }

    public String toString()
    {
        return "SortedTableLookupPlan " +
                super.toString() +
               " keyProperties=" + rangeKeyPair;
    }
}

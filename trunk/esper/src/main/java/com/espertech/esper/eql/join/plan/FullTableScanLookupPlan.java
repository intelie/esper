/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.join.plan;

import com.espertech.esper.eql.join.exec.FullTableScanLookupStrategy;
import com.espertech.esper.eql.join.exec.TableLookupStrategy;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.table.UnindexedEventTable;
import com.espertech.esper.event.EventType;

/**
 * Plan for a full table scan.
 */
public class FullTableScanLookupPlan extends TableLookupPlan
{
    /**
     * Ctor.
     * @param lookupStream - stream that generates event to look up for
     * @param indexedStream - stream to full table scan
     * @param indexNum - index number for the table containing the full unindexed contents
     */
    public FullTableScanLookupPlan(int lookupStream, int indexedStream, int indexNum)
    {
        super(lookupStream, indexedStream, indexNum);
    }

    public TableLookupStrategy makeStrategy(EventTable[][] indexesPerStream, EventType[] eventTypes)
    {
        UnindexedEventTable index = (UnindexedEventTable) indexesPerStream[this.getIndexedStream()][this.getIndexNum()];
        return new FullTableScanLookupStrategy(index);
    }

    public String toString()
    {
        return "FullTableScanLookupPlan " +
                super.toString();
    }

}

/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.exec;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.epl.join.rep.Cursor;
import com.espertech.esper.epl.join.PollResultIndexingStrategy;
import com.espertech.esper.epl.join.HistoricalIndexLookupStrategyNoIndex;
import com.espertech.esper.epl.join.HistoricalIndexLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.HistoricalEventViewable;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

// TODO
public class HistoricalTableLookupStrategy implements TableLookupStrategy
{
    private final HistoricalEventViewable viewable;
    private final PollResultIndexingStrategy indexingStrategy;
    private final HistoricalIndexLookupStrategy lookupStrategy;
    private final int numStreams;
    private final int streamNum;
    private final ExprNode outerJoinExprNode;
    private final EventBean[] eventsPerStream;

    public HistoricalTableLookupStrategy(HistoricalEventViewable viewable, PollResultIndexingStrategy indexingStrategy, HistoricalIndexLookupStrategy lookupStrategy, int numStreams, int streamNum, ExprNode outerJoinExprNode)
    {
        this.viewable = viewable;
        this.indexingStrategy = indexingStrategy;
        this.lookupStrategy = lookupStrategy;
        this.numStreams = numStreams;
        this.streamNum = streamNum;
        this.outerJoinExprNode = outerJoinExprNode;
        this.eventsPerStream = new EventBean[numStreams];
    }

    public Set<EventBean> lookup(EventBean event, Cursor cursor)
    {
        EventBean[][] lookupEventsPerStream = new EventBean[1][numStreams];
        int currStream = cursor.getStream();

        // TODO - multiple and indirect dependencies of historical stream
        lookupEventsPerStream[0][currStream] = event;
        eventsPerStream[currStream] = event;

        EventTable[] indexPerLookupRow = viewable.poll(lookupEventsPerStream, indexingStrategy);

        Set<EventBean> result = null;
        for (EventTable index : indexPerLookupRow)
        {
            // Using the index, determine a subset of the whole indexed table to process, unless
            // the strategy is a full table scan
            Iterator<EventBean> subsetIter = lookupStrategy.lookup(event, index);

            if (subsetIter != null)
            {
                // Add each row to the join result or, for outer joins, run through the outer join filter
                for (;subsetIter.hasNext();)
                {
                    EventBean candidate = subsetIter.next();

                    eventsPerStream[streamNum] = candidate;
                    Boolean pass = (Boolean) outerJoinExprNode.evaluate(eventsPerStream, true);
                    if ((pass != null) && (pass))
                    {
                        if (result == null)
                        {
                            result = new HashSet<EventBean>();
                        }
                        result.add(candidate);
                    }
                }
            }
        }

        return result;
    }
}

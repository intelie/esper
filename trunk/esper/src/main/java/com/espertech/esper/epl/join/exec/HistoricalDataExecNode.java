package com.espertech.esper.epl.join.exec;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.IndentWriter;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.PollResultIndexingStrategy;
import com.espertech.esper.epl.join.HistoricalIndexLookupStrategy;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.view.HistoricalEventViewable;

import java.util.List;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HistoricalDataExecNode extends ExecNode
{
    private static final Log log = LogFactory.getLog(HistoricalDataExecNode.class);

    private final EventBean[][] lookupRows1Event;
    private final int numStreams;
    private final HistoricalEventViewable historicalEventViewable;
    private final PollResultIndexingStrategy indexingStrategy;
    private final HistoricalIndexLookupStrategy indexLookupStrategy;
    private final int historicalStreamNumber;

    public HistoricalDataExecNode(HistoricalEventViewable historicalEventViewable, PollResultIndexingStrategy indexingStrategy, HistoricalIndexLookupStrategy indexLookupStrategy, int numStreams, int historicalStreamNumber)
    {
        this.historicalEventViewable = historicalEventViewable;
        this.indexingStrategy = indexingStrategy;
        this.indexLookupStrategy = indexLookupStrategy;
        this.numStreams = numStreams;
        this.historicalStreamNumber = historicalStreamNumber;

        lookupRows1Event = new EventBean[1][];
        lookupRows1Event[0] = new EventBean[numStreams];
    }

    public void process(EventBean lookupEvent, EventBean[] prefillPath, List<EventBean[]> result)
    {
        lookupRows1Event[0] = prefillPath;
        EventTable[] indexPerLookupRow = historicalEventViewable.poll(lookupRows1Event, indexingStrategy);

        for (EventTable index : indexPerLookupRow)
        {
            // Using the index, determine a subset of the whole indexed table to process, unless
            // the strategy is a full table scan
            Iterator<EventBean> subsetIter = indexLookupStrategy.lookup(lookupEvent, index);

            if (subsetIter != null)
            {
                // Add each row to the join result or, for outer joins, run through the outer join filter
                for (;subsetIter.hasNext();)
                {
                    EventBean[] resultRow = new EventBean[numStreams];
                    System.arraycopy(prefillPath, 0, resultRow, 0, numStreams);
                    resultRow[historicalStreamNumber] = subsetIter.next();
                    result.add(resultRow);
                }
            }
        }
    }

    public void print(IndentWriter writer)
    {
        writer.println("HistoricalDataExecNode");
    }
}

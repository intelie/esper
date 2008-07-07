package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.join.exec.ExecNode;
import com.espertech.esper.epl.join.exec.HistoricalDataExecNode;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTableList;
import com.espertech.esper.epl.join.HistoricalIndexLookupStrategyNoIndex;
import com.espertech.esper.epl.join.PollResultIndexingStrategy;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.IndentWriter;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.view.HistoricalEventViewable;

import java.util.List;

public class HistoricalDataPlanNode extends QueryPlanNode
{
    private final int streamNum;
    private final int numStreams;

    public HistoricalDataPlanNode(int streamNum, int numStreams)
    {
        this.streamNum = streamNum;
        this.numStreams = numStreams;
    }

    public ExecNode makeExec(EventTable[][] indexesPerStream, EventType[] streamTypes, Viewable[] streamViews)
    {
        HistoricalEventViewable viewable = (HistoricalEventViewable) streamViews[streamNum];
        HistoricalIndexLookupStrategyNoIndex noindex = new HistoricalIndexLookupStrategyNoIndex();
        PollResultIndexingStrategy iteratorIndexingStrategy = new PollResultIndexingStrategy()
        {
            public EventTable index(List<EventBean> pollResult, boolean isActiveCache)
            {
                return new UnindexedEventTableList(pollResult);
            }
        };
        
        return new HistoricalDataExecNode(viewable, iteratorIndexingStrategy, noindex, numStreams, streamNum);
    }

    protected void print(IndentWriter writer)
    {
        writer.incrIndent();
        writer.println("HistoricalDataPlanNode streamNum=" + streamNum);
    }
}

package com.espertech.esper.epl.join.plan;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.join.HistoricalIndexLookupStrategy;
import com.espertech.esper.epl.join.HistoricalIndexLookupStrategyNoIndex;
import com.espertech.esper.epl.join.JoinSetComposerFactoryImpl;
import com.espertech.esper.epl.join.PollResultIndexingStrategy;
import com.espertech.esper.epl.join.exec.ExecNode;
import com.espertech.esper.epl.join.exec.HistoricalDataExecNode;
import com.espertech.esper.epl.join.exec.HistoricalTableLookupStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTableList;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.IndentWriter;
import com.espertech.esper.view.HistoricalEventViewable;
import com.espertech.esper.view.Viewable;

import java.util.List;

public class HistoricalDataPlanNode extends QueryPlanNode
{
    private final int streamNum;
    private final int numStreams;
    private final QueryGraph queryGraph;
    private final ExprNode outerJoinExprNode;

    public HistoricalDataPlanNode(int streamNum, int numStreams, QueryGraph queryGraph, ExprNode exprNode)
    {
        this.streamNum = streamNum;
        this.numStreams = numStreams;
        this.queryGraph = queryGraph;
        this.outerJoinExprNode = exprNode;
    }

    public ExecNode makeExec(EventTable[][] indexesPerStream, EventType[] streamTypes, Viewable[] streamViews)
    {
        // TODO - index building
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

    public HistoricalTableLookupStrategy makeOuterJoinStategy(Viewable[] streamViews, EventType[] streamTypes, int pollingStreamNum)
    {
        int streamViewStreamNum = pollingStreamNum;
        EventType streamViewType = streamTypes[pollingStreamNum];

        int polledViewStreamNum = streamNum;
        EventType polledViewType = streamTypes[streamNum];

        Pair<HistoricalIndexLookupStrategy, PollResultIndexingStrategy> pair = JoinSetComposerFactoryImpl.determineIndexing(
                queryGraph, polledViewType, streamViewType, polledViewStreamNum, streamViewStreamNum);

        HistoricalEventViewable viewable = (HistoricalEventViewable) streamViews[streamNum];

        return new HistoricalTableLookupStrategy(viewable, pair.getSecond(), pair.getFirst(), numStreams, streamNum, outerJoinExprNode);
    }

    protected void print(IndentWriter writer)
    {
        writer.incrIndent();
        writer.println("HistoricalDataPlanNode streamNum=" + streamNum);
    }
}

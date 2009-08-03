package com.espertech.esper.rowregex;

import com.espertech.esper.view.ViewSupport;
import com.espertech.esper.view.window.RandomAccessByIndexGetter;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.SingleEventIterator;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.epl.agg.AggregationServiceMatchRecognize;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.MatchRecognizeSpec;
import com.espertech.esper.epl.spec.MatchRecognizeDefineItem;
import com.espertech.esper.epl.spec.MatchRecognizeMeasureItem;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.io.StringWriter;
import java.io.PrintWriter;

public class EventRowRegexIteratorResult
{
    private List<RegexNFAStateEntry> endStates;
    private int eventSequenceNum;

    public EventRowRegexIteratorResult(List<RegexNFAStateEntry> endStates, int eventSequenceNum)
    {
        this.endStates = endStates;
        this.eventSequenceNum = eventSequenceNum;
    }

    public List<RegexNFAStateEntry> getEndStates()
    {
        return endStates;
    }

    public int getEventSequenceNum()
    {
        return eventSequenceNum;
    }
}

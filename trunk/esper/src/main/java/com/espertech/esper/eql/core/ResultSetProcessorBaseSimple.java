package com.espertech.esper.eql.core;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.eql.spec.OutputLimitLimitType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventBeanUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Result set processor for the simplest case: no aggregation functions used in the select clause, and no group-by.
 * <p>
 * The processor generates one row for each event entering (new event) and one row for each event leaving (old event).
 */
public abstract class ResultSetProcessorBaseSimple implements ResultSetProcessor
{
    private static final Log log = LogFactory.getLog(ResultSetProcessorBaseSimple.class);
    private final Set<MultiKey<EventBean>> emptyRowSet = new HashSet<MultiKey<EventBean>>();

    public void clear()
    {
        // No need to clear state, there is no state held
    }

    public UniformPair<EventBean[]> processOutputLimitedJoin(List<UniformPair<Set<MultiKey<EventBean>>>> joinEventsSet, boolean generateSynthetic, OutputLimitLimitType outputLimitLimitType)
    {
        if (outputLimitLimitType != OutputLimitLimitType.LAST)
        {
            UniformPair<Set<MultiKey<EventBean>>> flattened = EventBeanUtility.flattenBatchJoin(joinEventsSet);
            return processJoinResult(flattened.getFirst(), flattened.getSecond(), generateSynthetic);
        }

        Set<MultiKey<EventBean>> lastNonEmptyNew = null;
        Set<MultiKey<EventBean>> lastNonEmptyOld = null;
        for (UniformPair<Set<MultiKey<EventBean>>> pair : joinEventsSet)
        {
            if (!pair.getFirst().isEmpty())
            {
                lastNonEmptyNew = pair.getFirst();
            }
            if (!pair.getSecond().isEmpty())
            {
                lastNonEmptyOld = pair.getSecond();
            }
        }

        if (lastNonEmptyNew == null)
        {
            lastNonEmptyNew = emptyRowSet;
        }
        if (lastNonEmptyOld == null)
        {
            lastNonEmptyOld = emptyRowSet;
        }
        UniformPair<EventBean[]> result = processJoinResult(lastNonEmptyNew, lastNonEmptyOld, generateSynthetic);

        EventBean[] lastNew = null;
        if ((result.getFirst() != null) && (result.getFirst().length != 0)) {
            lastNew = new EventBean[] {result.getFirst()[result.getFirst().length - 1]};
        }
        EventBean[] lastOld = null;
        if ((result.getSecond() != null) && (result.getSecond().length != 0)) {
            lastOld = new EventBean[] {result.getSecond()[result.getSecond().length - 1]};
        }
        return new UniformPair<EventBean[]>(lastNew, lastOld);
    }

    public UniformPair<EventBean[]> processOutputLimitedView(List<UniformPair<EventBean[]>> viewEventsList, boolean generateSynthetic, OutputLimitLimitType outputLimitLimitType)
    {
        if (outputLimitLimitType != OutputLimitLimitType.LAST)
        {
            UniformPair<EventBean[]> pair = EventBeanUtility.flattenBatchStream(viewEventsList);
            return processViewResult(pair.getFirst(), pair.getSecond(), generateSynthetic);
        }

        EventBean[] lastNonEmptyNew = null;
        EventBean[] lastNonEmptyOld = null;
        for (UniformPair<EventBean[]> pair : viewEventsList)
        {
            if ((pair.getFirst() != null) && (pair.getFirst().length != 0))
            {
                lastNonEmptyNew = pair.getFirst();
            }
            if ((pair.getSecond() != null) && (pair.getSecond().length != 0))
            {
                lastNonEmptyOld = pair.getSecond();
            }
        }

        UniformPair<EventBean[]> result = processViewResult(lastNonEmptyNew, lastNonEmptyOld, generateSynthetic);

        EventBean[] lastNew = null;
        if ((result.getFirst() != null) && (result.getFirst().length != 0)) {
            lastNew = new EventBean[] {result.getFirst()[result.getFirst().length - 1]};
        }
        EventBean[] lastOld = null;
        if ((result.getSecond() != null) && (result.getSecond().length != 0)) {
            lastOld = new EventBean[] {result.getSecond()[result.getSecond().length - 1]};
        }

        return new UniformPair<EventBean[]>(lastNew, lastOld);
    }
}

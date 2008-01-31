package com.espertech.esper.eql.view;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.core.UpdateDispatchView;
import com.espertech.esper.event.EventBean;

/**
 * An output strategy that outputs if there are results of if
 * the force-update flag is set.
 */
public class OutputStrategySimple implements OutputStrategy
{
    public void output(boolean forceUpdate, UniformPair<EventBean[]> result, UpdateDispatchView finalView)
    {
        EventBean[] newEvents = result != null ? result.getFirst() : null;
        EventBean[] oldEvents = result != null ? result.getSecond() : null;
        if(newEvents != null || oldEvents != null)
        {
            finalView.newResult(result);
        }
        else if(forceUpdate)
        {
            finalView.newResult(result);
        }
    }
}

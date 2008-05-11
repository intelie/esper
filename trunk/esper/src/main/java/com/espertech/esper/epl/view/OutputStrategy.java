package com.espertech.esper.epl.view;

import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.core.UpdateDispatchView;
import com.espertech.esper.event.EventBean;

/**
 * Strategy for performing an output via dispatch view.
 */
public interface OutputStrategy
{
    /**
     * Outputs the result to the output view and following update policy.
     * @param forceUpdate indicates whether output can be skipped, such as when no results collected
     * @param result the output to indicate
     * @param outputView the view to output to
     */
    public void output(boolean forceUpdate, UniformPair<EventBean[]> result, UpdateDispatchView outputView);
}

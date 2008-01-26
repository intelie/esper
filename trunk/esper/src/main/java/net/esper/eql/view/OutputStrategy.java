package net.esper.eql.view;

import net.esper.collection.Pair;
import net.esper.core.UpdateDispatchView;
import net.esper.event.EventBean;

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
    public void output(boolean forceUpdate, Pair<EventBean[], EventBean[]> result, UpdateDispatchView outputView);
}

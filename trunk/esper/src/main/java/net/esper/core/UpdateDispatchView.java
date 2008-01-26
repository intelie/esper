package net.esper.core;

import net.esper.view.View;
import net.esper.event.EventBean;
import net.esper.collection.Pair;

/**
 * Update dispatch view to indicate statement results to listeners.
 */
public interface UpdateDispatchView extends View
{
    /**
     * Convenience method that accepts a pair of new and old data
     * as this is the most treated unit.
     * @param result is new data (insert stream) and old data (remove stream)
     */
    public void newResult(Pair<EventBean[], EventBean[]> result); 
}

package com.espertech.esper.core;

import com.espertech.esper.view.View;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.Pair;

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

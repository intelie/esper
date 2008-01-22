package net.esper.core;

import net.esper.view.View;
import net.esper.event.EventBean;
import net.esper.collection.Pair;

// TODO: remove me if no longer needed
/**
 * Update dispatch views are required to indicate changes to listeners.
 */
public interface UpdateDispatchView extends View
{
    public void newResult(Pair<EventBean[], EventBean[]> result); 
}

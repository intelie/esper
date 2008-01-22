package net.esper.eql.view;

import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import net.esper.view.View;
import net.esper.collection.Pair;
import net.esper.core.UpdateDispatchView;

public interface OutputStrategy
{
    public void output(boolean forceUpdate, Pair<EventBean[], EventBean[]> result, UpdateDispatchView outputView);
}

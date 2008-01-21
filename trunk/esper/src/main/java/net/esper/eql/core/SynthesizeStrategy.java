package net.esper.eql.core;

import net.esper.event.EventBean;

public interface SynthesizeStrategy
{
    public boolean isSynthesize();
    public Object natural(EventBean[] eventsPerStream);
}

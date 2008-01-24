package net.esper.core;

import net.esper.event.EventBean;
import net.esper.collection.Pair;

public interface StatementResultNaturalStrategy 
{
    public void execute(Pair<EventBean[], EventBean[]> result);
}

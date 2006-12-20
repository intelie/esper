package net.esper.collection;

import net.esper.event.EventBean;
import net.esper.eql.core.ResultSetProcessor;

import java.util.Iterator;

public interface TransformEventMethod
{
    public EventBean transform(EventBean event);
}

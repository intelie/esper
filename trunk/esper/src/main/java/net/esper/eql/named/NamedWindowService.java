package net.esper.eql.named;

import net.esper.event.EventType;
import net.esper.core.EPStatementHandle;
import net.esper.view.ViewProcessingException;

import java.util.List;
import java.util.Map;

public interface NamedWindowService
{
    public boolean isNamedWindow(String name);
    public NamedWindowProcessor addProcessor(String name, EventType eventType) throws ViewProcessingException;
    public NamedWindowProcessor getProcessor(String name);

    public boolean dispatch();
    public void addDispatch(NamedWindowDeltaData delta, Map<EPStatementHandle,List<NamedWindowConsumerView>> consumers);
}

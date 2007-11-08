package net.esper.eql.named;

import net.esper.event.EventType;
import net.esper.core.EPStatementHandle;
import net.esper.view.ViewProcessingException;

import java.util.List;
import java.util.Map;

public interface NamedWindowService
{
    public final static String ERROR_MSG_DATAWINDOWS = "Named windows require one or more child views that are data window views";
    public final static String ERROR_MSG_NO_DATAWINDOW_ALLOWED = "Consuming statements to a named window cannot declare a data window view onto the named window";

    public boolean isNamedWindow(String name);
    public NamedWindowProcessor addProcessor(String name, EventType eventType) throws ViewProcessingException;
    public NamedWindowProcessor getProcessor(String name);

    public boolean dispatch();
    public void addDispatch(NamedWindowDeltaData delta, Map<EPStatementHandle,List<NamedWindowConsumerView>> consumers);
}

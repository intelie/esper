package net.esper.eql.named;

import net.esper.event.EventType;
import net.esper.eql.named.NamedWindowDeltaView;
import net.esper.eql.spec.OnDeleteDesc;
import net.esper.core.EPStatementHandle;
import net.esper.view.Viewable;
import net.esper.view.StatementStopService;

import java.util.List;
import java.util.Map;

public interface NamedWindowService
{
    public NamedWindowDeleteView addDeleter(OnDeleteDesc onDeleteDesc);
    public NamedWindowDeltaView addNamed(String name, Viewable eventStream, StatementStopService statementStopService);
    public NamedWindowConsumerView addConsumer(String windowName, EPStatementHandle statementHandle, StatementStopService statementStopService);

    public EventType getNamedWindowType(String eventName);

    public boolean dispatch();

    public void addDispatch(NamedWindowDeltaData delta, Map<EPStatementHandle,List<NamedWindowConsumerView>> consumers);
}

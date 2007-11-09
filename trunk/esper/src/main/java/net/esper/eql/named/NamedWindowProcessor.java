package net.esper.eql.named;

import net.esper.eql.spec.OnDeleteDesc;
import net.esper.view.StatementStopService;
import net.esper.event.EventType;
import net.esper.core.EPStatementHandle;

public class NamedWindowProcessor
{
    private final NamedWindowTailView tailView;
    private final NamedWindowRootView rootView;
    private final String windowName;
    private final EventType eventType;

    public NamedWindowProcessor(NamedWindowService namedWindowService, String windowName, EventType eventType)
    {
        this.windowName = windowName;
        this.eventType = eventType;

        rootView = new NamedWindowRootView();
        tailView = new NamedWindowTailView(eventType, namedWindowService, rootView);
        rootView.setDataWindowContents(tailView);   // for iteration used for delete without index
    }

    public NamedWindowTailView getTailView()
    {
        return tailView;    // hooked as the tail sview before any data windows
    }

    public NamedWindowRootView getRootView()
    {
        return rootView;    // hooked as the top view before any data windows
    }

    public NamedWindowDeleteView addDeleter(OnDeleteDesc onDeleteDesc, EventType filterEventType, StatementStopService statementStopService)
    {
        return rootView.addDeleter(onDeleteDesc, filterEventType, statementStopService);
    }

    public EventType getNamedWindowType(String eventName)
    {
        return eventType;
    }

    public NamedWindowConsumerView addConsumer(EPStatementHandle statementHandle, StatementStopService statementStopService)
    {
        return tailView.addConsumer(statementHandle, statementStopService);
    }

    public void destroy()
    {
        tailView.destroy();
        rootView.destroy();
    }
}

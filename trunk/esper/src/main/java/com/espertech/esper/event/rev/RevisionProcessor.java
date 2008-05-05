package com.espertech.esper.event.rev;

import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.named.NamedWindowIndexRepository;
import com.espertech.esper.epl.named.NamedWindowRootView;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.view.Viewable;
import com.espertech.esper.view.StatementStopService;
import com.espertech.esper.view.StatementStopCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public interface RevisionProcessor
{
    public EventType getEventType();
    public String getRevisionEventTypeAlias();

    // for use in checking insert-into
    public boolean validateRevisionableEventType(EventType eventType);

    // for use in executing insert-into
    public EventBean getRevision(EventBean event);

    // new events arriving, and remove stream data from on-delete statement
    public void onUpdate(EventBean[] newData, EventBean[] oldData, NamedWindowRootView namedWindowRootView, NamedWindowIndexRepository indexRepository);

    // iterator
    public Collection<EventBean> getSnapshot(EPStatementHandle createWindowStmtHandle, Viewable parent);

    // when the view expired events
    public void removeOldData(EventBean[] oldData, NamedWindowIndexRepository indexRepository);
}

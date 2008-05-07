package com.espertech.esper.event.rev;

import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.epl.named.NamedWindowIndexRepository;
import com.espertech.esper.epl.named.NamedWindowRootView;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.Viewable;

import java.util.Collection;

/**
 * Interface for a processor of base and delta events in a revision event type.
 */
public interface RevisionProcessor
{
    /**
     * Returns the event type that this revision processor generates.
     * @return event type
     */
    public EventType getEventType();

    /**
     * Returns the alias name of the revision event type.
     * @return name
     */
    public String getRevisionEventTypeAlias();

    /**
     * For use in checking insert-into statements, validates that the given type is eligible for revision event.
     * @param eventType the tyep of the event participating in revision event type (or not)
     * @return true if valid, false if not
     */
    public boolean validateRevisionableEventType(EventType eventType);

    /**
     * For use in executing an insert-into, wraps the given event applying the revision event type,
     * but not yet computing a new revision.
     * @param event to wrap
     * @return revision event bean
     */
    public EventBean getRevision(EventBean event);

    /**
     * Upon new events arriving into a named window (new data), and upon events being deleted
     * via on-delete (old data), update child views of the root view and apply to index repository as required (fast deletion).
     * @param newData new events
     * @param oldData remove stream
     * @param namedWindowRootView the root view
     * @param indexRepository delete and select indexes
     */
    public void onUpdate(EventBean[] newData, EventBean[] oldData, NamedWindowRootView namedWindowRootView, NamedWindowIndexRepository indexRepository);

    /**
     * Handle iteration over revision event contents.
     * @param createWindowStmtHandle statement handle for safe iteration
     * @param parent the provider of data
     * @return collection to iterate
     */
    public Collection<EventBean> getSnapshot(EPStatementHandle createWindowStmtHandle, Viewable parent);

    /**
     * Called each time a data window posts a remove stream event, to indicate that a data window
     * remove an event as it expired according to a specified expiration policy.
     * @param oldData to remove
     * @param indexRepository the indexes to update
     */
    public void removeOldData(EventBean[] oldData, NamedWindowIndexRepository indexRepository);
}

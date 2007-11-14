package net.esper.eql.named;

import net.esper.event.EventType;
import net.esper.core.EPStatementHandle;
import net.esper.view.ViewProcessingException;
import net.esper.util.ManagedLock;

import java.util.List;
import java.util.Map;

/**
 * Service to manage named window dispatches, locks and processors on an engine level.
 */
public interface NamedWindowService
{
    /**
     * Error message for data windows required.
     */
    public final static String ERROR_MSG_DATAWINDOWS = "Named windows require one or more child views that are data window views";

    /**
     * Error message for no data window allowed.
     */
    public final static String ERROR_MSG_NO_DATAWINDOW_ALLOWED = "Consuming statements to a named window cannot declare a data window view onto the named window";

    /**
     * Returns true to indicate that the name is a named window.
     * @param name is the window name
     * @return true if a named window, false if not a named window
     */
    public boolean isNamedWindow(String name);

    /**
     * Create a new named window.
     * @param name window name
     * @param eventType the event type of the window
     * @return processor for the named window
     * @throws ViewProcessingException if the named window already exists
     */
    public NamedWindowProcessor addProcessor(String name, EventType eventType) throws ViewProcessingException;

    /**
     * Returns the processing instance for a given named window.
     * @param name window name
     * @return processor for the named window
     */
    public NamedWindowProcessor getProcessor(String name);

    /**
     * Upon destroy of the named window creation statement, the named window processor must be removed.
     * @param name is the named window name
     */
    public void removeProcessor(String name);

    /**
     * Dispatch events of the insert and remove stream of named windows to consumers, as part of the
     * main event processing or dispatch loop.
     * @return send events to consuming statements
     */
    public boolean dispatch();

    /**
     * For use to add a result of a named window that must be dispatched to consuming views.
     * @param delta is the result to dispatch
     * @param consumers is the destination of the dispatch, a map of statements to one or more consuming views
     */
    public void addDispatch(NamedWindowDeltaData delta, Map<EPStatementHandle,List<NamedWindowConsumerView>> consumers);

    /**
     * Returns the statement lock for the named window, to be shared with on-delete statements for the same named window.
     * @param windowName is the window name
     * @return the lock for the named window, or null if the window dos not yet exists
     */
    public ManagedLock getNamedWindowLock(String windowName);

    /**
     * Sets the lock to use for a named window.
     * @param windowName is the named window name
     * @param statementResourceLock is the statement lock for the create window statement
     */
    public void addNamedWindowLock(String windowName, ManagedLock statementResourceLock);

    public void destroy();
}

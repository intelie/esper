package net.esper.eql.named;

import net.esper.eql.spec.OnTriggerDesc;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.expression.ExprNode;
import net.esper.view.StatementStopService;
import net.esper.event.EventType;
import net.esper.core.EPStatementHandle;
import net.esper.core.InternalEventRouter;

import java.util.List;

/**
 * An instance of this class is associated with a specific named window. The processor
 * provides the views to create-window, on-delete statements and statements selecting from a named window. 
 */
public class NamedWindowProcessor
{
    private final NamedWindowTailView tailView;
    private final NamedWindowRootView rootView;
    private final EventType eventType;

    /**
     * Ctor.
     * @param namedWindowService service for dispatching results
     * @param windowName the window name
     * @param eventType the type of event held by the named window
     * @param createWindowStmtHandle the statement handle of the statement that created the named window
     */
    public NamedWindowProcessor(NamedWindowService namedWindowService, String windowName, EventType eventType, EPStatementHandle createWindowStmtHandle)
    {
        this.eventType = eventType;

        rootView = new NamedWindowRootView();
        tailView = new NamedWindowTailView(eventType, namedWindowService, rootView, createWindowStmtHandle);
        rootView.setDataWindowContents(tailView);   // for iteration used for delete without index
    }

    /**
     * Returns the tail view of the named window, hooked into the view chain after the named window's data window views,
     * as the last view.
     * @return tail view
     */
    public NamedWindowTailView getTailView()
    {
        return tailView;    // hooked as the tail sview before any data windows
    }

    /**
     * Returns the root view of the named window, hooked into the view chain before the named window's data window views,
     * right after the filter stream that filters for insert-into events.
     * @return tail view
     */
    public NamedWindowRootView getRootView()
    {
        return rootView;    // hooked as the top view before any data windows
    }

    /**
     * Returns a new view for a new on-delete or on-select statement.
     * @param onTriggerDesc descriptor describing the on-trigger specification
     * @param filterEventType event type to trigger on
     * @param statementStopService to indicate a on-delete was stopped
     * @param internalEventRouter for insert-into handling
     * @param optionalResultSetProcessor for select-clause processing
     * @param statementHandle is the handle to the statement, used for routing/insert-into
     * @param joinExpr is the join expression or null if there is none
     * @return on trigger handling view
     */
    public NamedWindowOnExprBaseView addOnExpr(OnTriggerDesc onTriggerDesc, ExprNode joinExpr, EventType filterEventType, StatementStopService statementStopService, InternalEventRouter internalEventRouter, ResultSetProcessor optionalResultSetProcessor, EPStatementHandle statementHandle)
    {
        return rootView.addOnExpr(onTriggerDesc, joinExpr, filterEventType, statementStopService, internalEventRouter, optionalResultSetProcessor, statementHandle);
    }

    /**
     * Returns the event type of the named window.
     * @return event type
     */
    public EventType getNamedWindowType()
    {
        return eventType;
    }

    /**
     * Adds a consuming (selecting) statement to the named window.
     * @param statementHandle is the statement's handle for locking
     * @param statementStopService for indicating the consuming statement is stopped or destroyed
     * @param filterList is a list of filter expressions
     * @return consumer view
     */
    public NamedWindowConsumerView addConsumer(List<ExprNode> filterList, EPStatementHandle statementHandle, StatementStopService statementStopService)
    {
        return tailView.addConsumer(filterList, statementHandle, statementStopService);
    }

    /**
     * Deletes a named window and removes any associated resources.
     */
    public void destroy()
    {
        tailView.destroy();
        rootView.destroy();
    }
}

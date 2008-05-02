package com.espertech.esper.epl.named;

import com.espertech.esper.epl.spec.OnTriggerDesc;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.StatementStopService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.rev.RevisionProcessor;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.StatementResultService;

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
     * @param statementResultService for coordinating on whether insert and remove stream events should be posted
     */
    public NamedWindowProcessor(NamedWindowService namedWindowService, String windowName, EventType eventType, EPStatementHandle createWindowStmtHandle, StatementResultService statementResultService, RevisionProcessor revisionProcessor)
    {
        this.eventType = eventType;

        rootView = new NamedWindowRootView(revisionProcessor);
        tailView = new NamedWindowTailView(eventType, namedWindowService, rootView, createWindowStmtHandle, statementResultService, revisionProcessor);
        revisionProcessor.setOutputView(tailView);
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
     * @param resultSetProcessor for select-clause processing
     * @param statementHandle is the handle to the statement, used for routing/insert-into
     * @param joinExpr is the join expression or null if there is none
     * @param statementResultService for coordinating on whether insert and remove stream events should be posted
     * @return on trigger handling view
     */
    public NamedWindowOnExprBaseView addOnExpr(OnTriggerDesc onTriggerDesc, ExprNode joinExpr, EventType filterEventType, StatementStopService statementStopService, InternalEventRouter internalEventRouter, ResultSetProcessor resultSetProcessor, EPStatementHandle statementHandle, StatementResultService statementResultService)
    {
        return rootView.addOnExpr(onTriggerDesc, joinExpr, filterEventType, statementStopService, internalEventRouter, resultSetProcessor, statementHandle, statementResultService);
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

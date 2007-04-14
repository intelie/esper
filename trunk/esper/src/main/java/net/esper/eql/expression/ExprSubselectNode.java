package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.eql.spec.StatementSpecCompiled;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.event.EventBean;
import net.esper.view.Viewable;
import net.esper.view.internal.BufferView;
import net.esper.view.internal.BufferObserver;
import net.esper.collection.FlushedEventBuffer;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents a subselect in an expression tree.
 */
public class ExprSubselectNode extends ExprNode implements BufferObserver
{
    private static final Log log = LogFactory.getLog(ExprSubselectNode.class);
    
    private StatementSpecRaw statementSpecRaw;
    private StatementSpecCompiled statementSpecCompiled;
    private ExprNode selectClause;
    private String selectAsName;
    private Integer streamId;
    private UnindexedEventTable unindexedEventTable;
    private ExprNode filterExpr;

    /**
     * Ctor.
     */
    public ExprSubselectNode(StatementSpecRaw statementSpec)
    {
        this.statementSpecRaw = statementSpec;
    }

    public void setStatementSpecCompiled(StatementSpecCompiled statementSpecCompiled)
    {
        this.statementSpecCompiled = statementSpecCompiled;
    }

    public StatementSpecCompiled getStatementSpecCompiled()
    {
        return statementSpecCompiled;
    }

    public void setSubselectView(Viewable subselectView)
    {
        BufferView buffer = new BufferView(streamId);
        subselectView.addView(buffer);
        buffer.setObserver(this);
    }

    public void setSelectClause(ExprNode selectClause)
    {
        this.selectClause = selectClause;
    }

    public void setStreamId(int streamId)
    {
        this.streamId = streamId;
        unindexedEventTable = new UnindexedEventTable(streamId);
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
    }

    public StatementSpecRaw getStatementSpecRaw()
    {
        return statementSpecRaw;
    }

    public void setSelectAsName(String selectAsName)
    {
        this.selectAsName = selectAsName;
    }

    public void setFilterExpr(ExprNode filterExpr)
    {
        this.filterExpr = filterExpr;
    }

    public Class getType() throws ExprValidationException
    {
        return selectClause.getType();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Set<EventBean> matchingEvents = unindexedEventTable.getEventSet();
        if (matchingEvents.size() == 0)
        {
            return null;
        }
        if ((filterExpr == null) && (matchingEvents.size() > 1))
        {
            log.warn("Subselect returned more then one row in subselect '" + toExpressionString() + "', returning null result");
            return null;
        }

        // Evaluate filter
        EventBean subSelectResult = null;
        EventBean[] events = new EventBean[eventsPerStream.length + 1];
        System.arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

        if (filterExpr != null)
        {
            for (EventBean subselectEvent : matchingEvents)
            {
                // Prepare filter expression event list
                events[0] = subselectEvent;

                Boolean pass = (Boolean) filterExpr.evaluate(events, true);
                if ((pass != null) && (pass))
                {
                    if (subSelectResult != null)
                    {
                        log.warn("Subselect returned more then one row in subselect '" + toExpressionString() + "', returning null result");
                        return null;
                    }
                    subSelectResult = subselectEvent;
                }
            }

            if (subSelectResult == null)
            {
                return null;
            }
        }
        else
        {
            subSelectResult = matchingEvents.iterator().next();
        }

        events[0] = subSelectResult; 
        Object result = selectClause.evaluate(events, true);
        return result;
    }

    public String toExpressionString()
    {
        if (selectAsName != null)
        {
            return selectAsName;
        }
        return selectClause.toExpressionString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprSubselectNode))
        {
            return false;
        }

        return true;
    }

    public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
    {
        unindexedEventTable.remove(oldEventBuffer.getAndFlush());
        unindexedEventTable.add(newEventBuffer.getAndFlush());
    }
}

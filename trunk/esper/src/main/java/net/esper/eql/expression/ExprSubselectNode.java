package net.esper.eql.expression;

import net.esper.collection.FlushedEventBuffer;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.spec.StatementSpecCompiled;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.view.Viewable;
import net.esper.view.internal.BufferObserver;
import net.esper.view.internal.BufferView;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * Represents a subselect in an expression tree.
 */
public abstract class ExprSubselectNode extends ExprNode implements BufferObserver
{
    private static final Log log = LogFactory.getLog(ExprSubselectNode.class);
    
    protected ExprNode selectClause;
    protected ExprNode filterExpr;

    private StatementSpecRaw statementSpecRaw;
    private StatementSpecCompiled statementSpecCompiled;
    private String selectAsName;
    private Integer streamId;
    private UnindexedEventTable unindexedEventTable;

    public abstract Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents);
    public abstract boolean isAllowWildcardSelect();

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

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Set<EventBean> matchingEvents = unindexedEventTable.getEventSet();
        return evaluate(eventsPerStream, isNewData, matchingEvents);
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
        return false;   // 2 subselects are never equivalent
    }

    public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
    {
        unindexedEventTable.remove(oldEventBuffer.getAndFlush());
        unindexedEventTable.add(newEventBuffer.getAndFlush());
    }
}

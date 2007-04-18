package net.esper.eql.expression;

import net.esper.eql.spec.StatementSpecCompiled;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.eql.subquery.SubqueryTableLookupStrategy;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

/**
 * Represents a subselect in an expression tree.
 */
public abstract class ExprSubselectNode extends ExprNode
{
    private static final Log log = LogFactory.getLog(ExprSubselectNode.class);

    /**
     * The validated select clause.
     */
    protected ExprNode selectClause;

    /**
     * The validate filter expression.
     */
    protected ExprNode filterExpr;

    private StatementSpecRaw statementSpecRaw;
    private StatementSpecCompiled statementSpecCompiled;
    private SubqueryTableLookupStrategy strategy;
    private String selectAsName;

    /**
     * Evaluate the subquery expression returning an evaluation result object.
     * @param eventsPerStream is the events for each stream in a join
     * @param isNewData is true for new data, or false for old data
     * @param matchingEvents is filtered results from the table of stored subquery events
     * @return evaluation result
     */
    public abstract Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents);

    /**
     * Return true to indicate that wildcard selects are acceptable, or false to indicate wildcard is not acceptable 
     * @return true for yes-wildcards, false for no-wildcards
     */
    public abstract boolean isAllowWildcardSelect();

    /**
     * Ctor.
     * @param statementSpec is the subquery statement spec from the parser, unvalidated
     */
    public ExprSubselectNode(StatementSpecRaw statementSpec)
    {
        this.statementSpecRaw = statementSpec;
    }

    public boolean isConstantResult()
    {
        if (selectClause != null)
        {
            return selectClause.isConstantResult();
        }
        return false;
    }

    /**
     * Supplies a compiled statement spec.
     * @param statementSpecCompiled compiled validated filters
     */
    public void setStatementSpecCompiled(StatementSpecCompiled statementSpecCompiled)
    {
        this.statementSpecCompiled = statementSpecCompiled;
    }

    /**
     * Returns the compiled statement spec.
     * @return compiled statement
     */
    public StatementSpecCompiled getStatementSpecCompiled()
    {
        return statementSpecCompiled;
    }

    /**
     * Sets the validate select clause
     * @param selectClause is the expression representing the select clause
     */
    public void setSelectClause(ExprNode selectClause)
    {
        this.selectClause = selectClause;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Set<EventBean> matchingEvents = strategy.lookup(eventsPerStream);
        return evaluate(eventsPerStream, isNewData, matchingEvents);
    }

    /**
     * Returns the uncompiled statement spec.
     * @return statement spec uncompiled
     */
    public StatementSpecRaw getStatementSpecRaw()
    {
        return statementSpecRaw;
    }

    /**
     * Supplies the name of the select expression as-tag
     * @param selectAsName is the as-name
     */
    public void setSelectAsName(String selectAsName)
    {
        this.selectAsName = selectAsName;
    }

    /**
     * Sets the validated filter expression, or null if there is none.
     * @param filterExpr is the filter
     */
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

    /**
     * Sets the strategy for boiling down the table of subquery events into a subset against which to run the filter.
     * @param strategy is the looking strategy (full table scan or indexed)
     */
    public void setStrategy(SubqueryTableLookupStrategy strategy)
    {
        this.strategy = strategy;
    }
}

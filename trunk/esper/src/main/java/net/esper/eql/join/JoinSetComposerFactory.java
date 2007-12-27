package net.esper.eql.join;

import net.esper.eql.spec.OuterJoinDesc;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.expression.ExprEqualsNode;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.join.table.PropertyIndTableCoerceAll;
import net.esper.eql.join.plan.QueryPlan;
import net.esper.eql.join.plan.QueryPlanBuilder;
import net.esper.eql.join.plan.QueryPlanIndex;
import net.esper.eql.join.plan.QueryPlanNode;
import net.esper.eql.join.exec.ExecNode;
import net.esper.event.EventType;
import net.esper.view.Viewable;
import net.esper.view.HistoricalEventViewable;
import net.esper.type.OuterJoinType;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for building a {@link JoinSetComposer} implementations from analyzing filter nodes, for
 * fast join tuple result set composition.
 */
public interface JoinSetComposerFactory
{
    /**
     * Builds join tuple composer.
     * @param outerJoinDescList - list of descriptors for outer join criteria
     * @param optionalFilterNode - filter tree for analysis to build indexes for fast access
     * @param streamTypes - types of streams
     * @param streamNames - names of streams
     * @param streamViews - leaf view per stream
     * @param selectStreamSelectorEnum - indicator for rstream or istream-only, for optimization
     * @return composer implementation
     * @throws ExprValidationException is thrown to indicate that
     * validation of view use in joins failed.
     */
    public JoinSetComposer makeComposer(List<OuterJoinDesc> outerJoinDescList,
                                                   ExprNode optionalFilterNode,
                                                   EventType[] streamTypes,
                                                   String[] streamNames,
                                                   Viewable[] streamViews,
                                                   SelectClauseStreamSelectorEnum selectStreamSelectorEnum)
            throws ExprValidationException;
}

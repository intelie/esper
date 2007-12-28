package net.esper.eql.join;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.OuterJoinDesc;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.event.EventType;
import net.esper.view.Viewable;

import java.util.List;

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
     * @param isUnidirectional is an array of indicators for each stream set to true for a unidirectional stream in a join
     * @param hasChildViews indicates if child views are declared for a stream
     * @return composer implementation
     * @throws ExprValidationException is thrown to indicate that
     * validation of view use in joins failed.
     */
    public JoinSetComposer makeComposer(List<OuterJoinDesc> outerJoinDescList,
                                                   ExprNode optionalFilterNode,
                                                   EventType[] streamTypes,
                                                   String[] streamNames,
                                                   Viewable[] streamViews,
                                                   SelectClauseStreamSelectorEnum selectStreamSelectorEnum,
                                                   boolean[] isUnidirectional,
                                                   boolean[] hasChildViews)
            throws ExprValidationException;
}

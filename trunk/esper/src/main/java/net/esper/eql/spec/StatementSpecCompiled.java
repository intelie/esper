package net.esper.eql.spec;

import net.esper.collection.Pair;
import net.esper.eql.expression.ExprNode;

import java.util.List;

/**
 * Specification object representing a complete EQL statement including all EQL constructs.
 */
public class StatementSpecCompiled
{
    private final InsertIntoDesc insertIntoDesc;
    private final SelectClauseStreamSelectorEnum selectStreamDirEnum;
    private final SelectClauseSpec selectClauseSpec;
    private final List<StreamSpecCompiled> streamSpecs;
    private final List<OuterJoinDesc> outerJoinDescList;
    private ExprNode filterExprRootNode;
    private final List<ExprNode> groupByExpressions;
    private final ExprNode havingExprRootNode;
    private final OutputLimitSpec outputLimitSpec;
    private final List<Pair<ExprNode, Boolean>> orderByList;

    /**
     * Ctor.
     * @param insertIntoDesc insert into def
     * @param selectClauseStreamSelectorEnum stream selection
     * @param selectClauseSpec select clause
     * @param streamSpecs specs for streams
     * @param outerJoinDescList outer join def
     * @param filterExprRootNode where filter expr nodes
     * @param groupByExpressions group by expression
     * @param havingExprRootNode having expression
     * @param outputLimitSpec output limit
     * @param orderByList order by
     */
    public StatementSpecCompiled(InsertIntoDesc insertIntoDesc, SelectClauseStreamSelectorEnum selectClauseStreamSelectorEnum, SelectClauseSpec selectClauseSpec, List<StreamSpecCompiled> streamSpecs, List<OuterJoinDesc> outerJoinDescList, ExprNode filterExprRootNode, List<ExprNode> groupByExpressions, ExprNode havingExprRootNode, OutputLimitSpec outputLimitSpec, List<Pair<ExprNode, Boolean>> orderByList)
    {
        this.insertIntoDesc = insertIntoDesc;
        this.selectStreamDirEnum = selectClauseStreamSelectorEnum;
        this.selectClauseSpec = selectClauseSpec;
        this.streamSpecs = streamSpecs;
        this.outerJoinDescList = outerJoinDescList;
        this.filterExprRootNode = filterExprRootNode;
        this.groupByExpressions = groupByExpressions;
        this.havingExprRootNode = havingExprRootNode;
        this.outputLimitSpec = outputLimitSpec;
        this.orderByList = orderByList;
    }

    /**
     * Returns the FROM-clause stream definitions.
     * @return list of stream specifications
     */
    public List<StreamSpecCompiled> getStreamSpecs()
    {
        return streamSpecs;
    }

    /**
     * Returns SELECT-clause list of expressions.
     * @return list of expressions and optional name
     */
    public SelectClauseSpec getSelectClauseSpec()
    {
        return selectClauseSpec;
    }

    /**
     * Returns the WHERE-clause root node of filter expression.
     * @return filter expression root node
     */
    public ExprNode getFilterRootNode()
    {
        return filterExprRootNode;
    }

    /**
     * Returns the LEFT/RIGHT/FULL OUTER JOIN-type and property name descriptor, if applicable. Returns null if regular join.
     * @return outer join type, stream names and property names
     */
    public List<OuterJoinDesc> getOuterJoinDescList()
    {
        return outerJoinDescList;
    }

    /**
     * Returns list of group-by expressions.
     * @return group-by expression nodes as specified in group-by clause
     */
    public List<ExprNode> getGroupByExpressions()
    {
        return groupByExpressions;
    }

    /**
     * Returns expression root node representing the having-clause, if present, or null if no having clause was supplied.
     * @return having-clause expression top node
     */
    public ExprNode getHavingExprRootNode()
    {
        return havingExprRootNode;
    }

    /**
     * Returns the output limit definition, if any.
     * @return output limit spec
     */
    public OutputLimitSpec getOutputLimitSpec()
    {
        return outputLimitSpec;
    }

    /**
     * Return a descriptor with the insert-into event name and optional list of columns.
     * @return insert into specification
     */
    public InsertIntoDesc getInsertIntoDesc()
    {
        return insertIntoDesc;
    }

    /**
     * Returns the list of order-by expression as specified in the ORDER BY clause.
     * @return Returns the orderByList.
     */
    public List<Pair<ExprNode, Boolean>> getOrderByList() {
        return orderByList;
    }

    /**
     * Returns the stream selector (rstream/istream).
     * @return stream selector
     */
    public SelectClauseStreamSelectorEnum getSelectStreamSelectorEnum()
    {
        return selectStreamDirEnum;
    }

    /**
     * Set the where clause filter node.
     * @param optionalFilterNode is the where-clause filter node
     */
    public void setFilterExprRootNode(ExprNode optionalFilterNode)
    {
        filterExprRootNode = optionalFilterNode;
    }
}

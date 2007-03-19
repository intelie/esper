/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;
import net.esper.collection.Pair;
import net.esper.util.MetaDefItem;

import java.util.List;
import java.util.LinkedList;

/**
 * Specification object representing a complete EQL statement including all EQL constructs.
 */
public class StatementSpecRaw implements MetaDefItem
{
    private InsertIntoDesc insertIntoDesc;
    private SelectClauseStreamSelectorEnum selectStreamDirEnum = SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH;
    private SelectClauseSpec selectClauseSpec = new SelectClauseSpec();
    private List<StreamSpecRaw> streamSpecs = new LinkedList<StreamSpecRaw>();
    private List<OuterJoinDesc> outerJoinDescList = new LinkedList<OuterJoinDesc>();
    private ExprNode filterExprRootNode;
    private List<ExprNode> groupByExpressions = new LinkedList<ExprNode>();
    private ExprNode havingExprRootNode;
    private OutputLimitSpec outputLimitSpec;
    private List<Pair<ExprNode, Boolean>> orderByList = new LinkedList<Pair<ExprNode, Boolean>>();

    /**
     * Returns the FROM-clause stream definitions.
     * @return list of stream specifications
     */
    public List<StreamSpecRaw> getStreamSpecs()
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
     * Sets the output limiting definition.
     * @param outputLimitSpec defines the rules for output limiting
     */
    public void setOutputLimitSpec(OutputLimitSpec outputLimitSpec)
    {
        this.outputLimitSpec = outputLimitSpec;
    }

    /**
     * Sets the where clause filter expression node.
     * @param filterExprRootNode the where clause expression
     */
    public void setFilterExprRootNode(ExprNode filterExprRootNode)
    {
        this.filterExprRootNode = filterExprRootNode;
    }

    /**
     * Sets the having-clause filter expression node.
     * @param havingExprRootNode the having-clause expression
     */
    public void setHavingExprRootNode(ExprNode havingExprRootNode)
    {
        this.havingExprRootNode = havingExprRootNode;
    }

    /**
     * Sets the definition for any insert-into clause.
     * @param insertIntoDesc is the descriptor for insert-into rules
     */
    public void setInsertIntoDesc(InsertIntoDesc insertIntoDesc)
    {
        this.insertIntoDesc = insertIntoDesc;
    }

    /**
     * Sets the stream selector (rstream/istream/both etc).
     * @param selectStreamDirEnum to be set
     */
    public void setSelectStreamDirEnum(SelectClauseStreamSelectorEnum selectStreamDirEnum)
    {
        this.selectStreamDirEnum = selectStreamDirEnum;
    }
}

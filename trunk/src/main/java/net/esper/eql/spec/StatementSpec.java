package net.esper.eql.spec;

import net.esper.collection.Pair;
import net.esper.eql.spec.StreamSpec;
import net.esper.eql.expression.ExprNode;

import java.util.List;
import java.util.LinkedList;

public class StatementSpec
{
    private InsertIntoDesc insertIntoDesc;
    private List<SelectExprElementSpec> selectListExpressions = new LinkedList<SelectExprElementSpec>();
    private List<StreamSpec> streamSpecs = new LinkedList<StreamSpec>();
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
    public List<StreamSpec> getStreamSpecs()
    {
        return streamSpecs;
    }

    /**
     * Returns SELECT-clause list of expressions.
     * @return list of expressions and optional name
     */
    public List<SelectExprElementSpec> getSelectListExpressions()
    {
        return selectListExpressions;
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

    public void setOutputLimitSpec(OutputLimitSpec outputLimitSpec)
    {
        this.outputLimitSpec = outputLimitSpec;
    }

    public void setFilterExprRootNode(ExprNode filterExprRootNode)
    {
        this.filterExprRootNode = filterExprRootNode;
    }

    public void setHavingExprRootNode(ExprNode havingExprRootNode)
    {
        this.havingExprRootNode = havingExprRootNode;
    }

    public void setInsertIntoDesc(InsertIntoDesc insertIntoDesc)
    {
        this.insertIntoDesc = insertIntoDesc;
    }
}

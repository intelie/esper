///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.collection;
using net.esper.eql.expression;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Specification object representing a complete EQL statement including all EQL constructs.
	/// </summary>
	public class StatementSpecCompiled
	{
	    private readonly InsertIntoDesc insertIntoDesc;
	    private readonly SelectClauseStreamSelectorEnum selectStreamDirEnum;
	    private readonly SelectClauseSpec selectClauseSpec;
	    private readonly List<StreamSpecCompiled> streamSpecs;
	    private readonly List<OuterJoinDesc> outerJoinDescList;
	    private ExprNode filterExprRootNode;
	    private readonly List<ExprNode> groupByExpressions;
	    private readonly ExprNode havingExprRootNode;
	    private readonly OutputLimitSpec outputLimitSpec;
	    private readonly List<Pair<ExprNode, Boolean>> orderByList;
	    private readonly List<ExprSubselectNode> subSelectExpressions;

	    /// <summary>Ctor.</summary>
	    /// <param name="insertIntoDesc">insert into def</param>
	    /// <param name="selectClauseStreamSelectorEnum">stream selection</param>
	    /// <param name="selectClauseSpec">select clause</param>
	    /// <param name="streamSpecs">specs for streams</param>
	    /// <param name="outerJoinDescList">outer join def</param>
	    /// <param name="filterExprRootNode">where filter expr nodes</param>
	    /// <param name="groupByExpressions">group by expression</param>
	    /// <param name="havingExprRootNode">having expression</param>
	    /// <param name="outputLimitSpec">output limit</param>
	    /// <param name="orderByList">order by</param>
	    /// <param name="subSelectExpressions">list of subqueries</param>
	    public StatementSpecCompiled(InsertIntoDesc insertIntoDesc, SelectClauseStreamSelectorEnum selectClauseStreamSelectorEnum, SelectClauseSpec selectClauseSpec, List<StreamSpecCompiled> streamSpecs, List<OuterJoinDesc> outerJoinDescList, ExprNode filterExprRootNode, List<ExprNode> groupByExpressions, ExprNode havingExprRootNode, OutputLimitSpec outputLimitSpec, List<Pair<ExprNode, Boolean>> orderByList,
	                                 List<ExprSubselectNode> subSelectExpressions)
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
	        this.subSelectExpressions = subSelectExpressions;
	    }

	    /// <summary>Returns the FROM-clause stream definitions.</summary>
	    /// <returns>list of stream specifications</returns>
	    public List<StreamSpecCompiled> GetStreamSpecs()
	    {
	        return streamSpecs;
	    }

	    /// <summary>Returns SELECT-clause list of expressions.</summary>
	    /// <returns>list of expressions and optional name</returns>
	    public SelectClauseSpec GetSelectClauseSpec()
	    {
	        return selectClauseSpec;
	    }

	    /// <summary>Returns the WHERE-clause root node of filter expression.</summary>
	    /// <returns>filter expression root node</returns>
	    public ExprNode GetFilterRootNode()
	    {
	        return filterExprRootNode;
	    }

	    /// <summary>
	    /// Returns the LEFT/RIGHT/FULL OUTER JOIN-type and property name descriptor, if applicable. Returns null if regular join.
	    /// </summary>
	    /// <returns>outer join type, stream names and property names</returns>
	    public List<OuterJoinDesc> GetOuterJoinDescList()
	    {
	        return outerJoinDescList;
	    }

	    /// <summary>Returns list of group-by expressions.</summary>
	    /// <returns>group-by expression nodes as specified in group-by clause</returns>
	    public List<ExprNode> GetGroupByExpressions()
	    {
	        return groupByExpressions;
	    }

	    /// <summary>
	    /// Returns expression root node representing the having-clause, if present, or null if no having clause was supplied.
	    /// </summary>
	    /// <returns>having-clause expression top node</returns>
	    public ExprNode GetHavingExprRootNode()
	    {
	        return havingExprRootNode;
	    }

	    /// <summary>Returns the output limit definition, if any.</summary>
	    /// <returns>output limit spec</returns>
	    public OutputLimitSpec GetOutputLimitSpec()
	    {
	        return outputLimitSpec;
	    }

	    /// <summary>
	    /// Return a descriptor with the insert-into event name and optional list of columns.
	    /// </summary>
	    /// <returns>insert into specification</returns>
	    public InsertIntoDesc GetInsertIntoDesc()
	    {
	        return insertIntoDesc;
	    }

	    /// <summary>
	    /// Returns the list of order-by expression as specified in the ORDER BY clause.
	    /// </summary>
	    /// <returns>Returns the orderByList.</returns>
	    public List<Pair<ExprNode, Boolean>> GetOrderByList() {
	        return orderByList;
	    }

	    /// <summary>Returns the stream selector (rstream/istream).</summary>
	    /// <returns>stream selector</returns>
	    public SelectClauseStreamSelectorEnum GetSelectStreamSelectorEnum()
	    {
	        return selectStreamDirEnum;
	    }

	    /// <summary>Set the where clause filter node.</summary>
	    /// <param name="optionalFilterNode">is the where-clause filter node</param>
	    public void SetFilterExprRootNode(ExprNode optionalFilterNode)
	    {
	        filterExprRootNode = optionalFilterNode;
	    }

	    /// <summary>Returns the list of subquery expression nodes.</summary>
	    /// <returns>subquery nodes</returns>
	    public List<ExprSubselectNode> GetSubSelectExpressions()
	    {
	        return subSelectExpressions;
	    }
	}
} // End of namespace

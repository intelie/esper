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
using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Specification object representing a complete EQL statement including all EQL constructs.
	/// </summary>
	public class StatementSpecRaw : MetaDefItem
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

	    /// <summary>Returns the FROM-clause stream definitions.</summary>
	    /// <returns>list of stream specifications</returns>
	    public List<StreamSpecRaw> GetStreamSpecs()
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

	    /// <summary>Sets the output limiting definition.</summary>
	    /// <param name="outputLimitSpec">defines the rules for output limiting</param>
	    public void SetOutputLimitSpec(OutputLimitSpec outputLimitSpec)
	    {
	        this.outputLimitSpec = outputLimitSpec;
	    }

	    /// <summary>Sets the where clause filter expression node.</summary>
	    /// <param name="filterExprRootNode">the where clause expression</param>
	    public void SetFilterExprRootNode(ExprNode filterExprRootNode)
	    {
	        this.filterExprRootNode = filterExprRootNode;
	    }

	    /// <summary>Sets the having-clause filter expression node.</summary>
	    /// <param name="havingExprRootNode">the having-clause expression</param>
	    public void SetHavingExprRootNode(ExprNode havingExprRootNode)
	    {
	        this.havingExprRootNode = havingExprRootNode;
	    }

	    /// <summary>Sets the definition for any insert-into clause.</summary>
	    /// <param name="insertIntoDesc">is the descriptor for insert-into rules</param>
	    public void SetInsertIntoDesc(InsertIntoDesc insertIntoDesc)
	    {
	        this.insertIntoDesc = insertIntoDesc;
	    }

	    /// <summary>Sets the stream selector (rstream/istream/both etc).</summary>
	    /// <param name="selectStreamDirEnum">to be set</param>
	    public void SetSelectStreamDirEnum(SelectClauseStreamSelectorEnum selectStreamDirEnum)
	    {
	        this.selectStreamDirEnum = selectStreamDirEnum;
	    }
	}
} // End of namespace

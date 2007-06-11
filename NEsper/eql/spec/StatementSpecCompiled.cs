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
	    private readonly IList<StreamSpecCompiled> streamSpecs;
	    private readonly IList<OuterJoinDesc> outerJoinDescList;
	    private ExprNode filterExprRootNode;
	    private readonly IList<ExprNode> groupByExpressions;
	    private readonly ExprNode havingExprRootNode;
	    private readonly OutputLimitSpec outputLimitSpec;
	    private readonly IList<Pair<ExprNode, Boolean>> orderByList;
	    private readonly IList<ExprSubselectNode> subSelectExpressions;

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
	    public StatementSpecCompiled(InsertIntoDesc insertIntoDesc,
	                                 SelectClauseStreamSelectorEnum selectClauseStreamSelectorEnum,
	                                 SelectClauseSpec selectClauseSpec,
	                                 IList<StreamSpecCompiled> streamSpecs,
	                                 IList<OuterJoinDesc> outerJoinDescList, 
	                                 ExprNode filterExprRootNode, 
	                                 IList<ExprNode> groupByExpressions, 
	                                 ExprNode havingExprRootNode, 
	                                 OutputLimitSpec outputLimitSpec,
	                                 IList<Pair<ExprNode, Boolean>> orderByList,
	                                 IList<ExprSubselectNode> subSelectExpressions)
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
	    public IList<StreamSpecCompiled> StreamSpecs
	    {
	    	get { return streamSpecs; }
	    }

	    /// <summary>Returns SELECT-clause list of expressions.</summary>
	    /// <returns>list of expressions and optional name</returns>
	    public SelectClauseSpec SelectClauseSpec
	    {
	    	get { return selectClauseSpec; }
	    }

	    /// <summary>Gets or sets the WHERE-clause root node of filter expression.</summary>
	    /// <returns>filter expression root node</returns>
	    public ExprNode FilterExprRootNode
	    {
	    	get { return filterExprRootNode; }
	    	set { filterExprRootNode = value ; }
	    }

	    /// <summary>
	    /// Returns the LEFT/RIGHT/FULL OUTER JOIN-type and property name descriptor, if applicable. Returns null if regular join.
	    /// </summary>
	    /// <returns>outer join type, stream names and property names</returns>
	    public IList<OuterJoinDesc> OuterJoinDescList
	    {
	    	get { return outerJoinDescList; }
	    }

	    /// <summary>Returns list of group-by expressions.</summary>
	    /// <returns>group-by expression nodes as specified in group-by clause</returns>
	    public IList<ExprNode> GroupByExpressions
	    {
	    	get { return groupByExpressions; }
	    }

	    /// <summary>
	    /// Returns expression root node representing the having-clause, if present, or null if no having clause was supplied.
	    /// </summary>
	    /// <returns>having-clause expression top node</returns>
	    public ExprNode HavingExprRootNode
	    {
	    	get { return havingExprRootNode; }
	    }

	    /// <summary>Returns the output limit definition, if any.</summary>
	    /// <returns>output limit spec</returns>
	    public OutputLimitSpec OutputLimitSpec
	    {
	    	get { return outputLimitSpec; }
	    }

	    /// <summary>
	    /// Return a descriptor with the insert-into event name and optional list of columns.
	    /// </summary>
	    /// <returns>insert into specification</returns>
	    public InsertIntoDesc InsertIntoDesc
	    {
	    	get { return insertIntoDesc; }
	    }

	    /// <summary>
	    /// Returns the list of order-by expression as specified in the ORDER BY clause.
	    /// </summary>
	    /// <returns>Returns the orderByList.</returns>
	    public IList<Pair<ExprNode, bool>> OrderByList
	    {
	    	get { return orderByList; }
	    }

	    /// <summary>Returns the stream selector (rstream/istream).</summary>
	    /// <returns>stream selector</returns>
	    public SelectClauseStreamSelectorEnum SelectStreamSelectorEnum
	    {
	    	get { return selectStreamDirEnum; }
	    }

	    /// <summary>Returns the list of subquery expression nodes.</summary>
	    /// <returns>subquery nodes</returns>
	    public IList<ExprSubselectNode> SubSelectExpressions
	    {
	    	get { return subSelectExpressions; }
	    }
	}
} // End of namespace

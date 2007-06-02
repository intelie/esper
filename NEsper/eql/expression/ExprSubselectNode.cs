///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.eql.subquery;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents a subselect in an expression tree.
    /// </summary>
	public abstract class ExprSubselectNode : ExprNode
	{
	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>The validated select clause.</summary>
	    protected ExprNode selectClause;

	    /// <summary>The validate filter expression.</summary>
	    protected ExprNode filterExpr;

	    private StatementSpecRaw statementSpecRaw;
	    private StatementSpecCompiled statementSpecCompiled;
	    private SubqueryTableLookupStrategy strategy;
	    private String selectAsName;

	    /// <summary>
	    /// Evaluate the subquery expression returning an evaluation result object.
	    /// </summary>
	    /// <param name="eventsPerStream">is the events for each stream in a join</param>
	    /// <param name="isNewData">is true for new data, or false for old data</param>
	    /// <param name="matchingEvents">
	    /// is filtered results from the table of stored subquery events
	    /// </param>
	    /// <returns>evaluation result</returns>
	    public abstract Object Evaluate(EventBean[] eventsPerStream, bool isNewData, ISet<EventBean> matchingEvents);

	    /// <summary>
	    /// Return true to indicate that wildcard selects are acceptable, or false to indicate wildcard is not acceptable
	    /// </summary>
	    /// <returns>true for yes-wildcards, false for no-wildcards</returns>
        public abstract bool IsAllowWildcardSelect { get; }

	    /// <summary>Ctor.</summary>
	    /// <param name="statementSpec">
	    /// is the subquery statement spec from the parser, unvalidated
	    /// </param>
	    public ExprSubselectNode(StatementSpecRaw statementSpec)
	    {
	        this.statementSpecRaw = statementSpec;
	    }

	    public bool IsConstantResult()
	    {
	        if (selectClause != null)
	        {
	            return selectClause.IsConstantResult();
	        }
	        return false;
	    }

	    /// <summary>Supplies a compiled statement spec.</summary>
	    /// <param name="statementSpecCompiled">compiled validated filters</param>
	    public void SetStatementSpecCompiled(StatementSpecCompiled statementSpecCompiled)
	    {
	        this.statementSpecCompiled = statementSpecCompiled;
	    }

	    /// <summary>Returns the compiled statement spec.</summary>
	    /// <returns>compiled statement</returns>
	    public StatementSpecCompiled GetStatementSpecCompiled()
	    {
	        return statementSpecCompiled;
	    }

	    /// <summary>Sets the validate select clause</summary>
	    /// <param name="selectClause">is the expression representing the select clause</param>
	    public void SetSelectClause(ExprNode selectClause)
	    {
	        this.selectClause = selectClause;
	    }

	    public Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
	    {
	        Set<EventBean> matchingEvents = strategy.Lookup(eventsPerStream);
	        return Evaluate(eventsPerStream, isNewData, matchingEvents);
	    }

	    /// <summary>Returns the uncompiled statement spec.</summary>
	    /// <returns>statement spec uncompiled</returns>
	    public StatementSpecRaw GetStatementSpecRaw()
	    {
	        return statementSpecRaw;
	    }

	    /// <summary>Supplies the name of the select expression as-tag</summary>
	    /// <param name="selectAsName">is the as-name</param>
	    public void SetSelectAsName(String selectAsName)
	    {
	        this.selectAsName = selectAsName;
	    }

	    /// <summary>Sets the validated filter expression, or null if there is none.</summary>
	    /// <param name="filterExpr">is the filter</param>
	    public void SetFilterExpr(ExprNode filterExpr)
	    {
	        this.filterExpr = filterExpr;
	    }

	    public String ToExpressionString()
	    {
	        if (selectAsName != null)
	        {
	            return selectAsName;
	        }
	        return selectClause.ToExpressionString();
	    }

	    public bool EqualsNode(ExprNode node)
	    {
	        return false;   // 2 subselects are never equivalent
	    }

	    /// <summary>
	    /// Sets the strategy for boiling down the table of subquery events into a subset against which to run the filter.
	    /// </summary>
	    /// <param name="strategy">is the looking strategy (full table scan or indexed)</param>
	    public void SetStrategy(SubqueryTableLookupStrategy strategy)
	    {
	        this.strategy = strategy;
	    }
	}
} // End of namespace

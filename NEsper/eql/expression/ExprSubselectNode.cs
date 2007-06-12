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
	    public abstract Object Evaluate(EventBean[] eventsPerStream, bool isNewData, Set<EventBean> matchingEvents);

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

        /// <summary>
        /// Returns true if the expression node's evaluation value doesn't depend on any events data,
        /// as must be determined at validation time, which is bottom-up and therefore
        /// reliably allows each node to determine constant value.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// true for constant evaluation value, false for non-constant evaluation value
        /// </returns>
	    public override bool IsConstantResult
	    {
	    	get
	    	{
		        if (selectClause != null)
		        {
		            return selectClause.IsConstantResult;
		        }
		        return false;
	    	}
	    }

        /// <summary>
        /// Gets or sets the compiled statement spec.
        /// </summary>
        /// <value>The statement spec compiled.</value>
	    public StatementSpecCompiled StatementSpecCompiled
	    {
	    	get { return this.statementSpecCompiled; }
	    	set { this.statementSpecCompiled = value ; }
	    }

        /// <summary>
        /// Gets or sets the validate select clause
        /// </summary>
        /// <value>The select clause.</value>
	    public ExprNode SelectClause
	    {
	    	get { return this.selectClause ; }
	    	set { this.selectClause = value ; }
	    }

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <param name="isNewData">indicates whether we are dealing with new data (istream) or old data (rstream)</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
	    public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData)
	    {
	        Set<EventBean> matchingEvents = strategy.Lookup(eventsPerStream);
	        return Evaluate(eventsPerStream, isNewData, matchingEvents);
	    }

        /// <summary>
        /// Returns the uncompiled statement spec.
        /// </summary>
        /// <value>The statement spec raw.</value>
	    public StatementSpecRaw StatementSpecRaw
	    {
	    	get { return statementSpecRaw; }
	    }

        /// <summary>
        /// Gets or sets the name of the select expression as-tag
        /// </summary>
        /// <value>The name of the select as.</value>
	    public string SelectAsName
	    {
	    	get { return this.selectAsName; }
	    	set { this.selectAsName = value ; }
	    }

	    /// <summary>
	    /// Gets or sets the validated filter expression, or null
	    /// if there is none.</summary>
	    public ExprNode FilterExpr
	    {
	    	get { return this.filterExpr; }
	    	set { this.filterExpr = value; }
	    }

        /// <summary>
        /// Returns the expression node rendered as a string.
        /// </summary>
        /// <value></value>
        /// <returns> string rendering of expression
        /// </returns>
	    public override string ExpressionString
	    {
	    	get
	    	{
		        if (selectAsName != null)
		        {
		            return selectAsName;
		        }
		        return selectClause.ExpressionString;
	    	}
	    }

        /// <summary>
        /// Return true if a expression node semantically equals the current node, or false if not.
        /// Concrete implementations should compare the type and any additional information
        /// that impact the evaluation of a node.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
	    public override bool EqualsNode(ExprNode node)
	    {
	        return false;   // 2 subselects are never equivalent
	    }

	    /// <summary>
	    /// Gets or sets the strategy for boiling down the table of
	    /// subquery events into a subset against which to run the filter.
	    /// </summary>
	    public SubqueryTableLookupStrategy Strategy
	    {
	    	get { return this.strategy ; }
	    	set { this.strategy = value; }
	    }
	}
} // End of namespace

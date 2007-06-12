///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.expression;
using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Represents a single item in a SELECT-clause, potentially unnamed
	/// as no "as" tag may have been supplied in the syntax.
	/// <para>
	/// Compare to {@link SelectExprElementCompiledSpec} which carries a determined name.
	/// </para>
	/// </summary>
	public class SelectExprElementRawSpec : MetaDefItem
	{
	    private ExprNode selectExpression;
	    private String optionalAsName;

	    /// <summary>Ctor.</summary>
	    /// <param name="selectExpression">
	    /// the expression node to evaluate for matching events
	    /// </param>
	    /// <param name="optionalAsName">the name of the item, null if not name supplied</param>
	    public SelectExprElementRawSpec(ExprNode selectExpression, String optionalAsName)
	    {
	        this.selectExpression = selectExpression;
	        this.optionalAsName = optionalAsName;
	    }

	    /// <summary>
	    /// Returns the expression node representing the item in the select clause.
	    /// </summary>
	    /// <returns>expression node for item</returns>
	    public ExprNode SelectExpression
	    {
            get { return selectExpression; }
	    }

	    /// <summary>Returns the name of the item in the select clause.</summary>
	    /// <returns>name of item</returns>
	    public String OptionalAsName
	    {
            get { return optionalAsName; }
	    }
	}
} // End of namespace

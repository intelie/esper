///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.expression;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Represents a single item in a SELECT-clause, with a name assigned
	/// either by the engine or by the user specifying an "as" tag name.
	/// </summary>
	public class SelectExprElementCompiledSpec
	{
	    private ExprNode selectExpression;
	    private String assignedName;

	    /// <summary>Ctor.</summary>
	    /// <param name="selectExpression">
	    /// the expression node to evaluate for matching events
	    /// </param>
	    /// <param name="assignedName">
	    /// cannot be null as a name is always assigned or
	    /// system-determined
	    /// </param>
	    public SelectExprElementCompiledSpec(ExprNode selectExpression, String assignedName)
	    {
	        this.selectExpression = selectExpression;
	        this.assignedName = assignedName;
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
	    public String AssignedName
	    {
            get { return assignedName; }
	    }
	}
} // End of namespace

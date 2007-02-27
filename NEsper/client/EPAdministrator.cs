/// <summary>***********************************************************************************
/// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
/// http://esper.codehaus.org                                                          *
/// ---------------------------------------------------------------------------------- *
/// The software in this package is published under the terms of the GPL license       *
/// a copy of which has been included with this distribution in the license.txt file.  *
/// ************************************************************************************
/// </summary>
using System;

namespace net.esper.client
{
	/// <summary> Administrative interface to the event stream processing engine. Includes methods to create patterns and EQL statements.</summary>
	public interface EPAdministrator
	{
		/// <summary> Create a event pattern statement for the expressing string passed.</summary>
		/// <param name="onExpression">must follow the documented syntax for pattern statements
		/// </param>
		/// <returns> EPStatement to poll data from or to add listeners to
		/// </returns>
		/// <throws>  EPException when the expression was not valid </throws>
		EPStatement CreatePattern(String onExpression);
		
		/// <summary> Create a query language statement.</summary>
		/// <param name="eqlStatement">is the query language statement
		/// </param>
		/// <returns> EPStatement to poll data from or to add listeners to
		/// </returns>
		/// <throws>  EPException when the expression was not valid </throws>
		EPStatement CreateEQL(String eqlStatement);
	}
}
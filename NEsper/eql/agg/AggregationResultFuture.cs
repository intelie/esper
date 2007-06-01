///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.eql.agg
{

	/// <summary>
	/// Interface for use by aggregate expression nodes representing aggregate functions such as 'sum' or 'avg' to use
	/// to obtain the current value for the function at time of expression evaluation.
	/// </summary>
	public interface AggregationResultFuture
	{
	    /// <summary>
	    /// Returns current aggregation state, for use by expression node representing an aggregation function.
	    /// </summary>
	    /// <param name="column">
	    /// is assigned to the aggregation expression node and passed as an column (index) into a row
	    /// </param>
	    /// <returns>current aggragation state</returns>
	    Object GetValue(int column);
	}
} // End of namespace

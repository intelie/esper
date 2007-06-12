///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.eql.core;

namespace net.esper.eql.agg
{
	/// <summary>
	/// Maintains aggregation state applying values as entering and leaving the state.
    /// <para>
    /// Implementations must also act as a factory for further independent copies of aggregation
    /// states such that new aggregation state holders and be created from a prototype.
    /// </para>
	/// </summary>
	public interface AggregationMethod
	{
	    /// <summary>
	    /// Apply the value as entering aggregation (entering window).
	    /// <para>
	    /// The value can be null since 'null' values may be counted as unique separate values.
        /// </para>
	    /// </summary>
	    /// <param name="value">to add to aggregate</param>
	    void Enter(Object value);

	    /// <summary>
	    /// Apply the value as leaving aggregation (leaving window).
	    /// <para>
	    /// The value can be null since 'null' values may be counted as unique separate values.
        /// </para>
	    /// </summary>
	    /// <param name="value">to remove from aggregate</param>
	    void Leave(Object value);

	    /// <summary>Returns the current value held.</summary>
	    /// <returns>current value</returns>
        Object Value { get; }

	    /// <summary>Returns the type of the current value.</summary>
	    /// <returns>type of values held</returns>
        Type ValueType { get; }

	    /// <summary>Make a new, initalized aggregation state.</summary>
	    /// <param name="methodResolutionService">
	    /// for use in creating new aggregation method instances as a factory
	    /// </param>
	    /// <returns>initialized copy of the aggregator</returns>
	    AggregationMethod NewAggregator(MethodResolutionService methodResolutionService);
	}
} // End of namespace

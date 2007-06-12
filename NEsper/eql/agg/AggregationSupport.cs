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
    /// Base class for use with plug-in aggregation functions.
    /// </summary>
	public abstract class AggregationSupport : AggregationMethod
	{
	    /// <summary>
        /// Provides the aggregation function name.
        /// </summary>
	    protected String functionName;

	    /// <summary>
	    /// Implemented by plug-in aggregation functions to allow such functions to validate the
	    /// type of value passed to the function at statement compile time.
	    /// </summary>
	    /// <param name="childNodeType">
	    /// is the class of result of the expression sub-node within the aggregation function, or
	    /// null if a statement supplies no expression within the aggregation function
	    /// </param>
	    public abstract void Validate(Type childNodeType);

	    /// <summary>Ctor.</summary>
	    protected AggregationSupport()
	    {
	    }

        /// <summary>
        /// Gets or sets the aggregation function name.
        /// </summary>
        /// <value>The name of the function.</value>
	    public String FunctionName
	    {
            get { return this.functionName; }
            set { this.functionName = value; }
	    }

        /// <summary>
        /// Make a new, initalized aggregation state.
        /// </summary>
        /// <param name="methodResolutionService">for use in creating new aggregation method instances as a factory</param>
        /// <returns>initialized copy of the aggregator</returns>
	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakePlugInAggregator(functionName);
	    }

        /// <summary>
        /// Returns the current value held.
        /// </summary>
        /// <value></value>
        /// <returns>current value</returns>
	    abstract public object Value { get ; }

        /// <summary>
        /// Returns the type of the current value.
        /// </summary>
        /// <value></value>
        /// <returns>type of values held</returns>
	    abstract public Type ValueType { get ; }

        /// <summary>
        /// Apply the value as entering aggregation (entering window).
        /// <para>
        /// The value can be null since 'null' values may be counted as unique separate values.
        /// </para>
        /// </summary>
        /// <param name="value">to add to aggregate</param>
		abstract public void Enter(object value);

        /// <summary>
        /// Apply the value as leaving aggregation (leaving window).
        /// <para>
        /// The value can be null since 'null' values may be counted as unique separate values.
        /// </para>
        /// </summary>
        /// <param name="value">to remove from aggregate</param>
		abstract public void Leave(object value);
	}
} // End of namespace

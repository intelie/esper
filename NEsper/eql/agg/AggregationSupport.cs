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

	    /// <summary>Gets or sets the aggregation function name.</summary>
	    /// <param name="functionName">is the name of the aggregation function</param>
	    public String FunctionName
	    {
            get { return this.functionName; }
            set { this.functionName = value; }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakePlugInAggregator(functionName);
	    }
		
	    abstract public object Value { get ; }
	
	    abstract public Type ValueType { get ; }
		
		abstract public void Enter(object value);
		
		abstract public void Leave(object value);
	}
} // End of namespace

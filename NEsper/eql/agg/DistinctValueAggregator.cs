///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.core;

namespace net.esper.eql.agg
{
	/// <summary>
	/// AggregationMethod for use on top of another aggregator that handles unique value aggregation (versus all-value aggregation)
	/// for the underlying aggregator.
	/// </summary>
	public class DistinctValueAggregator : AggregationMethod
	{
	    private readonly AggregationMethod inner;
	    private readonly RefCountedSet<Object> valueSet;

	    /// <summary>Ctor.</summary>
	    /// <param name="inner">is the aggregator function computing aggregation values</param>
	    public DistinctValueAggregator(AggregationMethod inner)
	    {
	        this.inner = inner;
	        this.valueSet = new RefCountedSet<Object>();
	    }

	    public void Enter(Object value)
	    {
	        // if value not already encountered, enter into aggregate
	        if (valueSet.Add(value))
	        {
	            inner.Enter(value);
	        }
	    }

	    public void Leave(Object value)
	    {
	        // if last reference to the value is removed, remove from aggregate
	        if (valueSet.Remove(value))
	        {
	            inner.Leave(value);
	        }
	    }

	    public Object Value
	    {
            get { return inner.Value; }
	    }

	    public Type ValueType
	    {
            get { return inner.ValueType; }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        AggregationMethod innerCopy = inner.NewAggregator(methodResolutionService);
	        return methodResolutionService.MakeDistinctAggregator(innerCopy);
	    }
	}
} // End of namespace

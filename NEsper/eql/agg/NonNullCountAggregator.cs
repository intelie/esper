///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.eql.agg;
using net.esper.eql.core;

namespace net.esper.eql.agg
{
	/// <summary>Count all non-null values.</summary>
	public class NonNullCountAggregator : AggregationMethod
	{
	    private long numDataPoints;

	    public void Enter(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
	        numDataPoints++;
	    }

	    public void Leave(Object item)
	    {
            if (item == null)
	        {
	            return;
	        }
	        numDataPoints--;
	    }

	    public Object Value
	    {
            get { return numDataPoints; }
	    }

	    public Type ValueType
	    {
            get { return typeof(long?); }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakeCountAggregator(true);
	    }
	}
} // End of namespace

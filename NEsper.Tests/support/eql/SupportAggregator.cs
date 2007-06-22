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

namespace net.esper.support.eql
{
	public class SupportAggregator : AggregationMethod
	{
	    private int sum;

	    public void Enter(Object value)
	    {
	        if (value != null)
	        {
	            sum += (int) value;
	        }
	    }

	    public void Leave(Object value)
	    {
	        if (value != null)
	        {
	            sum -= (int) value;
	        }
	    }

	    public Object Value
	    {
	    	get { return sum; }
	    }

	    public Type ValueType
	    {
	    	get { return typeof(int?); }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return new SupportAggregator();
	    }
	}
} // End of namespace

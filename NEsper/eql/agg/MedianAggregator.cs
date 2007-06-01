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
    /// <summary>Median aggregation.</summary>
	public class MedianAggregator : AggregationMethod
	{
	    private SortedDoubleVector vector;

	    /// <summary>Ctor.</summary>
	    public MedianAggregator()
	    {
	        this.vector = new SortedDoubleVector();
	    }

	    public void Enter(Object item)
	    {
            if (item == null)
	        {
	            return;
	        }
	        double value = (double) item;
	        vector.Add(value);
	    }

	    public void Leave(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
	        double value = (double) item;
	        vector.Remove(value);
	    }

	    public Object GetValue()
	    {
	        if (vector.Size() == 0)
	        {
	            return null;
	        }
	        if (vector.Size() == 1)
	        {
	            return vector.GetValue(0);
	        }

	        int middle = vector.Size() >> 1;
	        if (vector.Size() % 2 == 0)
	        {
	            return (vector.GetValue(middle - 1) + vector.GetValue(middle)) / 2;
	        }
	        else
	        {
	            return vector.GetValue(middle);
	        }
	    }

	    public Type ValueType
	    {
	        get { return typeof(double); }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakeMedianAggregator();
	    }
	}
} // End of namespace

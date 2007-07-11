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
	/// <summary>Sum for any number value.</summary>
	public class NumIntegerSumAggregator : AggregationMethod
	{
	    private int sum;
	    private long numDataPoints;

	    public void Enter(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
	        numDataPoints++;
            int number = Convert.ToInt32(item);
            sum += number;
	    }

	    public void Leave(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
	        numDataPoints--;
	        int number = Convert.ToInt32(item) ;
	        sum -= number;
	    }

	    public Object Value
	    {
            get
            {
                if (numDataPoints == 0)
                {
                    return null;
                }
                return sum;
            }
	    }

	    public Type ValueType
	    {
            get { return typeof(int?); }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakeSumAggregator(null);
	    }
	}
} // End of namespace

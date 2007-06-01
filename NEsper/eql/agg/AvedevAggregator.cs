///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.core;

namespace net.esper.eql.agg
{
	/// <summary>
    /// Standard deviation always generates double-types numbers.
    /// </summary>
	public class AvedevAggregator : AggregationMethod
	{
	    private RefCountedSet<double> valueSet;
	    private double sum;

	    /// <summary>Ctor.</summary>
	    public AvedevAggregator()
	    {
	        valueSet = new RefCountedSet<double>();
	    }

	    public void Enter(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }

            double value = (double)item;
	        valueSet.Add(value);
	        sum += value;
	    }

	    public void Leave(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }

            double value = (double)item;
	        valueSet.Remove(value);
	        sum -= value;
	    }

	    public Object Value
	    {
            get
            {
                int datapoints = valueSet.Count;

                if (datapoints == 0)
                {
                    return null;
                }

                double total = 0;
                double avg = sum / datapoints;

                foreach( KeyValuePair<double,int> entry in valueSet )
                {
                    total += entry.Value * Math.Abs(entry.Key - avg);
                }

                return total / datapoints;
            }
	    }

	    public Type ValueType
	    {
	        get { return typeof(double); }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakeAvedevAggregator();
	    }
	}
} // End of namespace

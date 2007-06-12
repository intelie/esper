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

        /// <summary>
        /// Enters the specified item.
        /// </summary>
        /// <param name="item">The item.</param>
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

        /// <summary>
        /// Leaves the specified item.
        /// </summary>
        /// <param name="item">The item.</param>
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

        /// <summary>
        /// Returns the current value held.
        /// </summary>
        /// <value></value>
        /// <returns>current value</returns>
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

        /// <summary>
        /// Returns the type of the current value.
        /// </summary>
        /// <value></value>
        /// <returns>type of values held</returns>
	    public Type ValueType
	    {
	        get { return typeof(double); }
	    }

        /// <summary>
        /// Make a new, initalized aggregation state.
        /// </summary>
        /// <param name="methodResolutionService">for use in creating new aggregation method instances as a factory</param>
        /// <returns>initialized copy of the aggregator</returns>
	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakeAvedevAggregator();
	    }
	}
} // End of namespace

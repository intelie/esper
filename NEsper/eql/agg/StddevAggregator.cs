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
	/// <summary>
	/// Standard deviation always generates double-typed numbers.
	/// </summary>
    public class StddevAggregator : AggregationMethod
    {
        private double sum;
        private double sumSq;
        private long numDataPoints;

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

            double value = Convert.ToDouble(item);

            numDataPoints++;
            sum += value;
            sumSq += value * value;
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

            double value = Convert.ToDouble(item);

            numDataPoints--;
            sum -= value;
            sumSq -= value * value;
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
                if (numDataPoints < 2)
                {
                    return null;
                }

                double variance = (sumSq - sum * sum / numDataPoints) / (numDataPoints - 1);
                return Math.Sqrt(variance);
            }
        }

        /// <summary>
        /// Returns the type of the current value.
        /// </summary>
        /// <value></value>
        /// <returns>type of values held</returns>
        public Type ValueType
        {
            get { return typeof(double?); }
        }

        /// <summary>
        /// Make a new, initalized aggregation state.
        /// </summary>
        /// <param name="methodResolutionService">for use in creating new aggregation method instances as a factory</param>
        /// <returns>initialized copy of the aggregator</returns>
        public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
        {
            return methodResolutionService.MakeStddevAggregator();
        }
    }
} // End of namespace

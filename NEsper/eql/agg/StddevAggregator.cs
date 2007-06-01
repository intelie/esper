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


	/// <summary>Standard deviation always generates double-typed numbers.</summary>
    public class StddevAggregator : AggregationMethod
    {
        private double sum;
        private double sumSq;
        private long numDataPoints;

        public void Enter(Object item)
        {
            if (item == null)
            {
                return;
            }

            double value = (double)item;

            numDataPoints++;
            sum += value;
            sumSq += value * value;
        }

        public void Leave(Object item)
        {
            if (item == null)
            {
                return;
            }

            double value = (double)item;

            numDataPoints--;
            sum -= value;
            sumSq -= value * value;
        }

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

        public Type ValueType
        {
            get { return typeof(double); }
        }

        public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
        {
            return methodResolutionService.MakeStddevAggregator();
        }
    }
} // End of namespace

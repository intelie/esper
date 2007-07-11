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
	/// <summary>Sum for double values.</summary>
    public class DoubleSumAggregator : AggregationMethod
    {
        private double sum;
        private long numDataPoints;

        public void Enter(Object item)
        {
            if (item == null)
            {
                return;
            }
            numDataPoints++;
            sum += Convert.ToDouble(item);
        }

        public void Leave(Object item)
        {
            if (item == null)
            {
                return;
            }
            numDataPoints--;
            sum -= Convert.ToDouble(item);
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
            get { return typeof(double?); }
        }

        public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
        {
            return methodResolutionService.MakeSumAggregator(typeof(double));
        }
    }
} // End of namespace

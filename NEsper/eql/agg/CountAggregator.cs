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
	/// <summary>Counts all datapoints including null values.</summary>
    public class CountAggregator : AggregationMethod
    {
        private long numDataPoints;

        public void Enter(Object item)
        {
            numDataPoints++;
        }

        public void Leave(Object item)
        {
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
            return methodResolutionService.MakeCountAggregator(false);
        }
    }
} // End of namespace

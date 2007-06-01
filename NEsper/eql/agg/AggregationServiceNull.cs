///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.compat;
using net.esper.collection;
using net.esper.eql.agg;
using net.esper.events;

namespace net.esper.eql.agg
{
    /// <summary>
    /// A null object implementation of the AggregationServiceinterface.
    /// </summary>

    public class AggregationServiceNull : AggregationService
    {

        public void ApplyEnter(EventBean[] eventsPerStream,
                               MultiKeyUntyped optionalGroupKeyPerRow)
        {
        }

        public void ApplyLeave(EventBean[] eventsPerStream,
                               MultiKeyUntyped optionalGroupKeyPerRow)
        {
        }

        public void SetCurrentRow(MultiKeyUntyped groupKey)
        {
        }

        public Object GetValue(int column)
        {
            return null;
        }
    }
} // End of namespace

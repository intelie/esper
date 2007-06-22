///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.eql.agg;

namespace net.esper.support.eql
{
	public class SupportAggregationResultFuture : AggregationResultFuture
	{
	    private Object[] values;

	    public SupportAggregationResultFuture(Object[] values)
	    {
	        this.values = values;
	    }

	    public Object GetValue(int column)
	    {
	        return values[column];
	    }
	}
} // End of namespace

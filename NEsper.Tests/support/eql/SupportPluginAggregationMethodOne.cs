// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using net.esper.eql.agg;

namespace net.esper.support.eql
{
	public class SupportPluginAggregationMethodOne : AggregationSupport
	{
	    private int count;

	    public override void Validate(Type childNodeType)
	    {
	    }

	    public override void Enter(Object value)
	    {
	        count--;
	    }

	    public override void Leave(Object value)
	    {
	        count++;
	    }

	    public override Object Value
	    {
	    	get { return count; }
	    }

	    public override Type ValueType
	    {
	    	get { return typeof(int); }
	    }
	}
} // End of namespace

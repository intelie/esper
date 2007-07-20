// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Text;

using net.esper.eql.agg;

namespace net.esper.regression.client
{
	public class MyConcatAggregationFunction : AggregationSupport
	{
	    private readonly static char DELIMITER = ' ';
	    private StringBuilder builder;
	    private String delimiter;

	    public MyConcatAggregationFunction()
	        : base()
	    {
	        builder = new StringBuilder();
	        delimiter = "";
	    }

	    public override void Validate(Type childNodeType)
	    {
	        // No need to check the expression node type
	    }

	    public override void Enter(Object value)
	    {
	        if (value != null)
	        {
	            builder.Append(delimiter);
	            builder.Append(value.ToString());
	            delimiter = Convert.ToString(DELIMITER);
	        }
	    }

	    public override void Leave(Object value)
	    {
	        if (value != null)
	        {
	            builder.Remove(0, value.ToString().Length + 1);
	        }
	    }

	    public override Object Value
	    {
	    	get { return builder.ToString(); }
	    }

	    public override Type ValueType
	    {
	    	get { return typeof(string); }
	    }

	}
} // End of namespace

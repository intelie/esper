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
	public class SupportPluginAggregationMethodTwo : AggregationSupport
	{
		public override void Validate(Type childNodeType)
		{
			throw new ArgumentException("Invalid node type: " + childNodeType.FullName);
		}

		public override void Enter(Object value)
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

		public override void Leave(Object value)
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

		public override Object Value
		{
			get
			{
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}
		}

		public override Type ValueType
		{
			get
			{
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}
		}
	}
} // End of namespace

///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.eql
{
	public class SupportSelectExprProcessor : SelectExprProcessor
	{
	    public EventBean Process(EventBean[] eventsPerStream, bool isNewData)
	    {
	        return eventsPerStream[0];
	    }
		
		public virtual EventType ResultEventType {
			get {
				return SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
			}
		}
	}
} // End of namespace

// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using net.esper.client;
using net.esper.events;
using net.esper.util;

namespace net.esper.support.util
{
    public class LogUpdateListener : UpdateListener
	{
	    private String fieldNameLogged;

	    public LogUpdateListener(String fieldNameLogged)
	    {
	        this.fieldNameLogged = fieldNameLogged;
	    }

	    public void Update(EventBean[] newEvents, EventBean[] oldEvents)
	    {
	        EventBean _event = newEvents[0];
	        if (fieldNameLogged == null)
	        {
	            ThreadLogUtil.Trace(
	                String.Format("listener received, listener={0} eventUnderlying={1:X}", this,
	                              _event.Underlying.GetHashCode()));
	        }
	        else
	        {
	            ThreadLogUtil.Trace(
	                String.Format("listener received, listener={0} eventUnderlying={1:X}", this,
	                              _event["a"].GetHashCode()));
	        }
	    }
	}
} // End of namespace

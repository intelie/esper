// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;

namespace net.esper.multithread
{
	public class MTListener : UpdateListener
	{
	    private readonly String fieldName;
	    private List<Object> values;

	    public MTListener(String fieldName)
	    {
	        this.fieldName = fieldName;
	        values = new List<Object>();
	    }

	    public void Update(EventBean[] newEvents, EventBean[] oldEvents)
	    {
	        Object value = newEvents[0][fieldName];

	        lock(values)
	        {
	            values.Add(value);
	        }
	    }

	    public IList<Object> Values
	    {
            get { return values; }
	    }
	}
} // End of namespace

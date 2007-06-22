///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;

namespace net.esper.support.events
{
	public class SupportEventAdapterService
	{
	    private static EventAdapterService eventAdapterService;

	    static SupportEventAdapterService()
	    {
	        eventAdapterService = new EventAdapterServiceImpl();
	    }

        public static EventAdapterService Service
        {
            get { return eventAdapterService; }
        }

	    public static EventAdapterService GetService()
	    {
	        return eventAdapterService;
	    }
	}
} // End of namespace

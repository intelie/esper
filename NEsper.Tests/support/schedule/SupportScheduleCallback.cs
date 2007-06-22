///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.core;
using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.support.schedule
{
	public class SupportScheduleCallback : ScheduleHandleCallback
	{
	    private static int orderAllCallbacks;

	    private int orderTriggered = 0;

	    public void ScheduledTrigger(ExtensionServicesContext extensionServicesContext)
	    {
	        log.Debug(".scheduledTrigger");
	        orderAllCallbacks++;
	        orderTriggered = orderAllCallbacks;
	    }

	    public int ClearAndGetOrderTriggered()
	    {
	        int result = orderTriggered;
	        orderTriggered = 0;
	        return result;
	    }

	    public static void SetCallbackOrderNum(int orderAllCallbacks) {
	        SupportScheduleCallback.orderAllCallbacks = orderAllCallbacks;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

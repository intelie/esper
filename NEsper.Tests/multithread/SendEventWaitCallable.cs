// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.client;
using net.esper.support.util;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class SendEventWaitCallable : Callable
	{
	    private readonly int threadNum;
	    private readonly EPServiceProvider engine;
	    private readonly IEnumerator<Object> events;
	    private readonly Object sendLock;

	    public SendEventWaitCallable(int threadNum, EPServiceProvider engine, Object sendLock, IEnumerator<Object> events)
	    {
	        this.threadNum = threadNum;
	        this.engine = engine;
	        this.events = events;
	        this.sendLock = sendLock;
	    }

	    public Object Call()
	    {
	        try
	        {
	            while (events.MoveNext())
	            {
                    lock (sendLock)
                    {
                        Monitor.Wait(sendLock);
                    }
	                ThreadLogUtil.Info("sending event");
	                engine.EPRuntime.SendEvent(events.Current);
	            }
	        }
	        catch (Exception ex)
	        {
	            log.Fatal("Error in thread " + threadNum, ex);
	            return false;
	        }
	        return true;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

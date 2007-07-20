// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Threading;

using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class StmtInsertIntoCallable : Callable
	{
	    private readonly EPServiceProvider engine;
	    private readonly int numRepeats;
	    private readonly String threadKey;

	    public StmtInsertIntoCallable(String threadKey, EPServiceProvider engine, int numRepeats)
	    {
	        this.engine = engine;
	        this.numRepeats = numRepeats;
	        this.threadKey = threadKey;
	    }

	    public Object Call()
	    {
	        try
	        {
	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                SupportBean eventOne = new SupportBean();
	                eventOne.SetString("E1_" + threadKey);
	                engine.EPRuntime.SendEvent(eventOne);

	                SupportMarketDataBean eventTwo = new SupportMarketDataBean("E2_" + threadKey, 0d, null, null);
	                engine.EPRuntime.SendEvent(eventTwo);
	            }
	        }
	        catch (Exception ex)
	        {
	            log.Fatal("Error in thread " + Thread.CurrentThread.ManagedThreadId, ex);
	            return false;
	        }
	        return true;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

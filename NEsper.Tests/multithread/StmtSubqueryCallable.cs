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
	public class StmtSubqueryCallable : Callable
	{
	    private readonly int threadNum;
	    private readonly EPServiceProvider engine;
	    private readonly int numRepeats;

	    public StmtSubqueryCallable(int threadNum, EPServiceProvider engine, int numRepeats)
	    {
	        this.threadNum = threadNum;
	        this.engine = engine;
	        this.numRepeats = numRepeats;
	    }

	    public Object Call()
	    {
	        try
	        {
	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                int id = threadNum * 10000000 + loop;
	                Object eventS0 = new SupportBean_S0(id);
	                Object eventS1 = new SupportBean_S1(id);

	                engine.EPRuntime.SendEvent(eventS0);
	                engine.EPRuntime.SendEvent(eventS1);
	            }
	        }
            catch (AssertionException ex)
	        {
	            log.Fatal("Assertion error in thread " + Thread.CurrentThread.ManagedThreadId, ex);
	            return false;
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

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
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class StmtListenerCreateStmtCallable : Callable
	{
	    private readonly int numThread;
	    private readonly EPServiceProvider engine;
	    private readonly EPStatement statement;
	    private readonly int numRoutes;
	    private readonly Set<SupportMarketDataBean> routed;

	    public StmtListenerCreateStmtCallable(int numThread, EPServiceProvider engine, EPStatement statement, int numRoutes,
	                                          Set<SupportMarketDataBean> routed)
	    {
	        this.numThread = numThread;
	        this.engine = engine;
	        this.numRoutes = numRoutes;
	        this.statement = statement;
	        this.routed = routed;
	    }

	    public Object Call()
	    {
	        try
	        {
	            // add listener to triggering statement
	            MyUpdateListener listener = new MyUpdateListener(engine, numRoutes, routed, numThread);
	            statement.AddListener(listener);
	            Thread.Sleep(100);      // wait to send trigger _event, other threads receive all other's events

	            engine.EPRuntime.SendEvent(new SupportBean());

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

	    private class MyUpdateListener : UpdateListener
	    {
	        private readonly EPServiceProvider engine;
	        private readonly int numRepeats;
	        private readonly Set<SupportMarketDataBean> routed;
            private readonly int numThread;

	        public MyUpdateListener(EPServiceProvider engine, int numRepeats, Set<SupportMarketDataBean> routed, int numThread)
	        {
	            this.engine = engine;
	            this.numRepeats = numRepeats;
	            this.numThread = numThread;
	            this.routed = routed;
	        }

	        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
	        {
	            for (int i = 0; i < numRepeats; i++)
	            {
	                SupportMarketDataBean _event = new SupportMarketDataBean("", 0, numThread, null);
	                engine.EPRuntime.Route(_event);
	                routed.Add(_event);
	            }
	        }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

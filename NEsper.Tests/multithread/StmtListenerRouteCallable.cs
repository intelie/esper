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

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class StmtListenerRouteCallable : Callable
	{
	    private readonly int numThread;
	    private readonly EPServiceProvider engine;
	    private readonly EPStatement statement;
	    private readonly int numRepeats;

	    public StmtListenerRouteCallable(int numThread, EPServiceProvider engine, EPStatement statement, int numRepeats)
	    {
	        this.numThread = numThread;
	        this.engine = engine;
	        this.numRepeats = numRepeats;
	        this.statement = statement;
	    }

	    public Object Call()
	    {
	        try
	        {
	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                StmtListenerRouteCallable.MyUpdateListener listener = new StmtListenerRouteCallable.MyUpdateListener(engine, numThread);
	                statement.AddListener(listener);
	                engine.EPRuntime.SendEvent(new SupportBean());
	                statement.RemoveListener(listener);
	                listener.AssertCalled();
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

	    private class MyUpdateListener : UpdateListener
	    {
	        private readonly EPServiceProvider engine;
	        private readonly int numThread;
	        private bool isCalled;

	        public MyUpdateListener(EPServiceProvider engine, int numThread)
	        {
	            this.engine = engine;
	            this.numThread = numThread;
	        }

	        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
	        {
	            isCalled = true;

	            // create statement for thread - this can be called multiple times as other threads send SupportBean
	            EPStatement stmt = engine.EPAdministrator.CreateEQL(
	                    "select * from " + typeof(SupportMarketDataBean).FullName + " where volume=" + numThread);
	            SupportMTUpdateListener listener = new SupportMTUpdateListener();
	            stmt.AddListener(listener);

	            Object _event = new SupportMarketDataBean("", 0, (long) numThread, null);
	            engine.EPRuntime.SendEvent(_event);
	            stmt.Stop();

	            EventBean[] eventsReceived = listener.GetNewDataListFlattened();

	            bool found = false;
	            for (int i = 0; i < eventsReceived.Length; i++)
	            {
	                if (eventsReceived[i].Underlying == _event)
	                {
	                    found = true;
	                }
	            }
	            Assert.IsTrue(found);
	        }

	        public void AssertCalled()
	        {
	            Assert.IsTrue(isCalled);
	        }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

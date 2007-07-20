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
	public class StmtListenerAddRemoveCallable : Callable
	{
	    private readonly EPServiceProvider engine;
	    private readonly EPStatement stmt;
	    private readonly bool isEQL;
	    private readonly int numRepeats;

	    public StmtListenerAddRemoveCallable(EPServiceProvider engine, EPStatement stmt, bool isEQL, int numRepeats)
	    {
	        this.engine = engine;
	        this.stmt = stmt;
	        this.isEQL = isEQL;
	        this.numRepeats = numRepeats;
	    }

	    public Object Call()
	    {
	        try
	        {
	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                // Add assertListener
	                SupportMTUpdateListener assertListener = new SupportMTUpdateListener();
	                LogUpdateListener logListener;
	                if (isEQL)
	                {
	                    logListener = new LogUpdateListener(null);
	                }
	                else
	                {
	                    logListener = new LogUpdateListener("a");
	                }
	                ThreadLogUtil.Trace("adding listeners ", assertListener, logListener);
	                stmt.AddListener(assertListener);
                    stmt.AddListener(logListener);

	                // send event
	                Object _event = MakeEvent();
	                ThreadLogUtil.Trace("sending event ", _event);
	                engine.EPRuntime.SendEvent(_event);

	                // Should have received one or more events, one of them must be mine
	                EventBean[] newEvents = assertListener.GetNewDataListFlattened();
	                Assert.IsTrue(newEvents.Length >= 1,"No event received");
	                ThreadLogUtil.Trace("assert received, size is", newEvents.Length);
	                bool found = false;
	                for (int i = 0; i < newEvents.Length; i++)
	                {
	                    Object underlying = newEvents[i].Underlying;
	                    if (!isEQL)
	                    {
	                        underlying = newEvents[i]["a"];
	                    }
	                    if (underlying == _event)
	                    {
	                        found = true;
	                    }
	                }
	                Assert.IsTrue(found);
	                assertListener.Reset();

	                // Remove assertListener
	                ThreadLogUtil.Trace("removing assertListener");
	                stmt.RemoveListener(assertListener);
                    stmt.RemoveListener(logListener);

	                // Send another event
	                _event = MakeEvent();
	                ThreadLogUtil.Trace("send non-matching event ", _event);
	                engine.EPRuntime.SendEvent(_event);

	                // Make sure the event was not received
	                newEvents = assertListener.GetNewDataListFlattened();
	                found = false;
	                for (int i = 0; i < newEvents.Length; i++)
	                {
	                    Object underlying = newEvents[i].Underlying;
	                    if (!isEQL)
	                    {
	                        underlying = newEvents[i]["a"];
	                    }
	                    if (underlying == _event)
	                    {
	                        found = true;
	                    }
	                }
	                Assert.IsFalse(found);
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

	    private SupportMarketDataBean MakeEvent()
	    {
	        SupportMarketDataBean _event = new SupportMarketDataBean("IBM", 50, 1000L, "RT");
	        return _event;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

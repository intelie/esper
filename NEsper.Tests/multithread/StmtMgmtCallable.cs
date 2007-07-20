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
	public class StmtMgmtCallable : Callable
	{
	    private readonly EPServiceProvider engine;
	    private readonly Object[][] statements;
	    private readonly int numRepeats;

	    public StmtMgmtCallable(EPServiceProvider engine, Object[][] statements, int numRepeats)
	    {
	        this.engine = engine;
	        this.statements = statements;
	        this.numRepeats = numRepeats;
	    }

	    public Object Call()
	    {
	        try
	        {
	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                foreach (Object[] statement in statements)
	                {
	                    bool isEQL = (Boolean) statement[0];
	                    String statementText = (String) statement[1];

	                    // Create EQL or pattern statement
	                    EPStatement stmt;
	                    ThreadLogUtil.Trace("stmt create,", statementText);
	                    if (isEQL)
	                    {
	                        stmt = engine.EPAdministrator.CreateEQL(statementText);
	                    }
	                    else
	                    {
	                        stmt = engine.EPAdministrator.CreatePattern(statementText);
	                    }
	                    ThreadLogUtil.Trace("stmt done,", stmt);

	                    // Add listener
	                    SupportMTUpdateListener listener = new SupportMTUpdateListener();
	                    LogUpdateListener logListener;
	                    if (isEQL)
	                    {
	                        logListener = new LogUpdateListener(null);
	                    }
	                    else
	                    {
	                        logListener = new LogUpdateListener("a");
	                    }
	                    ThreadLogUtil.Trace("adding listeners ", listener, logListener);
	                    stmt.AddListener(listener);
	                    stmt.AddListener(logListener);

	                    Object _event = MakeEvent();
	                    ThreadLogUtil.Trace("sending event ", _event);
	                    engine.EPRuntime.SendEvent(_event);

	                    // Should have received one or more events, one of them must be mine
	                    EventBean[] newEvents = listener.GetNewDataListFlattened();
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
	                    listener.Reset();

	                    // Stopping statement, the event should not be received, another event may however
	                    ThreadLogUtil.Trace("stop statement");
	                    stmt.Stop();
	                    _event = MakeEvent();
	                    ThreadLogUtil.Trace("send non-matching event ", _event);
	                    engine.EPRuntime.SendEvent(_event);

	                    // Make sure the event was not received
	                    newEvents = listener.GetNewDataListFlattened();
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

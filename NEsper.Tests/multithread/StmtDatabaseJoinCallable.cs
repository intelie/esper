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
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class StmtDatabaseJoinCallable
	{
	    private readonly EPServiceProvider engine;
	    private readonly EPStatement stmt;
	    private readonly int numRepeats;
	    private readonly String[] MYVARCHAR_VALUES = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

	    public StmtDatabaseJoinCallable(EPServiceProvider engine, EPStatement stmt, int numRepeats)
	    {
	        this.engine = engine;
	        this.stmt = stmt;
	        this.numRepeats = numRepeats;
	    }

	    public Object Call()
	    {
	        try
	        {
	            // Add assertListener
	            SupportMTUpdateListener assertListener = new SupportMTUpdateListener();
	            ThreadLogUtil.Trace("adding listeners ", assertListener);
	            stmt.AddListener(assertListener);

	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                int intPrimitive = loop % 10 + 1;
	                Object eventS0 = MakeEvent(intPrimitive);

	                engine.EPRuntime.SendEvent(eventS0);

	                // Should have received one that's mine, possible multiple since the statement is used by other threads
	                bool found = false;
	                EventBean[] events = assertListener.GetNewDataListFlattened();
	                foreach (EventBean _event in events)
	                {
	                    Object s0Received = _event["s0"];
	                    IDataDictionary s1Received = (IDataDictionary) _event["s1"];
	                    if ((s0Received == eventS0) || (s1Received.Fetch("myvarchar").Equals(MYVARCHAR_VALUES[intPrimitive - 1])))
	                    {
	                        found = true;
	                    }
	                }
	                if (!found)
	                {
	                }
	                Assert.IsTrue(found);
	                assertListener.Reset();
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

	    private SupportBean MakeEvent(int intPrimitive)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetIntPrimitive(intPrimitive);
	        return _event;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

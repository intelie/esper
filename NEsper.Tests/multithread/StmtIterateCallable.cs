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

using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class StmtIterateCallable : Callable
	{
	    private readonly int threadNum;
	    private readonly EPServiceProvider engine;
	    private readonly EPStatement stmt;
	    private readonly int numRepeats;

	    public StmtIterateCallable(int threadNum, EPServiceProvider engine, EPStatement stmt, int numRepeats)
	    {
	        this.threadNum = threadNum;
	        this.engine = engine;
	        this.stmt = stmt;
	        this.numRepeats = numRepeats;
	    }

	    public Object Call()
	    {
	        try
	        {
	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                String id = Convert.ToString(threadNum * 100000000 + loop);
	                SupportBean bean = new SupportBean(id, 0);
	                engine.EPRuntime.SendEvent(bean);

	                IEnumerator<EventBean> it = stmt.GetEnumerator();
	                bool found = false;
                    while(it.MoveNext())
	                {
	                    EventBean _event = it.Current;
	                    if (_event["string"].Equals(id))
	                    {
	                        found = true;
	                    }
	                }
	                Assert.IsTrue(found);
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

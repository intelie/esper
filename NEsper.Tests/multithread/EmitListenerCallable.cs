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
using net.esper.support.emit;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class EmitListenerCallable : Callable
	{
	    private readonly EPServiceProvider engine;
	    private readonly int numRepeats;
	    private readonly int threadNum;

	    public EmitListenerCallable(int threadNum, EPServiceProvider engine, int numRepeats)
	    {
	        this.engine = engine;
	        this.numRepeats = numRepeats;
	        this.threadNum = threadNum;
	    }

	    public Object Call()
	    {
	        try
	        {
	            String channelName = Convert.ToString(threadNum);
	            SupportMTEmittedListener listener = new SupportMTEmittedListener();
	            engine.EPRuntime.AddEmittedListener(listener.Emitted, channelName);

	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                Object _event = new Object();
	                engine.EPRuntime.Emit(_event, channelName);

	                // Should have received event exactly one
	                Object[] received = listener.GetEmittedObjects();
	                Assert.AreEqual(1, received.Length);
	                Assert.AreSame(_event, received[0]);
	                listener.Reset();
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

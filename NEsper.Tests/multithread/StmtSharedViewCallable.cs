// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Threading;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.multithread
{
	public class StmtSharedViewCallable : Callable
	{
	    private readonly int numRepeats;
	    private readonly EPServiceProvider engine;
	    private readonly String[] symbols;

	    public StmtSharedViewCallable(int numRepeats, EPServiceProvider engine, String[] symbols)
	    {
	        this.numRepeats = numRepeats;
	        this.engine = engine;
	        this.symbols = symbols;
	    }

	    public Object Call()
	    {
	        try
	        {
	            for (int loop = 0; loop < numRepeats; loop++)
	            {
	                foreach (String symbol in symbols)
	                {
	                    Object _event = MakeEvent(symbol, loop);
	                    engine.EPRuntime.SendEvent(_event);
	                }
	            }
	        }
	        catch (Exception ex)
	        {
	            log.Fatal("Error in thread " + Thread.CurrentThread.ManagedThreadId, ex);
	            return false;
	        }
	        return true;
	    }

	    private SupportMarketDataBean MakeEvent(String symbol, double price)
	    {
	        return new SupportMarketDataBean(symbol, price, null, null);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace

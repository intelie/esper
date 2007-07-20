// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;
using System.IO;

using net.esper.pattern.observer;
using net.esper.pattern;
using net.esper.schedule;

namespace net.esper.regression.client
{
	public class MyFileExistsObserver : EventObserver
	{
	    private readonly MatchedEventMap beginState;
	    private readonly ObserverEventEvaluator observerEventEvaluator;
	    private readonly String filename;

	    public MyFileExistsObserver(MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, String filename)
	    {
	        this.beginState = beginState;
	        this.observerEventEvaluator = observerEventEvaluator;
	        this.filename = filename;
	    }

	    public void StartObserve()
	    {
            if (File.Exists(filename))
	        {
	            observerEventEvaluator.ObserverEvaluateTrue(beginState);
	        }
	        else
	        {
	            observerEventEvaluator.ObserverEvaluateFalse();
	        }
	    }

	    public void StopObserve()
	    {
	        // this is called when the subexpression quits or the pattern is stopped
	        // no action required
	    }
	}
} // End of namespace

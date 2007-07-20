// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.pattern;
using net.esper.pattern.observer;

namespace net.esper.regression.client
{
	public class MyFileExistsObserverFactory : ObserverFactorySupport
	{
	    private String filename;

		public override IList<object> ObserverParameters
		{
	    	set
	    	{
		        String message = "File exists observer takes a single string filename parameter";
		        if (value.Count != 1)
		        {
		            throw new ObserverParameterException(message);
		        }
		        if (!(value[0] is string))
		        {
		            throw new ObserverParameterException(message);
		        }
	
		        filename = Convert.ToString(value[0]);
		    }
	    }

	    public override EventObserver MakeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, Object stateNodeId, Object observerState)
	    {
	        return new MyFileExistsObserver(beginState, observerEventEvaluator, filename);
	    }
	}
} // End of namespace

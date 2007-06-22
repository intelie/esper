// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.pattern.observer;
using net.esper.pattern;

namespace net.esper.support.pattern
{
	public class SupportObserverFactory : ObserverFactory
	{
	    public EventObserver MakeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, Object stateNodeId, Object observerState)
	    {
	        return null;
	    }
		
		public virtual IList<object> ObserverParameters {
			set { }
		}
	}
} // End of namespace

///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.pattern.observer
{
	/// <summary>
	/// Abstract class for applications to extend to implement a pattern observer factory.
	/// </summary>
	public abstract class ObserverFactorySupport : ObserverFactory
	{
		abstract public IList<object> ObserverParameters { set ; }
		abstract public EventObserver MakeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator, object stateNodeId, object observerState);
	}
} // End of namespace

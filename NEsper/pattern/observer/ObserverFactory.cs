using System;
using System.Collections.Generic;

using net.esper.pattern;

namespace net.esper.pattern.observer
{
	/// <summary> Interface for factories for making observer instances.</summary>
	public interface ObserverFactory
	{
        /// <summary>
        /// Sets the observer object parameters.
        /// </summary>
        /// <value>The observer parameters.</value>
	    IList<Object> ObserverParameters { set ; }

        /// <summary>
        /// Make an observer instance.
        /// </summary>
        /// <param name="context">services that may be required by observer implementation</param>
        /// <param name="beginState">start state for observer</param>
        /// <param name="observerEventEvaluator">receiver for events observed</param>
        /// <param name="stateNodeId">optional id for the associated pattern state node</param>
        /// <param name="observerState">state node for observer</param>
        /// <returns>observer instance</returns>
		EventObserver MakeObserver(PatternContext context,
								   MatchedEventMap beginState,
								   ObserverEventEvaluator observerEventEvaluator,
                                   Object stateNodeId,
                                   Object observerState);
	}
}

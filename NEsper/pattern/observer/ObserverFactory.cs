using System;
using System.Collections.Generic;

using net.esper.pattern;
using net.esper.pattern.guard;

namespace net.esper.pattern.observer
{
	/// <summary> Interface for factories for making observer instances.</summary>
	public interface ObserverFactory
	{
	    /**
	     * Sets the observer object parameters.
	     * @param observerParameters is a list of parameters
	     * @throws ObserverParameterException thrown to indicate a parameter problem
	     */
	    IList<Object> ObserverParameters { set ; }
	
	    /**
	     * Make an observer instance.
	     * @param context - services that may be required by observer implementation
	     * @param beginState - start state for observer
	     * @param observerEventEvaluator - receiver for events observed
	     * @param stateNodeId - optional id for the associated pattern state node
	     * @param observerState - state node for observer
	     * @return observer instance
	     */
		EventObserver MakeObserver(PatternContext context,
								   MatchedEventMap beginState,
								   ObserverEventEvaluator observerEventEvaluator,
                                   Object stateNodeId,
                                   Object observerState);
	}
}

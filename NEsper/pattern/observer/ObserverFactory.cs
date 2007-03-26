using System;
using PatternContext = net.esper.pattern.PatternContext;
using MatchedEventMap = net.esper.pattern.MatchedEventMap;
namespace net.esper.pattern.observer
{
	
	/// <summary> Interface for factories for making observer instances.</summary>
	public interface ObserverFactory
	{
		/// <summary> Make an observer instance.</summary>
		/// <param name="context">services that may be required by observer implementation
		/// </param>
		/// <param name="beginState">Start state for observer
		/// </param>
		/// <param name="observerEventEvaluator">receiver for events observed
		/// </param>
		/// <returns> observer instance
		/// </returns>
		EventObserver makeObserver(PatternContext context, MatchedEventMap beginState, ObserverEventEvaluator observerEventEvaluator);
	}
}
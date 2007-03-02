using System;
using net.esper.pattern;
namespace net.esper.pattern.observer
{
	
	/// <summary> For use by {@link EventObserver} instances to place an event for processing/evaluation.</summary>
	public interface ObserverEventEvaluator
	{
		/// <summary> Indicate an event for evaluation (sub-expression the observer represents has turned true).</summary>
		/// <param name="matchEvent">is the matched events so far
		/// </param>
		void  observerEvaluateTrue(MatchedEventMap matchEvent);
		
		/// <summary> Indicate that the observer turned permanently false.</summary>
		void  observerEvaluateFalse();
	}
}

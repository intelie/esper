using System;

using MatchedEventMap = net.esper.pattern.MatchedEventMap;

namespace net.esper.pattern.guard
{
	/// <summary> Guard instances inspect a matched events and makes a determination on whether to let it pass or not.</summary>
	public interface Guard
	{
		/// <summary> Start the guard operation.</summary>
		void StartGuard();
		
		/// <summary> Called when sub-expression quits, or when the pattern Stopped.</summary>
		void StopGuard();
		
		/// <summary> Returns true if inspection shows that the match events can pass, or false to not pass.</summary>
		/// <param name="matchEvent">is the map of matching events
		/// </param>
		/// <returns> true to pass, false to not pass
		/// </returns>
		bool Inspect(MatchedEventMap matchEvent);
	}
}

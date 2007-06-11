using System;

using net.esper.util;

namespace net.esper.pattern
{
	/// <summary> Interface for observing when an event expression needs to Start (by adding the first listener).
	/// The publishing event expression supplies the callback used for indicating matches. The implementation supplies
	/// as a return value the callback to use to Stop the event expression.
	/// </summary>

	public interface PatternStarter
	{
		/// <summary> An event expression was Started and supplies the callback to use when matching events appear.
		/// Returns the callback to use to Stop the event expression.
		/// </summary>
		/// <param name="matchCallback">must be supplied to indicate what to call when the expression turns true
		/// </param>
		/// <param name="context">is the context for handles to services required for evaluation.
		/// </param>
		/// <returns> a callback to Stop the expression again
		/// </returns>
		
		PatternStopCallback Start(PatternMatchCallback matchCallback, PatternContext context);
	}
}

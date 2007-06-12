using System;
using System.Collections.Generic;

using PatternContext = net.esper.pattern.PatternContext;

namespace net.esper.pattern.guard
{
	/// <summary>
	/// Interface for a factory for <seealso cref="Guard"/> instances.
	/// </summary>

	public interface GuardFactory
	{
	    /// <summary>Sets the guard object parameters.</summary>
	    /// <throws>GuardParameterException thrown to indicate a parameter problem</throws>
	    IList<Object> GuardParameters { set ; }

		/// <summary> Constructs a guard instance.</summary>
		/// <param name="context">services for use by guard</param>
		/// <param name="quitable">to use for indicating the guard has quit</param>
		/// <param name="stateNodeId">a node id for the state object</param>
     	/// <param name="guardState">state node for guard</param>
		/// <returns>guard instance</returns>
		Guard MakeGuard(PatternContext context, Quitable quitable, Object stateNodeId, Object guardState);
	}
}

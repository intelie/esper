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
	    /**
	     * Sets the guard object parameters.
	     * @param guardParameters is a list of parameters
	     * @throws GuardParameterException thrown to indicate a parameter problem
	     */
	    IList<Object> GuardParameters { set ; }
	
		/// <summary> Constructs a guard instance.</summary>
		/// <param name="context">services for use by guard
		/// </param>
		/// <param name="quitable">to use for indicating the guard has quit
		/// </param>
		/// <returns> guard instance
		/// </returns>
		Guard MakeGuard(PatternContext context, Quitable quitable);
	}
}

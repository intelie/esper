using System;
using PatternContext = net.esper.pattern.PatternContext;
namespace net.esper.pattern.guard
{
	
	/// <summary> Interface for a factory for {@link Guard} instances.</summary>
	public interface GuardFactory
	{
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

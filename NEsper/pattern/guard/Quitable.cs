using System;

namespace net.esper.pattern.guard
{
	/// <summary>
    /// Receiver for quit events for use by guards.
    /// </summary>
	
    public interface Quitable
	{
		/// <summary> Indicate guard quitted.</summary>
		void GuardQuit();
	}
}
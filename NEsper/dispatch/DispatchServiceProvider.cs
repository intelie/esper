using System;
namespace net.esper.dispatch
{
	
	/// <summary> Provider of implementations for the dispatch service.</summary>
	public class DispatchServiceProvider
	{
		/// <summary> Returns new service.</summary>
		/// <returns> new dispatch service implementation.
		/// </returns>
		public static DispatchService newService()
		{
			return new DispatchServiceImpl();
		}
	}
}
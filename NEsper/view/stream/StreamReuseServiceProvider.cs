using System;
namespace net.esper.view.stream
{
	
	/// <summary> Static factory for implementations of the StreamReuseService interface.</summary>
	public sealed class StreamReuseServiceProvider
	{
		/// <summary> Creates an implementation of the StreamReuseService interface.</summary>
		/// <returns> implementation
		/// </returns>
		public static StreamReuseService newService()
		{
			return new StreamReuseServiceImpl();
		}
	}
}
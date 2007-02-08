using System;
namespace net.esper.emit
{
	
	/// <summary> Static factory for implementations of the EmitService interface.</summary>
	public sealed class EmitServiceProvider
	{
		/// <summary> Creates an implementation of the EmitService interface.</summary>
		/// <returns> implementation
		/// </returns>
		public static EmitService newService()
		{
			return new EmitServiceImpl();
		}
	}
}
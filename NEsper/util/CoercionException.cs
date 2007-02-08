using System;
using EPException = net.esper.client.EPException;
namespace net.esper.util
{
	
	/// <summary> Exception to represent a mismatch in Java types in an expression.</summary>
	[Serializable]
	public class CoercionException:EPException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">supplies the detailed description
		/// </param>
		public CoercionException(String message):base(message)
		{
		}
	}
}
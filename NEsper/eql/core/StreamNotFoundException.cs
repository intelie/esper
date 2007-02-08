using System;
namespace net.esper.eql.core
{
	
	/// <summary> Exception to indicate that a stream name could not be resolved.</summary>
	[Serializable]
	public class StreamNotFoundException:StreamTypesException
	{
		/// <summary> Ctor.</summary>
		/// <param name="msg">- message
		/// </param>
		public StreamNotFoundException(String msg):base(msg)
		{
		}
	}
}
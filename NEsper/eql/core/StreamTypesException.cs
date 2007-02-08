using System;
namespace net.esper.eql.core
{
	
	/// <summary> Base class for stream and property name resolution errors.</summary>
	[Serializable]
	public abstract class StreamTypesException:System.Exception
	{
		/// <summary> Ctor.</summary>
		/// <param name="msg">- message
		/// </param>
		public StreamTypesException(String msg):base(msg)
		{
		}
	}
}
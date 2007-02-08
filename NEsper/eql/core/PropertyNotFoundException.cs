using System;
namespace net.esper.eql.core
{
	
	/// <summary> Exception to indicate that a property name used in a filter doesn't resolve.</summary>
	[Serializable]
	public class PropertyNotFoundException:StreamTypesException
	{
		/// <summary> Ctor.</summary>
		/// <param name="msg">- message
		/// </param>
		public PropertyNotFoundException(String msg):base(msg)
		{
		}
	}
}
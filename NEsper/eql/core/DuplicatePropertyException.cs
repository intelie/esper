using System;
namespace net.esper.eql.core
{
	
	/// <summary> Indicates a property exists in multiple streams.</summary>
	[Serializable]
	public class DuplicatePropertyException:StreamTypesException
	{
		/// <summary> Ctor.</summary>
		/// <param name="msg">- exception message
		/// </param>
		public DuplicatePropertyException(String msg):base(msg)
		{
		}
	}
}
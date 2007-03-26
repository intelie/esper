using System;
namespace net.esper.util
{
	
	/// <summary> Exception thrown when a runtime assertion failed.</summary>
	[Serializable]
	public class AssertionException:SystemException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">assertion message
		/// </param>
		public AssertionException(String message):base(message)
		{
		}
	}
}
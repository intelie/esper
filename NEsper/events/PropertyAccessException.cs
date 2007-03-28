using System;

namespace net.esper.events
{
	/// <summary>
	/// This exception is thrown to indicate a problem with a accessing a
	/// property of an <seealso cref="EventBean" />.
	/// </summary>
	
	[Serializable]
	public sealed class PropertyAccessException:SystemException
	{
		/// <summary> Constructor.</summary>
		/// <param name="message">is the error message
		/// </param>
		public PropertyAccessException(String message)
			: base(message)
		{
		}
		
		/// <summary> Constructor for an inner exception and message.</summary>
		/// <param name="message">is the error message
		/// </param>
		/// <param name="cause">is the inner exception
		/// </param>
		public PropertyAccessException(String message, System.Exception cause)
			: base(message, cause)
		{
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="cause">is the inner exception
		/// </param>
		public PropertyAccessException(System.Exception cause)
			: base(String.Empty, cause)
		{
		}
	}
}

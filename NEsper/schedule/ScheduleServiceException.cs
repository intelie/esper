using System;

namespace net.esper.schedule
{
	/// <summary>
	/// This exception is thrown to indicate a problem with scheduling.
	/// </summary>
	
	[Serializable]
	public class ScheduleServiceException : Exception
	{
		/// <summary> Constructor.</summary>
		/// <param name="message">is the error message
		/// </param>
		public ScheduleServiceException(String message)
			: base(message)
		{
		}
		
		/// <summary> Constructor for an inner exception and message.</summary>
		/// <param name="message">is the error message
		/// </param>
		/// <param name="cause">is the inner exception
		/// </param>
		public ScheduleServiceException(String message, System.Exception cause)
			: base(message, cause)
		{
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="cause">is the inner exception
		/// </param>
		public ScheduleServiceException(System.Exception cause)
			: base(String.Empty, cause)
		{
		}
	}
}

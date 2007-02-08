using System;
namespace net.esper.eql.db
{
	/// <summary>
	/// Exception to indicate that a stream name could not be resolved.
	/// </summary>
	
	[Serializable]
	public class DatabaseConfigException : System.Exception
	{
		/// <summary> Ctor.</summary>
		/// <param name="msg">- message
		/// </param>
		
		public DatabaseConfigException(String msg):base(msg)
		{
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="message">- error message
		/// </param>
		/// <param name="cause">- cause is the inner exception
		/// </param>

		public DatabaseConfigException(String message, System.Exception cause):base(message, cause)
		{
		}
	}
}

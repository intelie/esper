using System;

using antlr;

using net.esper.client;

namespace net.esper.eql.parse
{
	/// <summary>
	/// This exception is thrown to indicate a problem in statement creation.
	/// </summary>
	
	[Serializable]
	public class ASTWalkException:SystemException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">is the error message
		/// </param>
		/// <param name="t">is the inner throwable
		/// </param>
		public ASTWalkException(String message, System.Exception t) :
			base(message, t)
		{
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="message">is the error message
		/// </param>
		public ASTWalkException(String message) :
			base(message)
		{
		}
	}
}

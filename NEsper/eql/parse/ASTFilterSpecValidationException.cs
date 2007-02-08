using System;
using EPException = net.esper.client.EPException;
namespace net.esper.eql.parse
{
	
	/// <summary>
	/// This exception is thrown to indicate a problem in a filter specification.
	/// </summary>
	
	[Serializable]
	public class ASTFilterSpecValidationException : ASTWalkException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">- error message
		/// </param>
		/// <param name="t">- inner throwable
		/// </param>
		public ASTFilterSpecValidationException(String message, System.Exception t):base(message, t)
		{
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="message">- error message
		/// </param>
		public ASTFilterSpecValidationException(String message):base(message)
		{
		}
	}
}

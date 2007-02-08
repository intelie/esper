using System;
namespace net.esper.eql.expression
{
	
	/// <summary> Thrown to indicate a validation error in a filter expression.</summary>
	[Serializable]
	public class ExprValidationException:System.Exception
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">- validation error message
		/// </param>
		public ExprValidationException(String message):base(message)
		{
		}
	}
}
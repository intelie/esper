using System;
using EPException = net.esper.client.EPException;
using EPStatementException = net.esper.client.EPStatementException;
using TokenStreamException = antlr.TokenStreamException;
using RecognitionException = antlr.RecognitionException;
namespace net.esper.eql.parse
{
	
	/// <summary> This exception is thrown to indicate a problem in statement creation.</summary>
	[Serializable]
	public class EPStatementSyntaxException:EPStatementException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">error message
		/// </param>
		/// <param name="expression">expression text
		/// </param>
		public EPStatementSyntaxException(String message, String expression):base(message, expression)
		{
		}
		
		/// <summary> Converts from a syntax error to a nice statement exception.</summary>
		/// <param name="e">is the syntax error
		/// </param>
		/// <param name="expression">is the expression text
		/// </param>
		/// <returns> syntax exception
		/// </returns>
		public static EPStatementSyntaxException convert(RecognitionException e, String expression)
		{
			String positionInfo = e.getLine() > 0 && e.getColumn() > 0?" near line " + e.getLine() + ", column " + e.getColumn():"";
			return new EPStatementSyntaxException(e.Message + positionInfo, expression);
		}
		
		/// <summary> Converts from a syntax (token stream) error to a nice statement exception.</summary>
		/// <param name="e">is the syntax error
		/// </param>
		/// <param name="expression">is the expression text
		/// </param>
		/// <returns> syntax exception
		/// </returns>
		public static EPStatementSyntaxException convert(TokenStreamException e, String expression)
		{
			return new EPStatementSyntaxException(e.Message, expression);
		}
	}
}

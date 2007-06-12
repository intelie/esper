using System;

using net.esper.client;

using antlr;

namespace net.esper.eql.parse
{
	/// <summary> This exception is thrown to indicate a problem in statement creation.</summary>
	[Serializable]
	public class EPStatementSyntaxException : EPStatementException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">error message
		/// </param>
		/// <param name="expression">expression text
		/// </param>
		public EPStatementSyntaxException(String message, String expression)
			: base(message, expression)
		{
		}

		/// <summary> Converts from a syntax error to a nice statement exception.</summary>
		/// <param name="e">is the syntax error
		/// </param>
		/// <param name="expression">is the expression text
		/// </param>
		/// <returns> syntax exception
		/// </returns>
		public static EPStatementSyntaxException Convert(RecognitionException e, String expression)
		{
	        return new EPStatementSyntaxException(e.Message + GetPositionInfo(e), expression);
	    }

	    /// <summary>
	    /// Converts end-of-input error from a syntax error to a nice statement exception.
	    /// </summary>
	    /// <param name="e">is the syntax error</param>
	    /// <param name="expression">is the expression text</param>
	    /// <param name="tokenNameExpected">
	    /// is the name or paraphrase of the expected token
	    /// </param>
	    /// <returns>syntax exception</returns>
	    public static EPStatementSyntaxException ConvertEndOfInput(RecognitionException e, String tokenNameExpected, String expression)
	    {
	        return new EPStatementSyntaxException("end of input when expecting " + tokenNameExpected + GetPositionInfo(e), expression);
	    }

	    /// <summary>
	    /// Converts end-of-input error from a syntax error to a nice statement exception.
	    /// </summary>
	    /// <param name="e">is the syntax error</param>
	    /// <param name="expression">is the expression text</param>
	    /// <returns>syntax exception</returns>
	    public static EPStatementSyntaxException ConvertEndOfInput(RecognitionException e, String expression)
	    {
	        return new EPStatementSyntaxException("end of input" + GetPositionInfo(e), expression);
	    }

	    /// <summary>Returns the position information string for a parser exception.</summary>
	    /// <param name="e">is the parser exception</param>
	    /// <returns>is a string with line and column information</returns>
	    public static String GetPositionInfo(RecognitionException e)
	    {
	        return e.getLine() > 0 && e.getColumn() > 0
	                ? " near line " + e.getLine() + ", column " + e.getColumn()
	                : "";
	    }

		/// <summary> Converts from a syntax (token stream) error to a nice statement exception.</summary>
		/// <param name="e">is the syntax error
		/// </param>
		/// <param name="expression">is the expression text
		/// </param>
		/// <returns> syntax exception
		/// </returns>
		public static EPStatementSyntaxException Convert(TokenStreamException e, String expression)
		{
			return new EPStatementSyntaxException(e.Message, expression);
		}
	}
}

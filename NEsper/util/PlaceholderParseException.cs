using System;
using AST = antlr.collections.AST;
using CommonAST = antlr.CommonAST;
namespace net.esper.util
{
	
	/// <summary> Exception to indicate a parse error in parsing placeholders.</summary>
	[Serializable]
	public class PlaceholderParseException:System.Exception
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">is the error message
		/// </param>
		public PlaceholderParseException(String message):base(message)
		{
		}
	}
}
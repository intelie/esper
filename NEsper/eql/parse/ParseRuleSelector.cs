using System;
using EQLStatementParser = net.esper.eql.generated.EQLStatementParser;
using TokenStreamException = antlr.TokenStreamException;
using RecognitionException = antlr.RecognitionException;
namespace net.esper.eql.parse
{
	
	/// <summary> For selection of the parse rule to use.</summary>
	public interface ParseRuleSelector
	{
		/// <summary> Implementations can invoke a parse rule of their choice on the parser.</summary>
		/// <param name="parser">- to invoke parse rule on
		/// </param>
		/// <throws>  TokenStreamException is a parse exception </throws>
		/// <throws>  RecognitionException is a parse exception </throws>
		void  invokeParseRule(EQLStatementParser parser);
	}
}
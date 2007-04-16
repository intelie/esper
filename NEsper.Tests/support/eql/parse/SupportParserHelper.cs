using System;
using System.IO;

using antlr.collections;
using antlr.debug;

using net.esper.eql.generated;
using net.esper.eql.parse;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.support.eql.parse
{
	public class SupportParserHelper
	{
		private class AnonymousClassParseRuleSelector : ParseRuleSelector
		{
			public virtual void invokeParseRule( EQLStatementParser parser )
			{
				parser.startPatternExpressionRule();
			}
		}

		private class AnonymousClassParseRuleSelector1 : ParseRuleSelector
		{
			public virtual void invokeParseRule( EQLStatementParser parser )
			{
				parser.startEQLExpressionRule();
			}
		}

		private class AnonymousClassParseRuleSelector2 : ParseRuleSelector
		{
			public virtual void invokeParseRule( EQLStatementParser parser )
			{
				parser.startEventPropertyRule();
			}
		}

		public static void displayAST( AST ast )
		{
			log.Debug( ".displayAST list=" + ast.ToStringList() );

			log.Debug( ".displayAST DumpASTVisitor..." );
			if ( log.IsDebugEnabled )
			{
				DebugFacility.DumpAST( ast );
			}
		}

		public static AST parsePattern( String text )
		{
			ParseRuleSelector startRuleSelector = new AnonymousClassParseRuleSelector();
			return parse( startRuleSelector, text );
		}

		public static AST parseEQL( String text )
		{
			ParseRuleSelector startRuleSelector = new AnonymousClassParseRuleSelector1();
			return parse( startRuleSelector, text );
		}

		public static AST parseEventProperty( String text )
		{
			ParseRuleSelector startRuleSelector = new AnonymousClassParseRuleSelector2();
			return parse( startRuleSelector, text );
		}

		public static AST parse( ParseRuleSelector parseRuleSelector, String text )
		{
			EQLStatementLexer lexer = new EQLStatementLexer( new System.IO.StringReader( text ) );
			EQLStatementParser parser = new EQLStatementParser( lexer );
			parseRuleSelector.invokeParseRule( parser );
			return parser.getAST();
		}

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}

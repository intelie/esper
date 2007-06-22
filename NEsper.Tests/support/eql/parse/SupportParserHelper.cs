using System;
using System.IO;

using antlr.collections;

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

		public static void DisplayAST( AST ast )
		{
			log.Debug( ".displayAST list=" + ast.ToStringList() );

			log.Debug( ".displayAST DumpASTVisitor..." );
			if ( log.IsDebugEnabled )
			{
				DebugFacility.DumpAST( ast );
			}
		}

		public static AST ParsePattern( String text )
		{
			ParseRuleSelector startRuleSelector = new AnonymousClassParseRuleSelector();
            return Parse(startRuleSelector, text);
		}

		public static AST ParseEQL( String text )
		{
			ParseRuleSelector startRuleSelector = new AnonymousClassParseRuleSelector1();
            return Parse(startRuleSelector, text);
		}

		public static AST ParseEventProperty( String text )
		{
			ParseRuleSelector startRuleSelector = new AnonymousClassParseRuleSelector2();
            return Parse(startRuleSelector, text);
		}

		public static AST Parse( ParseRuleSelector parseRuleSelector, String text )
		{
			EQLStatementLexer lexer = new EQLStatementLexer( new StringReader( text ) );
			EQLStatementParser parser = new EQLStatementParser( lexer );
			parseRuleSelector.invokeParseRule( parser );
			return parser.getAST();
		}

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}

using System;

using antlr;
using antlr.collections;

using net.esper.client;
using net.esper.eql.generated;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
    /// <summary> Helper class for parsing an expression and walking a parse tree.</summary>
    public class ParseHelper
    {
        /// <summary> Walk parse tree Starting at the rule the walkRuleSelector supplies.</summary>
        /// <param name="ast">ast to walk
        /// </param>
        /// <param name="walker">walker instance
        /// </param>
        /// <param name="walkRuleSelector">walk rule
        /// </param>
        /// <param name="expression">the expression we are walking in string form
        /// </param>
        public static void walk(AST ast, EQLBaseWalker walker, WalkRuleSelector walkRuleSelector, String expression)
        {
            // Walk tree
            try
            {
                if (log.IsDebugEnabled)
                {
                    log.Debug(".walk Walking AST using walker " + walker.GetType().FullName);
                }

                walkRuleSelector.invokeWalkRule(walker, ast);

                if (log.IsDebugEnabled)
                {
                    log.Debug(".walk AST tree after walking");
                    DebugFacility.DumpAST(ast);
                }
            }
            catch (RecognitionException e)
            {
                throw EPStatementSyntaxException.Convert(e, expression);
            }
        }

        /// <summary> Parse expression using the rule the ParseRuleSelector instance supplies.</summary>
        /// <param name="expression">text to parse
        /// </param>
        /// <param name="parseRuleSelector">parse rule to select
        /// </param>
        /// <returns> AST - syntax tree
        /// </returns>
        /// <throws>  EPException </throws>
        public static AST parse(String expression, ParseRuleSelector parseRuleSelector)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".parse Parsing expr=" + expression);
            }

            EQLStatementLexer lexer = new EQLStatementLexer(new System.IO.StringReader(expression));
            EQLStatementParser parser = new EQLStatementParser(lexer);

            try
            {
                parseRuleSelector.invokeParseRule(parser);
            }
	        catch(MismatchedTokenException mte)
	        {
	            if(mte.token.getText() == null)
	            {
	                throw EPStatementSyntaxException.ConvertEndOfInput(mte, EQLStatementParser._tokenNames[mte.expecting], expression);
	            }
	            else
	            {
	                throw EPStatementSyntaxException.Convert(mte, expression);
	            }
	        }
	        catch (TokenStreamRecognitionException e)
	        {
	            if (e.recog is MismatchedCharException)
	            {
	                MismatchedCharException mme = (MismatchedCharException) e.recog;
	                // indicates EOF char
	                if (mme.foundChar == 65535)
	                {
	                    char expected = (char) mme.expecting;
	                    String wrapped = String.Format("'{0}'", expected);
	                    if (expected == '\'')
	                    {
	                        wrapped = "a singe quote \"'\"";
	                    }
	                    throw EPStatementSyntaxException.ConvertEndOfInput(mme, wrapped, expression);
	                }
	            }
	            throw EPStatementSyntaxException.Convert(e, expression);
	        }
	        catch (TokenStreamException e)
	        {
	            throw EPStatementSyntaxException.Convert(e, expression);
	        }
	        catch (NoViableAltException e)
	        {
	            if(e.token.getText() == null)
	            {
	                throw EPStatementSyntaxException.ConvertEndOfInput(e, expression);
	            }
	            else
	            {
	                throw EPStatementSyntaxException.Convert(e, expression);
	            }
	        }
            catch (RecognitionException e)
            {
                throw EPStatementSyntaxException.Convert(e, expression);
            }

            AST ast = parser.getAST();

            if (log.IsDebugEnabled)
            {
                log.Debug(".parse Dumping AST...");
                DebugFacility.DumpAST(ast);
            }

            return ast;
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}

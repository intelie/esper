package net.esper.eql.parse;

import java.io.StringReader;

import net.esper.client.EPException;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.generated.EQLStatementLexer;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.util.DebugFacility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import antlr.*;
import antlr.collections.AST;

/**
 * Helper class for parsing an expression and walking a parse tree.
 */
public class ParseHelper
{
    /**
     * Walk parse tree starting at the rule the walkRuleSelector supplies.
     * @param ast - ast to walk
     * @param walker - walker instance
     * @param walkRuleSelector - walk rule
     * @param expression - the expression we are walking in string form
     */
    public static void walk(AST ast, EQLBaseWalker walker, WalkRuleSelector walkRuleSelector, String expression)
    {
        // Walk tree
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug(".walk Walking AST using walker " + walker.getClass().getName());
            }

            walkRuleSelector.invokeWalkRule(walker, ast);

            if (log.isDebugEnabled())
            {
                log.debug(".walk AST tree after walking");
                DebugFacility.dumpAST(ast);
            }
        }
        catch (RecognitionException e)
        {
            throw EPStatementSyntaxException.convert(e, expression);
        }
    }

    /**
     * Parse expression using the rule the ParseRuleSelector instance supplies.
     * @param expression - text to parse
     * @param parseRuleSelector - parse rule to select
     * @return AST - syntax tree
     * @throws EPException when the AST could not be parsed
     */
    public static AST parse(String expression, ParseRuleSelector parseRuleSelector) throws EPException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parse Parsing expr=" + expression);
        }

        EQLStatementLexer lexer = new EQLStatementLexer(new StringReader(expression));
        EQLStatementParser parser = new EQLStatementParser(lexer);

        try
        {
            parseRuleSelector.invokeParseRule(parser);
        }
        catch(MismatchedTokenException mte)
        {
            if(mte.token.getText() == null)
            {
                throw EPStatementSyntaxException.convertEndOfInput(mte, EQLStatementParser._tokenNames[mte.expecting], expression);
            }
            else
            {
                throw EPStatementSyntaxException.convert(mte, expression);
            }
        }
        catch (TokenStreamRecognitionException e)
        {
            if (e.recog instanceof MismatchedCharException)
            {
                MismatchedCharException mme = (MismatchedCharException) e.recog;
                // indicates EOF char
                if (mme.foundChar == 65535)
                {
                    char expected = (char) mme.expecting;
                    String wrapped = "'" + new String(Character.toString(expected)) + "'";
                    if (expected == '\'')
                    {
                        wrapped = "a singe quote \"'\"";
                    }
                    throw EPStatementSyntaxException.convertEndOfInput(mme, wrapped, expression);
                }
            }
            throw EPStatementSyntaxException.convert(e, expression);
        }
        catch (TokenStreamException e)
        {
            throw EPStatementSyntaxException.convert(e, expression);
        }
        catch (NoViableAltException e)
        {
            if(e.token.getText() == null)
            {
                throw EPStatementSyntaxException.convertEndOfInput(e, expression);
            }
            else
            {
                throw EPStatementSyntaxException.convert(e, expression);
            }
        }
        catch (RecognitionException e)
        {
            throw EPStatementSyntaxException.convert(e, expression);
        }

        AST ast = parser.getAST();

        if (log.isDebugEnabled())
        {
            log.debug(".parse Dumping AST...");
            DebugFacility.dumpAST(ast);
        }

        return ast;
    }

    private static Log log = LogFactory.getLog(ParseHelper.class);
}

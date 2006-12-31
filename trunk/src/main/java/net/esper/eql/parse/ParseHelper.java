package net.esper.eql.parse;

import java.io.StringReader;

import net.esper.client.EPException;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.generated.EQLStatementLexer;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.util.DebugFacility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import antlr.RecognitionException;
import antlr.TokenStreamException;
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
     * @throws EPException
     */
    public static AST parse(String expression, ParseRuleSelector parseRuleSelector) throws EPException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parse Parsing expr=" + expression);
        }

        EQLStatementLexer lexer = new EQLStatementLexer(new StringReader(expression));
        EQLStatementParser parser = new EQLStatementParser(lexer);
        parser.getASTFactory().setASTNodeClass(PositionTrackingAST.class);
        
        try
        {
            parseRuleSelector.invokeParseRule(parser);
        }
        catch (TokenStreamException e)
        {
            throw EPStatementSyntaxException.convert(e, expression);
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

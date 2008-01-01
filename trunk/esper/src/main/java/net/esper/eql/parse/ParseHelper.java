/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import java.io.StringReader;
import java.io.IOException;

import net.esper.client.EPException;
import net.esper.util.DebugFacility;
import net.esper.eql.generated.EsperEPLTree;
import net.esper.eql.generated.EsperEPLParser;
import net.esper.eql.generated.EsperEPLLexer;
import net.esper.event.PropertyAccessException;
import net.esper.antlr.NoCaseSensitiveStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.*;

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
    public static void walk(Tree ast, EsperEPLTree walker, WalkRuleSelector walkRuleSelector, String expression)
    {
        // Walk tree
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug(".walk Walking AST using walker " + walker.getClass().getName());
            }

            walkRuleSelector.invokeWalkRule(walker);

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
    public static Tree parse(String expression, ParseRuleSelector parseRuleSelector) throws EPException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parse Parsing expr=" + expression);
        }

        CharStream input;
        try
        {
            input = new NoCaseSensitiveStream(new StringReader(expression));
        }
        catch (IOException ex)
        {
            throw new EPException("IOException parsing expression '" + expression + '\'', ex);
        }

        EsperEPLLexer lex = new EsperEPLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        EsperEPLParser parser = new EsperEPLParser(tokens);
        EsperEPLParser.startEventPropertyRule_return r;

        Tree tree = null;
        try
        {
            tree = parseRuleSelector.invokeParseRule(parser);
        }
        catch(MismatchedTokenException mte)
        {
            if(mte.token.getText() == null)
            {
                // TODO: passing null for tokens
                throw EPStatementSyntaxException.convertEndOfInput(mte, null, expression);
            }
            else
            {
                throw EPStatementSyntaxException.convert(mte, expression);
            }
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

        if (log.isDebugEnabled())
        {
            log.debug(".parse Dumping AST...");
            DebugFacility.dumpAST(tree);
        }

        return tree;
    }

    private static Log log = LogFactory.getLog(ParseHelper.class);
}

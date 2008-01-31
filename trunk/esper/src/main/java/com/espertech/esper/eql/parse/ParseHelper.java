/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.parse;

import java.io.StringReader;
import java.io.IOException;
import java.util.Stack;

import com.espertech.esper.client.EPException;
import com.espertech.esper.eql.generated.EsperEPL2GrammarParser;
import com.espertech.esper.eql.generated.EsperEPL2GrammarLexer;
import com.espertech.esper.antlr.NoCaseSensitiveStream;
import com.espertech.esper.antlr.ASTUtil;

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
    public static void walk(Tree ast, EQLTreeWalker walker, WalkRuleSelector walkRuleSelector, String expression)
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
                ASTUtil.dumpAST(ast);
            }
        }
        catch (RuntimeException e)
        {
            log.info("Error walking statement [" + expression + "]", e);
            if (e.getCause() instanceof RecognitionException)
            {
                throw EPStatementSyntaxException.convert((RecognitionException)e.getCause(), expression, walker);
            }
            else
            {
                throw e;
            }
        }
        catch (RecognitionException e)
        {
            log.info("Error walking statement [" + expression + "]", e);
            throw EPStatementSyntaxException.convert(e, expression, walker);
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

        EsperEPL2GrammarLexer lex = new EsperEPL2GrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        EsperEPL2GrammarParser parser = new EsperEPL2GrammarParser(tokens);
        EsperEPL2GrammarParser.startEventPropertyRule_return r;

        Tree tree;
        try
        {
            tree = parseRuleSelector.invokeParseRule(parser);
        }
        catch (RuntimeException e)
        {
            log.info("Error parsing statement [" + expression + "]", e);
            if (e.getCause() instanceof RecognitionException)
            {
                throw EPStatementSyntaxException.convert((RecognitionException)e.getCause(), expression, parser);
            }
            else
            {
                throw e;
            }
        }
        catch (RecognitionException ex)
        {
            log.info("Error parsing statement [" + expression + "]", ex);
            throw EPStatementSyntaxException.convert(ex, expression, parser);
        }

        if (log.isDebugEnabled())
        {
            log.debug(".parse Dumping AST...");
            ASTUtil.dumpAST(tree);
        }

        return tree;
    }

    private static Log log = LogFactory.getLog(ParseHelper.class);
}

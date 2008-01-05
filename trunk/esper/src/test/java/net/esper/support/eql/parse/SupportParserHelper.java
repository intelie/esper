package net.esper.support.eql.parse;

import net.esper.antlr.NoCaseSensitiveStream;
import net.esper.antlr.ASTUtil;
import net.esper.eql.generated.EsperEPL2GrammarLexer;
import net.esper.eql.generated.EsperEPL2GrammarParser;
import net.esper.eql.parse.ParseRuleSelector;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.RewriteCardinalityException;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.StringReader;

public class SupportParserHelper
{
    public static void displayAST(Tree ast) throws Exception
    {
        log.debug(".displayAST...");
        if (log.isDebugEnabled())
        {
            ASTUtil.dumpAST(ast);
        }
    }

    public static Tree parsePattern(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPL2GrammarParser parser) throws RecognitionException
            {
                EsperEPL2GrammarParser.startPatternExpressionRule_return r = parser.startPatternExpressionRule();
                return (Tree) r.getTree();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static Tree parseEQL(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPL2GrammarParser parser) throws RecognitionException
            {
                EsperEPL2GrammarParser.startEPLExpressionRule_return r = parser.startEPLExpressionRule();
                return (Tree) r.getTree();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static Tree parseEventProperty(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPL2GrammarParser parser) throws RecognitionException
            {
                return (Tree) parser.startEventPropertyRule().getTree();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static Tree parse(ParseRuleSelector parseRuleSelector, String text) throws Exception
    {
        CharStream input;
        try
        {
            input = new NoCaseSensitiveStream(new StringReader(text));
        }
        catch (IOException ex)
        {
            throw new RuntimeException("IOException parsing text '" + text + '\'', ex);
        }

        EsperEPL2GrammarLexer lex = new EsperEPL2GrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        ASTUtil.printTokens(tokens);
        EsperEPL2GrammarParser g = new EsperEPL2GrammarParser(tokens);
        EsperEPL2GrammarParser.startEventPropertyRule_return r;

        Tree tree;
        try
        {
            tree = parseRuleSelector.invokeParseRule(g);
        }
        catch (RewriteCardinalityException ex)
        {
            log.error(ex.getMessage());
            throw ex;
        }
        return tree;
    }

    private static Log log = LogFactory.getLog(SupportParserHelper.class);
}

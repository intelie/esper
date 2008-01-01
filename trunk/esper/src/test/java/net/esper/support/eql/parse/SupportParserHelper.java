package net.esper.support.eql.parse;

import net.esper.antlr.NoCaseSensitiveStream;
import net.esper.eql.generated.EsperEPLLexer;
import net.esper.eql.generated.EsperEPLParser;
import net.esper.eql.parse.ParseRuleSelector;
import net.esper.util.DebugFacility;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.RewriteCardinalityException;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.PrintWriter;

public class SupportParserHelper
{
    public static void displayAST(Tree ast) throws Exception
    {
        log.debug(".displayAST...");
        if (log.isDebugEnabled())
        {
            DebugFacility.dumpAST(ast);
        }
    }

    public static Tree parsePattern(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPLParser parser) throws RecognitionException
            {
                EsperEPLParser.startPatternExpressionRule_return r = parser.startPatternExpressionRule();
                return (Tree) r.getTree();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static Tree parseEQL(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPLParser parser) throws RecognitionException
            {
                EsperEPLParser.startEPLExpressionRule_return r = parser.startEPLExpressionRule();
                return (Tree) r.getTree();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static Tree parseEventProperty(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPLParser parser) throws RecognitionException
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

        EsperEPLLexer lex = new EsperEPLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);

        if (log.isDebugEnabled())
        {
            StringWriter writer = new StringWriter();
            PrintWriter printer = new PrintWriter(writer);
            for (int i = 0; i < tokens.size(); i++)
            {
                Token t = tokens.get(i);
                printer.print(t.getText());
                printer.print('[');
                printer.print(t.getType());
                printer.print(']');
                printer.print(" ");
            }
            log.debug("Tokens: " + writer.toString());
        }        

        EsperEPLParser g = new EsperEPLParser(tokens);
        EsperEPLParser.startEventPropertyRule_return r;

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

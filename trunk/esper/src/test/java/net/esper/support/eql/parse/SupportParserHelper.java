package net.esper.support.eql.parse;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.StringReader;

import net.esper.eql.generated.EQLStatementLexer;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.parse.ParseRuleSelector;
import net.esper.util.DebugFacility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import antlr.CommonAST;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.debug.misc.ASTFrame;

public class SupportParserHelper
{
    public static void displayAST(AST ast) throws Exception
    {
        log.debug(".displayAST list=" + ast.toStringList());

        log.debug(".displayAST DumpASTVisitor...");
        if (log.isDebugEnabled())
        {
            DebugFacility.dumpAST(ast);
        }
    }

    public static void displayASTSwing(AST ast)
    {
        // Display in Swing frame
        CommonAST commonAST = (CommonAST) ast;

        //commonAST.setVerboseStringConversion(true, parser.getTokenNames());
        antlr.ASTFactory factory = new antlr.ASTFactory();
        AST r = factory.create(0,"AST ROOT");
        r.setFirstChild(commonAST);

        final ASTFrame frame = new ASTFrame("Java AST", r);
        frame.setVisible(true);
        frame.addWindowListener(
            new WindowAdapter() {
               public void windowClosing (WindowEvent e) {
                   frame.setVisible(false); // hide the Frame
                   frame.dispose();
                   System.exit(0);
               }
            }
        );
        while(true);
    }

    public static AST parsePattern(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public void invokeParseRule(EQLStatementParser parser) throws TokenStreamException, RecognitionException
            {
                parser.startPatternExpressionRule();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static AST parseEQL(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public void invokeParseRule(EQLStatementParser parser) throws TokenStreamException, RecognitionException
            {
                parser.startEQLExpressionRule();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static AST parseEventProperty(String text) throws Exception
    {
        ParseRuleSelector startRuleSelector = new ParseRuleSelector()
        {
            public void invokeParseRule(EQLStatementParser parser) throws TokenStreamException, RecognitionException
            {
                parser.startEventPropertyRule();
            }
        };
        return parse(startRuleSelector, text);
    }

    public static AST parse(ParseRuleSelector parseRuleSelector, String text) throws Exception
    {
        EQLStatementLexer lexer = new EQLStatementLexer(new StringReader(text));
        EQLStatementParser parser = new EQLStatementParser(lexer);
        parseRuleSelector.invokeParseRule(parser);
        return parser.getAST();
    }

    private static Log log = LogFactory.getLog(SupportParserHelper.class);
}

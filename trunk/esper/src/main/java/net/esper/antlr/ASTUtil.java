package net.esper.antlr;

import org.antlr.runtime.Token;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class ASTUtil
{
    private static Log log = LogFactory.getLog(ASTUtil.class);

    private final static String PROPERTY_ENABLED_AST_DUMP = "ENABLE_AST_DUMP";

    /**
     * Dump the AST node to system.out.
     * @param ast to dump
     */
    public static void dumpAST(Tree ast)
    {
        if (System.getProperty(PROPERTY_ENABLED_AST_DUMP) != null)
        {
            StringWriter writer = new StringWriter();
            PrintWriter printer = new PrintWriter(writer);

            renderNode(new char[0], ast, printer);
            dumpAST(printer, ast, 2);

            log.info(".dumpAST ANTLR Tree dump follows...\n" + writer.toString());
        }
    }

    private static void dumpAST(PrintWriter printer, Tree ast, int ident)
    {
        char[] identChars = new char[ident];
        Arrays.fill(identChars, ' ');

        for (int i = 0; i < ast.getChildCount(); i++)
        {
            Tree node = ast.getChild(i);
            if (node == null)
            {
                throw new NullPointerException("Null AST node");
            }
            renderNode(identChars, node, printer);
            dumpAST(printer, node, ident + 2);
        }
    }

    public static void printTokens(CommonTokenStream tokens)
    {
        if (log.isDebugEnabled())
        {
            StringWriter writer = new StringWriter();
            PrintWriter printer = new PrintWriter(writer);
            for (int i = 0; i < tokens.size(); i++)
            {
                Token t = tokens.get(i);
                String text = t.getText();
                if (text.trim().length() == 0)
                {
                    printer.print("'" + text + "'");                    
                }
                else
                {
                    printer.print(text);
                }
                printer.print('[');
                printer.print(t.getType());
                printer.print(']');
                printer.print(" ");
            }
            printer.println();
            log.debug("Tokens: " + writer.toString());
        }
    }

    private static void renderNode(char[] ident, Tree node, PrintWriter printer)
    {
        printer.print(ident);
        printer.print(node.getText());
        printer.print(" [");
        printer.print(node.getType());
        printer.print("]");
        printer.println();
    }

}

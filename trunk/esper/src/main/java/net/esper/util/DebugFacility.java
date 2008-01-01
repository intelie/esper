/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.util;

import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Utility class for enabling certain debugging using system properties.
 */
public class DebugFacility
{
    private static Log log = LogFactory.getLog(DebugFacility.class);
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
            renderNode(identChars, node, printer);
            dumpAST(printer, node, ident + 2);
        }
    }

    private static void renderNode(char[] ident, Tree node, PrintWriter printer)
    {
        printer.print(ident);
        printer.println(node);
    }
}

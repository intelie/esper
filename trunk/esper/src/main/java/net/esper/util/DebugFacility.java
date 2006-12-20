package net.esper.util;

import antlr.DumpASTVisitor;
import antlr.collections.AST;

/**
 * Utility class for enabling certain debugging using system properties.
 */
public class DebugFacility
{
    private final static String PROPERTY_ENABLED_AST_DUMP = "ENABLE_AST_DUMP";

    /**
     * Dump the AST node to system.out.
     * @param ast to dump
     */
    public static void dumpAST(AST ast)
    {
        if (System.getProperty(PROPERTY_ENABLED_AST_DUMP) != null)
        {
            (new DumpASTVisitor()).visit(ast);
        }
    }
}

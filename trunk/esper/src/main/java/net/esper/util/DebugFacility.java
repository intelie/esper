/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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

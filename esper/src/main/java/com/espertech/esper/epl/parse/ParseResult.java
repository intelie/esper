/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.parse;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.Tree;

/**
 * Result of a parse action.
 */
public class ParseResult
{
    private Tree tree;
    private String expressionWithoutAnnotations;
    private CommonTokenStream tokenStream;

    /**
     * Ctor.
     * @param tree parse tree
     * @param expressionWithoutAnnotations expression text no annotations, or null if same
     */
    public ParseResult(Tree tree, String expressionWithoutAnnotations, CommonTokenStream tokenStream)
    {
        this.tree = tree;
        this.expressionWithoutAnnotations = expressionWithoutAnnotations;
        this.tokenStream = tokenStream;
    }

    /**
     * AST.
     * @return ast
     */
    public Tree getTree()
    {
        return tree;
    }

    /**
     * Returns the expression text no annotations.
     * @return expression text no annotations.
     */
    public String getExpressionWithoutAnnotations()
    {
        return expressionWithoutAnnotations;
    }

    public CommonTokenStream getTokenStream() {
        return tokenStream;
    }
}

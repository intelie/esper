package com.espertech.esper.epl.parse;

import org.antlr.runtime.tree.Tree;

public class ParseResult
{
    private Tree tree;
    private String expressionWithoutAnnotations;

    public ParseResult(Tree tree, String expressionWithoutAnnotations)
    {
        this.tree = tree;
        this.expressionWithoutAnnotations = expressionWithoutAnnotations;
    }

    public Tree getTree()
    {
        return tree;
    }

    public String getExpressionWithoutAnnotations()
    {
        return expressionWithoutAnnotations;
    }
}

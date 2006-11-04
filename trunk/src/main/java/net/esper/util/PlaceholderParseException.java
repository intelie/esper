package net.esper.util;

import antlr.collections.AST;
import antlr.CommonAST;

import java.util.List;
import java.util.LinkedList;

public class PlaceholderParseException extends Exception
{
    public PlaceholderParseException(String message)
    {
        super(message);
    }
}

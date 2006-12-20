package net.esper.util;

import antlr.collections.AST;
import antlr.CommonAST;

import java.util.List;
import java.util.LinkedList;

/**
 * Exception to indicate a parse error in parsing placeholders.
 */
public class PlaceholderParseException extends Exception
{
    /**
     * Ctor.
     * @param message is the error message
     */
    public PlaceholderParseException(String message)
    {
        super(message);
    }
}

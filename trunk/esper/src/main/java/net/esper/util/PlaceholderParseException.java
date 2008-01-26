/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
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

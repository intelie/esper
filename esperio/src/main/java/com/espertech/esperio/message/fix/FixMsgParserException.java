/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.message.fix;

/**
 * Exception parsing a fix message.
 */
public class FixMsgParserException extends Exception
{
    /**
     * Ctor.
     * @param message error message
     */
    public FixMsgParserException(String message) {
        super(message);
    }

    /**
     * Ctor.
     * @param message error message
     * @param cause inner exception
     */
    public FixMsgParserException(String message, Throwable cause) {
        super(message, cause);
    }
}

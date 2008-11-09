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
public class FixMsgUnrecognizableException extends FixMsgParserException
{
    /**
     * Ctor.
     * @param message error message
     * @param fixMsgText fix text
     */
    public FixMsgUnrecognizableException(String message, String fixMsgText) {
        super(message + " for message text '" + fixMsgText + "'");
    }

    /**
     * Ctor.
     * @param message error message
     */
    public FixMsgUnrecognizableException(String message) {
        super(message);
    }
}

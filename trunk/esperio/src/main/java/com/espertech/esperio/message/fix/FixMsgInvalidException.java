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
 * Indicates an invalid Fix message.
 */
public class FixMsgInvalidException extends FixMsgParserException
{
    /**
     * Ctor.
     * @param message invalid fix message
     */
    public FixMsgInvalidException(String message) {
        super(message);
    }
}

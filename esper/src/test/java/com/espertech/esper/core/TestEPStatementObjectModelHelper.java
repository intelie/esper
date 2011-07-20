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

package com.espertech.esper.core;

import junit.framework.TestCase;

import java.io.StringWriter;

import com.espertech.esper.core.EPStatementObjectModelHelper;

public class TestEPStatementObjectModelHelper extends TestCase
{
    public void testRenderEPL()
    {
        assertEquals("null", tryConstant(null));
        assertEquals("\"\"", tryConstant(""));
        assertEquals("1", tryConstant(1));
        assertEquals("\"abc\"", tryConstant("abc"));
    }

    private String tryConstant(Object value)
    {
        StringWriter writer = new StringWriter();
        EPStatementObjectModelHelper.renderEPL(writer, value);
        return writer.toString();
    }
}

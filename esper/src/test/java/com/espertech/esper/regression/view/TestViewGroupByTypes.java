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

package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestViewGroupByTypes extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testType()
    {
        String viewStmt = "select * from " + SupportBean.class.getName() +
                ".std:groupwin(intPrimitive).win:length(4).std:groupwin(longBoxed).stat:uni(doubleBoxed)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(viewStmt);

        assertEquals(int.class, stmt.getEventType().getPropertyType("intPrimitive"));
        assertEquals(Long.class, stmt.getEventType().getPropertyType("longBoxed"));
        assertEquals(Double.class, stmt.getEventType().getPropertyType("stddev"));
        assertEquals(8, stmt.getEventType().getPropertyNames().length);
    }
}

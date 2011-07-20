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

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestDotExpressionDuckTyping extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

	public void testDuckTyping()
	{
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getExpression().setDuckTyping(true);

	    epService = EPServiceProviderManager.getDefaultProvider(config);
	    epService.initialize();
        listener = new SupportUpdateListener();

        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanDuckType", SupportBeanDuckType.class);

        String epl = "select " +
                "(dt).makeString() as strval, " +
                "(dt).makeInteger() as intval, " +
                "(dt).makeCommon().makeString() as commonstrval, " +
                "(dt).makeCommon().makeInteger() as commonintval, " +
                "(dt).returnDouble() as commondoubleval " +
                "from SupportBeanDuckType dt ";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        Object[][] rows = new Object[][] {
                {"strval", Object.class},
                {"intval", Object.class},
                {"commonstrval", Object.class},
                {"commonintval", Object.class},
                {"commondoubleval", double.class}   // this one is strongly typed
                };
        for (int i = 0; i < rows.length; i++) {
            EventPropertyDescriptor prop = stmt.getEventType().getPropertyDescriptors()[i];
            assertEquals(rows[i][0], prop.getPropertyName());
            assertEquals(rows[i][1], prop.getPropertyType());
        }

        String[] fields = "strval,intval,commonstrval,commonintval,commondoubleval".split(",");

        epService.getEPRuntime().sendEvent(new SupportBeanDuckTypeOne("x"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"x", null, null, -1, 12.9876d});

        epService.getEPRuntime().sendEvent(new SupportBeanDuckTypeTwo(-10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, -10, "mytext", null, 11.1234d});
    }
}

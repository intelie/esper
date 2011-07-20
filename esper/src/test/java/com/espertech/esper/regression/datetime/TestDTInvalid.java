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

package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestDTInvalid extends TestCase {

    private EPServiceProvider epService;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportBean_ST0_Container", SupportBean_ST0_Container.class);
        config.addEventType("SupportDateTime", SupportDateTime.class);
        config.addImport(SupportBean_ST0_Container.class);
        config.addPlugInSingleRowFunction("makeTest", SupportBean_ST0_Container.class.getName(), "makeTest");
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testInvalid() {
        String epl;

        // invalid incompatible params
        epl = "select contained.set('hour', 1) from SupportBean_ST0_Container";
        tryInvalid(epl, "Error starting statement: Date-time enumeration method 'set' requires either a Calendar, Date or long value as input or events of an event type that declares a timestamp property but received java.util.List [select contained.set('hour', 1) from SupportBean_ST0_Container]");

        // invalid incompatible params
        epl = "select window(*).set('hour', 1) from SupportBean.win:keepall()";
        tryInvalid(epl, "Error starting statement: Date-time enumeration method 'set' requires either a Calendar, Date or long value as input or events of an event type that declares a timestamp property [select window(*).set('hour', 1) from SupportBean.win:keepall()]");

        // invalid incompatible params
        epl = "select utildate.set('invalid') from SupportDateTime";
        tryInvalid(epl, "Error starting statement: Parameters mismatch for date-time method 'set', the method requires an expression providing a string-type calendar field name and an expression providing an integer-type value [select utildate.set('invalid') from SupportDateTime]");

        // invalid lambda parameter
        epl = "select utildate.set(x => true) from SupportDateTime";
        tryInvalid(epl, "Error starting statement: Parameters mismatch for date-time method 'set', the method requires an expression providing a string-type calendar field name and an expression providing an integer-type value [select utildate.set(x => true) from SupportDateTime]");

        // invalid no parameter
        epl = "select utildate.set() from SupportDateTime";
        tryInvalid(epl, "Error starting statement: Parameters mismatch for date-time method 'set', the method requires an expression providing a string-type calendar field name and an expression providing an integer-type value [select utildate.set() from SupportDateTime]");

        // invalid wrong parameter
        epl = "select utildate.set(1) from SupportDateTime";
        tryInvalid(epl, "Error starting statement: Parameters mismatch for date-time method 'set', the method requires an expression providing a string-type calendar field name and an expression providing an integer-type value [select utildate.set(1) from SupportDateTime]");
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }
}

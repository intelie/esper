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

package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collections;
import java.util.Map;

public class TestInsertIntoPopulateCreateStream extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testCreateStream()
    {
        epService.getEPAdministrator().createEPL("create schema MyEvent(myId int)");
        epService.getEPAdministrator().createEPL("create schema CompositeEvent(c1 MyEvent, c2 MyEvent, rule string)");
        epService.getEPAdministrator().createEPL("insert into MyStream select c, 'additionalValue' as value from MyEvent c");
        epService.getEPAdministrator().createEPL("insert into CompositeEvent select e1.c as c1, e2.c as c2, '4' as rule " +
                "from pattern [e1=MyStream -> e2=MyStream]");
        epService.getEPAdministrator().createEPL("@Name('Target') select * from CompositeEvent");
        epService.getEPAdministrator().getStatement("Target").addListener(listener);

        epService.getEPRuntime().sendEvent(makeEvent(10), "MyEvent");
        epService.getEPRuntime().sendEvent(makeEvent(11), "MyEvent");
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(10, event.get("c1.myId"));
        assertEquals(11, event.get("c2.myId"));
        assertEquals("4", event.get("rule"));
    }

    public void testCreateStreamTwo()
    {
        epService.getEPAdministrator().createEPL("create schema MyEvent(myId int)");
        epService.getEPAdministrator().createEPL("create schema AllMyEvent as (myEvent MyEvent, class String, reverse boolean)");
        epService.getEPAdministrator().createEPL("create schema SuspectMyEvent as (myEvent MyEvent, class String)");

        EPStatement stmtOne = epService.getEPAdministrator().createEPL("insert into AllMyEvent " +
                                                 "select c as myEvent, 'test' as class, false as reverse " +
                                                 "from MyEvent(myId=1) c");
        stmtOne.addListener(listener);

        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("insert into SuspectMyEvent " +
                                                 "select c.myEvent as myEvent, class " +
                                                 "from AllMyEvent(not reverse) c");
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmtTwo.addListener(listenerTwo);

        epService.getEPRuntime().sendEvent(makeEvent(1), "MyEvent");
        
        EventBean resultOne = listener.assertOneGetNewAndReset();
        assertTrue(resultOne.get("myEvent") instanceof EventBean);
        assertEquals(1, ((EventBean)resultOne.get("myEvent")).get("myId"));
        assertNotNull(stmtOne.getEventType().getFragmentType("myEvent"));

        EventBean resultTwo = listenerTwo.assertOneGetNewAndReset();
        assertTrue(resultTwo.get("myEvent") instanceof EventBean);
        assertEquals(1, ((EventBean)resultTwo.get("myEvent")).get("myId"));
        assertNotNull(stmtTwo.getEventType().getFragmentType("myEvent"));
    }

    private Map<String, Object> makeEvent(int myId) {
        return Collections.<String, Object>singletonMap("myId", myId);
    }
}

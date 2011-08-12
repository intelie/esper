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

package com.espertech.esper.epl.join.exec;

import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.join.exec.base.IndexedTableLookupStrategy;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import junit.framework.TestCase;

import java.util.Set;

public class TestIndexedTableLookupStrategy extends TestCase
{
    private EventType eventType;
    private IndexedTableLookupStrategy lookupStrategy;
    private PropertyIndexedEventTable propertyMapEventIndex;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);

        propertyMapEventIndex = new PropertyIndexedEventTable(0, eventType, new String[] {"string", "intPrimitive"});
        lookupStrategy = new IndexedTableLookupStrategy(eventType, new String[] {"string", "intPrimitive"}, propertyMapEventIndex);

        propertyMapEventIndex.add(new EventBean[] {SupportEventBeanFactory.createObject(new SupportBean("a", 1))});
    }

    public void testLookup()
    {
        Set<EventBean> events = lookupStrategy.lookup(SupportEventBeanFactory.createObject(new SupportBean("a", 1)), null, null);

        assertEquals(1, events.size());
    }

    public void testInvalid()
    {
        try
        {
            new IndexedTableLookupStrategy(eventType, new String[] {"string", "xxx"}, propertyMapEventIndex);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }
}

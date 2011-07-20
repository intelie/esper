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

package com.espertech.esper.event.property;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.bean.BeanEventType;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventAdapterService;
import junit.framework.TestCase;

public class TestMappedProperty extends TestCase
{
    private MappedProperty[] mapped;
    private EventBean event;
    private BeanEventType eventType;

    public void setUp()
    {
        mapped = new MappedProperty[2];
        mapped[0] = new MappedProperty("mapped", "keyOne");
        mapped[1] = new MappedProperty("mapped", "keyTwo");

        event = SupportEventBeanFactory.createObject(SupportBeanComplexProps.makeDefaultBean());
        eventType = (BeanEventType)event.getEventType();
    }

    public void testGetGetter()
    {
        Object[] expected = new String[] {"valueOne", "valueTwo"};
        for (int i = 0; i < mapped.length; i++)
        {
            EventPropertyGetter getter = mapped[i].getGetter(eventType, SupportEventAdapterService.getService());
            assertEquals(expected[i], getter.get(event));
        }

        // try invalid case
        MappedProperty mpd = new MappedProperty("dummy", "dummy");
        assertNull(mpd.getGetter(eventType, SupportEventAdapterService.getService()));
    }

    public void testGetPropertyType()
    {
        Class[] expected = new Class[] {String.class, String.class};
        for (int i = 0; i < mapped.length; i++)
        {
            assertEquals(expected[i], mapped[i].getPropertyType(eventType, SupportEventAdapterService.getService()));
        }

        // try invalid case
        MappedProperty mpd = new MappedProperty("dummy", "dummy");
        assertNull(mpd.getPropertyType(eventType, SupportEventAdapterService.getService()));
        mpd = new MappedProperty("mapProperty", "dummy");
        assertEquals(String.class, mpd.getPropertyType(eventType, SupportEventAdapterService.getService()));
    }
}

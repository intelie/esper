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

import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.support.event.SupportEventAdapterService;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class TestPropertyParser extends TestCase
{
    private EventAdapterService eventAdapterService;

    public void setUp()
    {
        eventAdapterService = SupportEventAdapterService.getService();
    }

    public void testParse() throws Exception
    {
        Property property = PropertyParser.parse("a", false);
        assertEquals("a", ((SimpleProperty)property).getPropertyNameAtomic());

        property = PropertyParser.parse("i[1]", false);
        assertEquals("i", ((IndexedProperty)property).getPropertyNameAtomic());
        assertEquals(1, ((IndexedProperty)property).getIndex());

        property = PropertyParser.parse("m('key')", false);
        assertEquals("m", ((MappedProperty)property).getPropertyNameAtomic());
        assertEquals("key", ((MappedProperty)property).getKey());

        property = PropertyParser.parse("a.b[2].c('m')", false);
        List<Property> nested = ((NestedProperty)property).getProperties();
        assertEquals(3, nested.size());
        assertEquals("a", ((SimpleProperty)nested.get(0)).getPropertyNameAtomic());
        assertEquals("b", ((IndexedProperty)nested.get(1)).getPropertyNameAtomic());
        assertEquals(2, ((IndexedProperty)nested.get(1)).getIndex());
        assertEquals("c", ((MappedProperty)nested.get(2)).getPropertyNameAtomic());
        assertEquals("m", ((MappedProperty)nested.get(2)).getKey());

        property = PropertyParser.parse("a", true);
        assertEquals("a", ((DynamicSimpleProperty)property).getPropertyNameAtomic());
    }

    public void testParseMapKey() throws Exception
    {
        assertEquals("a", tryKey("a"));
    }

    private String tryKey(String key) throws Exception
    {
        String propertyName = "m(\"" + key + "\")";
        log.debug(".tryKey propertyName=" + propertyName + " key=" + key);
        Property property = PropertyParser.parse(propertyName, false);
        return ((MappedProperty)property).getKey();
    }

    private static Log log = LogFactory.getLog(TestPropertyParser.class);
}

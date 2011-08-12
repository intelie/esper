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

package com.espertech.esper.event.bean;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.event.EventTypeIdGeneratorImpl;
import com.espertech.esper.support.bean.SupportBeanCombinedProps;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestNestedPropertyGetter extends TestCase
{
    private NestedPropertyGetter getter;
    private NestedPropertyGetter getterNull;
    private EventBean event;
    private SupportBeanCombinedProps bean;
    private BeanEventTypeFactory beanEventTypeFactory;

    public void setUp() throws Exception
    {
        beanEventTypeFactory = new BeanEventAdapter(new ConcurrentHashMap<Class, BeanEventType>(), SupportEventAdapterService.getService(), new EventTypeIdGeneratorImpl());
        bean = SupportBeanCombinedProps.makeDefaultBean();
        event = SupportEventBeanFactory.createObject(bean);

        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();
        getters.add(makeGetterOne(0));
        getters.add(makeGetterTwo("0ma"));
        getter = new NestedPropertyGetter(getters, SupportEventAdapterService.getService(),Map.class, null);

        getters = new LinkedList<EventPropertyGetter>();
        getters.add(makeGetterOne(2));
        getters.add(makeGetterTwo("0ma"));
        getterNull = new NestedPropertyGetter(getters, SupportEventAdapterService.getService(), Map.class,null);
    }

    public void testGet()
    {
        assertEquals(bean.getIndexed(0).getMapped("0ma"), getter.get(event));

        // test null value returned
        assertNull(getterNull.get(event));

        try
        {
            getter.get(SupportEventBeanFactory.createObject(""));
            fail();
        }
        catch (PropertyAccessException ex)
        {
            // expected
        }
    }

    private KeyedFastPropertyGetter makeGetterOne(int index)
    {
        FastClass fastClassOne = FastClass.create(Thread.currentThread().getContextClassLoader(), SupportBeanCombinedProps.class);
        FastMethod methodOne = fastClassOne.getMethod("getIndexed", new Class[] {int.class});
        return new KeyedFastPropertyGetter(methodOne, index, SupportEventAdapterService.getService());
    }

    private KeyedFastPropertyGetter makeGetterTwo(String key)
    {
        FastClass fastClassTwo = FastClass.create(Thread.currentThread().getContextClassLoader(), SupportBeanCombinedProps.NestedLevOne.class);
        FastMethod methodTwo = fastClassTwo.getMethod("getMapped", new Class[] {String.class});
        return new KeyedFastPropertyGetter(methodTwo, key, SupportEventAdapterService.getService());
    }
}

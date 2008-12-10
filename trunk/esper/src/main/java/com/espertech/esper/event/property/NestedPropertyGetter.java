/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.event.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;

import java.util.List;

/**
 * Getter for one or more levels deep nested properties.
 */
public class NestedPropertyGetter implements EventPropertyGetter
{
    private final EventPropertyGetter[] getterChain;
    private final EventAdapterService eventAdapterService;

    /**
     * Ctor.
     * @param getterChain is the chain of getters to retrieve each nested property
     * @param eventAdapterService is the cache and factory for event bean types and event wrappers
     */
    public NestedPropertyGetter(List<EventPropertyGetter> getterChain, EventAdapterService eventAdapterService)
    {
        this.getterChain = getterChain.toArray(new EventPropertyGetter[getterChain.size()]);
        this.eventAdapterService = eventAdapterService;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object value = null;

        for (int i = 0; i < getterChain.length; i++)
        {
            value = getterChain[i].get(eventBean);

            if (value == null)
            {
                return null;
            }

            if (i < (getterChain.length - 1))
            {
                EventType type = eventAdapterService.getBeanEventTypeFactory().createBeanType(value.getClass().getName(), value.getClass(), false);
                eventBean = new BeanEventBean(value, type);
            }
        }
        return value;
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        int lastElementIndex = getterChain.length - 1;

        // walk the getter chain up to the previous-to-last element, returning its object value.
        // any null values in between mean the property does not exists
        for (int i = 0; i < getterChain.length - 1; i++)
        {
            Object value = getterChain[i].get(eventBean);

            if (value == null)
            {
                return false;
            }
            else
            {
                EventType type = eventAdapterService.getBeanEventTypeFactory().createBeanType(value.getClass().getName(), value.getClass(), false);
                eventBean = new BeanEventBean(value, type);
            }
        }

        return getterChain[lastElementIndex].isExistsProperty(eventBean);
    }

    public EventBean getFragment(EventBean eventBean)
    {
        return null; // TODO
    }

    public Integer getIndexSize(EventBean eventBean)
    {
        return null; // TODO
    }

    public EventBean[] getFragmentArray(EventBean eventBean)
    {
        return null; // TODO
    }    
}

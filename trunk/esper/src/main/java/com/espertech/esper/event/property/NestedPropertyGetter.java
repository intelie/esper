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
import java.util.List;

/**
 * Getter for one or more levels deep nested properties.
 */
public class NestedPropertyGetter implements EventPropertyGetter
{
    private final EventPropertyGetter[] getterChain;
    private final BeanEventTypeFactory beanEventTypeFactory;

    /**
     * Ctor.
     * @param getterChain is the chain of getters to retrieve each nested property
     * @param beanEventTypeFactory is the chache and factory for event bean types and event wrappers
     */
    public NestedPropertyGetter(List<EventPropertyGetter> getterChain, BeanEventTypeFactory beanEventTypeFactory)
    {
        this.getterChain = getterChain.toArray(new EventPropertyGetter[getterChain.size()]);
        this.beanEventTypeFactory = beanEventTypeFactory;
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
                EventType type = beanEventTypeFactory.createBeanType(value.getClass().getName(), value.getClass());
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
                EventType type = beanEventTypeFactory.createBeanType(value.getClass().getName(), value.getClass());
                eventBean = new BeanEventBean(value, type);
            }
        }

        return getterChain[lastElementIndex].isExistsProperty(eventBean);
    }
}

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
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;

import java.util.List;
import java.util.Map;

/**
 * Getter for one or more levels deep nested properties of maps.
 */
public class MapNestedPropertyGetter implements EventPropertyGetter
{
    private final EventPropertyGetter[] getterChain;
    private final EventAdapterService eventAdaperService;
    private final int lastElementIndex;

    /**
     * Ctor.
     * @param getterChain is the chain of getters to retrieve each nested property
     * @param eventAdaperService is a factory for POJO bean event types
     */
    public MapNestedPropertyGetter(List<EventPropertyGetter> getterChain,
                                   EventAdapterService eventAdaperService)
    {
        this.getterChain = getterChain.toArray(new EventPropertyGetter[getterChain.size()]);
        lastElementIndex = this.getterChain.length - 1;
        this.eventAdaperService = eventAdaperService;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object value = null;

        for (int i = 0; i < getterChain.length; i++)
        {
            Object result = getterChain[i].get(eventBean);

            if (result == null)
            {
                return null;
            }

            // this is not the last element
            if (i < lastElementIndex)
            {
                if (result instanceof Map)
                {
                    eventBean = new MapEventBean((Map) result, null);
                }
                else
                {
                    BeanEventType type = eventAdaperService.getBeanEventTypeFactory().createBeanType(result.getClass().getName(), result.getClass(), false);
                    eventBean = eventAdaperService.adapterForBean(result, type);
                }
            }
            else
            {
                value = result;
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
            Object result = getterChain[i].get(eventBean);

            if (result == null)
            {
                return false;
            }
            else
            {
                if (result instanceof Map)
                {
                    eventBean = new MapEventBean((Map) result, null);
                }
                else
                {
                    BeanEventType type = eventAdaperService.getBeanEventTypeFactory().createBeanType(result.getClass().getName(), result.getClass(), false);
                    eventBean = new BeanEventBean(result, type);
                }
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

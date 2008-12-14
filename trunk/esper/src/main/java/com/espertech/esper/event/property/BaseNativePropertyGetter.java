/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.event.BeanEventType;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.util.JavaClassHelper;

import java.lang.reflect.Array;

/**
 * Base getter for native fragments.
 */
public abstract class BaseNativePropertyGetter implements EventPropertyGetter
{
    private final EventAdapterService eventAdapterService;
    private volatile BeanEventType fragmentEventType;
    private final Class fragmentClassType;
    private boolean isFragmentable;
    private final boolean isArray;

    /**
     * Constructor.
     * @param eventAdapterService factory for event beans and event types
     * @param returnType type of the entry returned
     */
    public BaseNativePropertyGetter(EventAdapterService eventAdapterService, Class returnType)
    {
        this.eventAdapterService = eventAdapterService;
        if (returnType.isArray())
        {
            this.fragmentClassType = returnType.getComponentType();
            isArray = true;
        }
        else
        {
            this.fragmentClassType = returnType;
            isArray = false;
        }
        isFragmentable = true;
    }

    /**
     * Returns the fragment for dynamic properties.
     * @param object to inspect
     * @param eventAdapterService factory for event beans and event types
     * @return fragment
     */
    public static Object getFragmentDynamic(Object object, EventAdapterService eventAdapterService)
    {
        if (object == null)
        {
            return null;
        }

        BeanEventType fragmentEventType = null;
        boolean isArray = false;
        if (object.getClass().isArray())
        {
            if (JavaClassHelper.isFragmentableType(object.getClass().getComponentType()))
            {
                isArray = true;
                fragmentEventType = eventAdapterService.getBeanEventTypeFactory().createBeanTypeNoAlias(object.getClass().getComponentType());
            }
        }
        else
        {
            if (JavaClassHelper.isFragmentableType(object.getClass()))
            {
                fragmentEventType = eventAdapterService.getBeanEventTypeFactory().createBeanTypeNoAlias(object.getClass());
            }
        }

        if (fragmentEventType == null)
        {
            return null;
        }

        if (isArray)
        {
            int len = Array.getLength(object);
            EventBean[] events = new EventBean[len];
            int countFilled = 0;

            for (int i = 0; i < len; i++)
            {
                Object element = Array.get(object, i);
                if (element == null)
                {
                    continue;
                }

                events[countFilled] = eventAdapterService.adapterForBean(element, fragmentEventType);
                countFilled++;
            }

            if (countFilled == len)
            {
                return events;
            }

            if (countFilled == 0)
            {
                return new EventBean[0];
            }

            EventBean[] returnVal = new EventBean[countFilled];
            System.arraycopy(events, 0, returnVal, 0, countFilled);
            return returnVal;
        }
        else
        {
            return eventAdapterService.adapterForBean(object, fragmentEventType);
        }
    }

    public Object getFragment(EventBean eventBean)
    {
        Object object = get(eventBean);
        if (object == null)
        {
            return null;
        }

        if (!isFragmentable)
        {
            return null;
        }

        if (fragmentEventType == null)
        {
            if (JavaClassHelper.isFragmentableType(fragmentClassType))
            {
                fragmentEventType = eventAdapterService.getBeanEventTypeFactory().createBeanTypeNoAlias(fragmentClassType);
            }
            else
            {
                isFragmentable = false;
                return null;
            }
        }

        if (isArray)
        {
            int len = Array.getLength(object);
            EventBean[] events = new EventBean[len];
            int countFilled = 0;

            for (int i = 0; i < len; i++)
            {
                Object element = Array.get(object, i);
                if (element == null)
                {
                    continue;
                }
                
                events[countFilled] = eventAdapterService.adapterForBean(element, fragmentEventType);
                countFilled++;
            }

            if (countFilled == len)
            {
                return events;
            }

            if (countFilled == 0)
            {
                return new EventBean[0];
            }

            EventBean[] returnVal = new EventBean[countFilled];
            System.arraycopy(events, 0, returnVal, 0, countFilled);
            return returnVal;
        }
        else
        {
            return eventAdapterService.adapterForBean(object, fragmentEventType);
        }
    }
}
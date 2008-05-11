/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.indicator.jmx;

import javax.management.DynamicMBean;

import com.espertech.esper.event.EventBean;

/**
 * DynamicMBean interface extension to set the last element to render in a JMX view.
 */
public interface JMXLastElementObserver extends DynamicMBean
{
    /**
     * Update JMX last element view with a new value.
     * @param element is the new last value
     */
    public void setLastValue(EventBean element);
}

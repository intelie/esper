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

package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

/**
 * Interface for reading all event properties of an event.
 */
public interface EventBeanReader
{
    /**
     * Returns all event properties in the exact order they appear as properties.
     * @param event to read
     * @return property values
     */
    public Object[] read(EventBean event);
}

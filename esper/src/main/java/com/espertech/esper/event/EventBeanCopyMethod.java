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
 * Implementations copy the event object for controlled modification (shallow copy).
 */
public interface EventBeanCopyMethod
{
    /**
     * Copy the event bean returning a shallow copy.
     * @param event to copy
     * @return shallow copy
     */
    public EventBean copy(EventBean event);
}

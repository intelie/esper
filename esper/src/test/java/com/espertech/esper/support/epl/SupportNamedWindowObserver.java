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

package com.espertech.esper.support.epl;

import com.espertech.esper.epl.named.NamedWindowLifecycleObserver;
import com.espertech.esper.epl.named.NamedWindowLifecycleEvent;

import java.util.List;
import java.util.ArrayList;

import junit.framework.Assert;

public class SupportNamedWindowObserver implements NamedWindowLifecycleObserver
{
    private List<NamedWindowLifecycleEvent> events = new ArrayList<NamedWindowLifecycleEvent>();

    public void observe(NamedWindowLifecycleEvent event)
    {
        events.add(event);
    }

    public List<NamedWindowLifecycleEvent> getEvents()
    {
        return events;
    }

    public NamedWindowLifecycleEvent getFirstAndReset()
    {
        Assert.assertEquals(1, events.size());
        NamedWindowLifecycleEvent event = events.get(0);
        events.clear();
        return event;
    }
}

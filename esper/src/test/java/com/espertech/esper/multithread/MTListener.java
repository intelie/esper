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

package com.espertech.esper.multithread;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import java.util.List;
import java.util.LinkedList;

public class MTListener implements UpdateListener
{
    private final String fieldName;
    private List values;

    public MTListener(String fieldName)
    {
        this.fieldName = fieldName;
        values = new LinkedList();
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        Object value = newEvents[0].get(fieldName);

        synchronized(values)
        {
            values.add(value);
        }
    }

    public List getValues()
    {
        return values;
    }
}

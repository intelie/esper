/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;

import java.util.Iterator;

public interface AggregationAccess
{
    void applyLeave(EventBean[] eventsPerStream);
    void applyEnter(EventBean[] eventsPerStream);

    public EventBean getFirstValue();
    public EventBean getLastValue();
    public EventBean getFirstNthValue(int index);
    public EventBean getLastNthValue(int index);
    public Iterator<EventBean> iterator();
    public int size();

    public void clear();

}

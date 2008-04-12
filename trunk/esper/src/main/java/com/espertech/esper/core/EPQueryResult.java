package com.espertech.esper.core;

import com.espertech.esper.event.EventBean;

import java.util.Iterator;

public interface EPQueryResult
{
    public Iterator<EventBean> iterator();
    public int getRowCount();
    public EventBean[] getArray();
}

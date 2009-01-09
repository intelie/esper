package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;

public interface PropertyEvaluator
{
    public EventBean[] getProperty(EventBean event);
    public FragmentEventType getFragmentEventType();
}

package com.espertech.esper.regression.rev;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;

public class RevisionBeanHolder
{
    private long version;
    private EventBean eventBean;
    private EventPropertyGetter[] getters;

    public RevisionBeanHolder(long version, EventBean eventBean, EventPropertyGetter[] getters)
    {
        this.version = version;
        this.eventBean = eventBean;
        this.getters = getters;
    }

    public long getVersion()
    {
        return version;
    }

    public EventBean getEventBean()
    {
        return eventBean;
    }

    public EventPropertyGetter[] getGetters()
    {
        return getters;
    }

    public Object getValueForProperty(int propertyNumber)
    {
        return getters[propertyNumber].get(eventBean);
    }
}

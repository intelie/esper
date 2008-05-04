package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;

public class RevisionGetterParameters
{
    private final String propertyName;
    private final int propertyNumber;
    private final EventPropertyGetter fullGetter;
    private final int[] propertyGroups;

    public RevisionGetterParameters(String propertyName, int propertyNumber, EventPropertyGetter fullGetter, int[] authoritySets)
    {
        this.propertyName = propertyName;
        this.propertyNumber = propertyNumber;
        this.fullGetter = fullGetter;
        this.propertyGroups = authoritySets;
    }

    public int[] getPropertyGroups()
    {
        return propertyGroups;
    }

    public int getPropertyNumber()
    {
        return propertyNumber;
    }

    public EventPropertyGetter getFullGetter()
    {
        return fullGetter;
    }
}

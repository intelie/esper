package com.espertech.esper.client;

public class EventTypeFragment
{
    private EventType fragmentType;
    private boolean isIndexed;

    public EventTypeFragment(EventType fragmentEventType, boolean indexed)
    {
        this.fragmentType = fragmentEventType;
        isIndexed = indexed;
    }

    public boolean isIndexed()
    {
        return isIndexed;
    }

    public EventType getFragmentType()
    {
        return fragmentType;
    }
}

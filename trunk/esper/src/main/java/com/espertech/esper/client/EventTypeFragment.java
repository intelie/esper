package com.espertech.esper.client;

// If the property is itself an event
// If the property is available as an event itself
public class EventTypeFragment
{
    private EventType fragmentType;
    private boolean isIndexed;
    private boolean isNative;

    public EventTypeFragment(EventType fragmentType, boolean indexed, boolean aNative)
    {
        this.fragmentType = fragmentType;
        isIndexed = indexed;
        isNative = aNative;
    }

    public boolean isIndexed()
    {
        return isIndexed;
    }

    public EventType getFragmentType()
    {
        return fragmentType;
    }

    public boolean isNative()
    {
        return isNative;
    }
}

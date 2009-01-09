package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;

public class PropertyEvaluatorSimple implements PropertyEvaluator
{
    private final EventPropertyGetter getter;
    private final FragmentEventType fragmentEventType;

    public PropertyEvaluatorSimple(EventPropertyGetter getter, FragmentEventType fragmentEventType)
    {
        this.fragmentEventType = fragmentEventType;
        this.getter = getter;
    }

    public EventBean[] getProperty(EventBean event)
    {
        try
        {
            Object result = getter.getFragment(event);

            if (fragmentEventType.isIndexed())
            {
                return (EventBean[]) result;
            }
            else
            {
                return new EventBean[] {(EventBean) result};
            }
        }
        catch (RuntimeException ex)
        {
            ex.printStackTrace();
            // TODO, also test reuse of properties
        }
        return null;
    }

    public FragmentEventType getFragmentEventType()
    {
        return fragmentEventType;
    }
}
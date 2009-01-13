package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.epl.expression.ExprNode;

public class PropertyEvaluatorSimple implements PropertyEvaluator
{
    private final EventPropertyGetter getter;
    private final FragmentEventType fragmentEventType;
    private final ExprNode filter;

    public PropertyEvaluatorSimple(EventPropertyGetter getter, FragmentEventType fragmentEventType, ExprNode filter)
    {
        this.fragmentEventType = fragmentEventType;
        this.getter = getter;
        this.filter = filter;
    }

    public EventBean[] getProperty(EventBean event)
    {
        try
        {
            Object result = getter.getFragment(event);

            if (fragmentEventType.isIndexed())
            {
                EventBean[] rows = (EventBean[]) result;
                if (filter == null)
                {
                    
                }
                //return applyWhere((EventBean[]) result);
            }
            else
            {
                //if (checkWhere())
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
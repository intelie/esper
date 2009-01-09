package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.spec.PropertyEvalSpec;
import com.espertech.esper.epl.expression.ExprValidationException;

public class PropertyEvaluatorFactory
{
    public static PropertyEvaluator makeEvaluator(PropertyEvalSpec spec, EventType sourceEventType)
            throws ExprValidationException
    {
        int length = spec.getPropertyNames().length;
        EventPropertyGetter[] getters = new EventPropertyGetter[length];
        FragmentEventType types[] = new FragmentEventType[length];
        EventType currentEventType = sourceEventType;

        for (int i = 0; i < length; i++)
        {
            String propertyName = spec.getPropertyNames()[i];
            FragmentEventType fragmentEventType = currentEventType.getFragmentType(propertyName);
            if (fragmentEventType == null)
            {
                throw new ExprValidationException("Property expression '" + propertyName + "' against type '" + currentEventType.getName() + "' does not return a fragmentable property value");
            }
            EventPropertyGetter getter = currentEventType.getGetter(propertyName);
            if (getter == null)
            {
                throw new ExprValidationException("Property expression '" + propertyName + "' against type '" + currentEventType.getName() + "' does not return a fragmentable property value");
            }

            currentEventType = fragmentEventType.getFragmentType();
            types[i] = fragmentEventType;
            getters[i] = getter;
        }

        if (length == 1)
        {
            return new PropertyEvaluatorSimple(getters[0], types[0]);
        }
        else
        {
            return new PropertyEvaluatorNested(getters, types);
        }
    }
}
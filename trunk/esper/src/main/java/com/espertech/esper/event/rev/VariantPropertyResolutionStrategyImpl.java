package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventType;

// - properties that don't have the same type on all types are allowed?
public class VariantPropertyResolutionStrategyImpl implements VariantPropertyResolutionStrategy
{
    public VariantPropertyDesc resolveProperty(String propertyName, EventType[] variants)
    {
        boolean existsInAll = true;
        Class commonType = null;

        for (int i = 0; i < variants.length; i++)
        {
            Class type = variants[i].getPropertyType(propertyName);
            if (type == null)
            {
                existsInAll = false;
                continue;
            }

            if (commonType == null)
            {
                commonType = type;
                continue;
            }

            // compare types
            if (type.equals(commonType))
            {
                continue;
            }

            commonType = Object.class;
        }



        return null;
    }
}

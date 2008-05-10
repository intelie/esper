package com.espertech.esper.event.rev;

import com.espertech.esper.event.*;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.SimpleTypeCaster;
import com.espertech.esper.util.SimpleTypeCasterFactory;

import java.util.Set;
import java.util.LinkedHashSet;

public class VariantPropResolutionStrategyAny implements VariantPropResolutionStrategy
{
    private int currentPropertyNumber;
    private VariantPropertyGetterCache propertyGetterCache;

    public VariantPropResolutionStrategyAny(VariantSpec variantSpec)
    {
        propertyGetterCache = new VariantPropertyGetterCache(variantSpec.getEventTypes());
    }

    public VariantPropertyDesc resolveProperty(String propertyName, EventType[] variants)
    {
        // property numbers should start at zero since the serve as array index
        final int assignedPropertyNumber = currentPropertyNumber;
        currentPropertyNumber++;
        propertyGetterCache.addGetters(assignedPropertyNumber, propertyName);

        EventPropertyGetter getter = new EventPropertyGetter()
        {
            public Object get(EventBean eventBean) throws PropertyAccessException
            {
                VariantEventBean variant = (VariantEventBean) eventBean;
                EventPropertyGetter getter = propertyGetterCache.getGetter(assignedPropertyNumber, variant.getUnderlyingEventBean().getEventType());
                if (getter == null)
                {
                    return null;
                }
                return getter.get(variant.getUnderlyingEventBean());
            }

            public boolean isExistsProperty(EventBean eventBean)
            {
                VariantEventBean variant = (VariantEventBean) eventBean;
                EventPropertyGetter getter = propertyGetterCache.getGetter(assignedPropertyNumber, variant.getUnderlyingEventBean().getEventType());
                if (getter == null)
                {
                    return false;
                }
                return getter.isExistsProperty(variant.getUnderlyingEventBean());
            }
        };

        return new VariantPropertyDesc(Object.class, getter, true);
    }
}

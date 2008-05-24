package com.espertech.esper.event.vaevent;

import com.espertech.esper.event.*;

/**
 * A property resolution strategy that allows any type, wherein all properties are Object type.
 */
public class VariantPropResolutionStrategyAny implements VariantPropResolutionStrategy
{
    private int currentPropertyNumber;
    private VariantPropertyGetterCache propertyGetterCache;

    /**
     * Ctor.
     * @param variantSpec specified the preconfigured types
     */
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
                VariantEvent variant = (VariantEvent) eventBean;
                EventPropertyGetter getter = propertyGetterCache.getGetter(assignedPropertyNumber, variant.getUnderlyingEventBean().getEventType());
                if (getter == null)
                {
                    return null;
                }
                return getter.get(variant.getUnderlyingEventBean());
            }

            public boolean isExistsProperty(EventBean eventBean)
            {
                VariantEvent variant = (VariantEvent) eventBean;
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

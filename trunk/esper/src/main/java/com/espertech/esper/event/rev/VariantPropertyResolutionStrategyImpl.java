package com.espertech.esper.event.rev;

import com.espertech.esper.event.*;
import com.espertech.esper.util.SimpleTypeCaster;
import com.espertech.esper.util.SimpleTypeCasterFactory;
import com.espertech.esper.util.JavaClassHelper;

public class VariantPropertyResolutionStrategyImpl implements VariantPropertyResolutionStrategy
{
    private int currentPropertyNumber;
    private VariantPropertyGetterCache propertyGetterCache;

    public VariantPropertyResolutionStrategyImpl(EventType[] knownTypes)
    {
        propertyGetterCache = new VariantPropertyGetterCache(knownTypes);
    }

    public VariantPropertyDesc resolveProperty(String propertyName, EventType[] variants)
    {
        boolean existsInAll = true;
        Class commonType = null;
        boolean mustCoerce = false;
        for (int i = 0; i < variants.length; i++)
        {
            Class type = JavaClassHelper.getBoxedType(variants[i].getPropertyType(propertyName));
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

            if (JavaClassHelper.isNumeric(type))
            {
                if (JavaClassHelper.canCoerce(type, commonType))
                {
                    mustCoerce = true;
                    continue;
                }
                if (JavaClassHelper.canCoerce(commonType, type))
                {
                    mustCoerce = true;
                    commonType = type;
                }
            }

            commonType = Object.class;
        }

        if (!existsInAll)
        {
            return null;
        }

        if (commonType == null)
        {
            return null;
        }

        // property numbers should start at zero since the serve as array index
        final int assignedPropertyNumber = currentPropertyNumber;
        currentPropertyNumber++;
        propertyGetterCache.addGetters(assignedPropertyNumber, propertyName);

        EventPropertyGetter getter;
        if (mustCoerce)
        {
            final SimpleTypeCaster caster = SimpleTypeCasterFactory.getCaster(commonType);
            getter = new EventPropertyGetter()
            {
                public Object get(EventBean eventBean) throws PropertyAccessException
                {
                    VariantEventBean variant = (VariantEventBean) eventBean;
                    EventPropertyGetter getter = propertyGetterCache.getGetter(assignedPropertyNumber, variant.getUnderlyingEventBean().getEventType());
                    if (getter == null)
                    {
                        return null;
                    }
                    Object value = getter.get(variant.getUnderlyingEventBean());
                    if (value == null)
                    {
                        return value;
                    }
                    return caster.cast(value);
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
        }
        else
        {
            getter = new EventPropertyGetter()
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
        }

        return new VariantPropertyDesc(commonType, getter, true);
    }
}

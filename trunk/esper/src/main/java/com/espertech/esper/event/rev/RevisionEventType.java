package com.espertech.esper.event.rev;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.MapPOJOEntryPropertyGetter;
import com.espertech.esper.event.property.Property;
import com.espertech.esper.event.property.PropertyParser;
import com.espertech.esper.event.property.MapPropertyGetter;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Event type of revision events.
 */
public class RevisionEventType implements EventType
{
    private String[] propertyNames;
    private Map<String, RevisionPropertyTypeDesc> propertyDesc;
    private EventAdapterService eventAdapterService;

    /**
     * Ctor.
     * @param propertyDesc describes each properties type
     */
    public RevisionEventType(Map<String, RevisionPropertyTypeDesc> propertyDesc, EventAdapterService eventAdapterService)
    {
        this.propertyDesc = propertyDesc;
        Set<String> keys = propertyDesc.keySet();
        propertyNames = keys.toArray(new String[keys.size()]);
        this.eventAdapterService = eventAdapterService;
    }

    public EventPropertyGetter getGetter(String propertyName)
    {
        RevisionPropertyTypeDesc desc = propertyDesc.get(propertyName);
        if (desc != null)
        {
            return desc.getRevisionGetter();
        }

        // dynamic property names note allowed
        if (propertyName.indexOf('?') != -1)
        {
            return null;
        }

        // see if this is a nested property
        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
        if (index == -1)
        {
            return null; // not a nested property
        }

        // Map event types allow 2 types of properties inside:
        //   - a property that is a Java object is interrogated via bean property getters and BeanEventType
        //   - a property that is a Map itself is interrogated via map property getters

        // Take apart the nested property into a map key and a nested value class property name
        String propertyMap = ASTFilterSpecHelper.unescapeDot(propertyName.substring(0, index));
        String propertyNested = propertyName.substring(index + 1, propertyName.length());

        desc = propertyDesc.get(propertyMap);
        if (desc == null)
        {
            return null;  // prefix not a known property
        }

        if (desc.getPropertyType() instanceof Map)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), false);
            Map nestedTypes = (Map) desc.getPropertyType();
            EventPropertyGetter getterNestedMap = prop.getGetterMap(nestedTypes);
            if (getterNestedMap == null)
            {
                return null;
            }
            return new MapPropertyGetter(propertyMap, getterNestedMap);
        }
        else if (desc.getPropertyType() instanceof Class)
        {
            // ask the nested class to resolve the property
            Class simpleClass = (Class) desc.getPropertyType();
            EventType nestedEventType = eventAdapterService.addBeanType(simpleClass.getName(), simpleClass);
            final EventPropertyGetter nestedGetter = nestedEventType.getGetter(propertyNested);
            if (nestedGetter == null)
            {
                return null;
            }

            // construct getter for nested property
            return new MapPOJOEntryPropertyGetter(propertyMap, nestedGetter, eventAdapterService);
        }
        else
        {
            return null;
        }
    }

    public Class getPropertyType(String propertyName)
    {
        RevisionPropertyTypeDesc desc = propertyDesc.get(propertyName);
        if (desc != null)
        {
            if (desc.getPropertyType() instanceof Map)
            {
                return Map.class;
            }
            if (desc.getPropertyType() instanceof Class)
            {
                return (Class) desc.getPropertyType();
            }
            return null;
        }

        // dynamic property names note allowed
        if (propertyName.indexOf('?') != -1)
        {
            return null;
        }

        // see if this is a nested property
        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
        if (index == -1)
        {
            return null; // not a nested property
        }

        // Map event types allow 2 types of properties inside:
        //   - a property that is a Java object is interrogated via bean property getters and BeanEventType
        //   - a property that is a Map itself is interrogated via map property getters

        // Take apart the nested property into a map key and a nested value class property name
        String propertyMap = ASTFilterSpecHelper.unescapeDot(propertyName.substring(0, index));
        String propertyNested = propertyName.substring(index + 1, propertyName.length());

        desc = propertyDesc.get(propertyMap);
        if (desc == null)
        {
            return null;  // prefix not a known property
        }

        if (desc.getPropertyType() instanceof Map)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), false);
            Map nestedTypes = (Map) desc.getPropertyType();
            return prop.getPropertyTypeMap(nestedTypes);
        }
        else if (desc.getPropertyType() instanceof Class)
        {
            Class simpleClass = (Class) desc.getPropertyType();
            EventType nestedEventType = eventAdapterService.addBeanType(simpleClass.getName(), simpleClass);
            return nestedEventType.getPropertyType(propertyNested);
        }
        else
        {
            return null;
        }
    }

    public Class getUnderlyingType()
    {
        return RevisionEventType.class;
    }

    public String[] getPropertyNames()
    {
        return propertyNames;
    }

    public boolean isProperty(String property)
    {
        return getPropertyType(property) != null;
    }

    public EventType[] getSuperTypes()
    {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return null;
    }
}

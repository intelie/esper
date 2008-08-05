package com.espertech.esper.event.vaevent;

import com.espertech.esper.epl.parse.ASTFilterSpecHelper;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.BeanEventType;
import com.espertech.esper.event.property.*;

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
     * @param eventAdapterService for nested property handling
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
            Property prop = PropertyParser.parse(propertyName, eventAdapterService.getBeanEventTypeFactory(), false);
            if (prop instanceof SimpleProperty)
            {
                // there is no such property since it wasn't found earlier
                return null;
            }
            String atomic = null;
            if (prop instanceof IndexedProperty)
            {
                IndexedProperty indexedprop = (IndexedProperty) prop;
                atomic = indexedprop.getPropertyNameAtomic();
            }
            if (prop instanceof MappedProperty)
            {
                MappedProperty indexedprop = (MappedProperty) prop;
                atomic = indexedprop.getPropertyNameAtomic();
            }
            desc = propertyDesc.get(atomic);
            if (desc == null)
            {
                return null;
            }
            if (!(desc.getPropertyType() instanceof Class))
            {
                return null;
            }
            Class nestedClass = (Class) desc.getPropertyType();
            BeanEventType complexProperty = (BeanEventType) eventAdapterService.addBeanType(nestedClass.getName(), nestedClass);
            return prop.getGetter(complexProperty);
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

        // only nested classes supported for revision event types since deep property information not currently exposed by EventType
        if (desc.getPropertyType() instanceof Class)
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
            return new RevisionNestedPropertyGetter(desc.getRevisionGetter(), nestedGetter, eventAdapterService);
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
            return null;
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
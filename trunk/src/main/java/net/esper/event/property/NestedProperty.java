package net.esper.event.property;

import net.esper.event.*;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;

/**
 * This class represents a nested property, each nesting level made up of a property instance that
 * can be of type indexed, mapped or simple itself.
 * <p>
 * The syntax for nested properties is as follows.
 * <pre>
 * a.n
 * a[1].n
 * a('1').n
 * </pre>
 */
public class NestedProperty implements Property
{
    private List<Property> properties;
    private BeanEventAdapter beanEventAdapter;

    /**
     * Ctor.
     * @param properties is the list of Property instances representing each nesting level
     * @param beanEventAdapter is the chache and factory for event bean types and event wrappers
     */
    public NestedProperty(List<Property> properties, BeanEventAdapter beanEventAdapter)
    {
        this.properties = properties;
        this.beanEventAdapter = beanEventAdapter;
    }

    /**
     * Returns the list of property instances making up the nesting levels.
     * @return list of Property instances
     */
    public List<Property> getProperties()
    {
        return properties;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();

        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            Property property = it.next();
            EventPropertyGetter getter = property.getGetter(eventType);
            if (getter == null)
            {
                return null;
            }

            if (it.hasNext())
            {
                Class clazz = property.getPropertyType(eventType);
                if (clazz == null)
                {
                    // if the property is not valid, return null
                    return null;
                }
                // Map cannot be used to further nest as the type cannot be determined
                if (clazz == Map.class)
                {
                    return null;
                }
                if (clazz.isArray())
                {
                    return null;
                }
                eventType = (BeanEventType) beanEventAdapter.createBeanType(clazz);
            }
            getters.add(getter);
        }

        return new EventNestedPropertyGetter(getters, beanEventAdapter);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        Class result = null;

        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            Property property = it.next();
            result = property.getPropertyType(eventType);

            if (result == null)
            {
                // property not found, return null
                return null;
            }

            if (it.hasNext())
            {
                // Map cannot be used to further nest as the type cannot be determined
                if (result == Map.class)
                {
                    return null;
                }

                if (result.isArray())
                {
                    return null;
                }

                eventType = beanEventAdapter.createBeanType(result);
            }
        }

        return result;
    }
}

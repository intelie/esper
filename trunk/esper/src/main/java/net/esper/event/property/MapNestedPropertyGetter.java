package net.esper.event.property;

import net.esper.event.*;

import java.util.List;
import java.util.Map;

/**
 * Getter for one or more levels deep nested properties of maps.
 */
public class MapNestedPropertyGetter implements EventPropertyGetter
{
    private final EventPropertyGetter[] getterChain;

    /**
     * Ctor.
     * @param getterChain is the chain of getters to retrieve each nested property
     */
    public MapNestedPropertyGetter(List<EventPropertyGetter> getterChain)
    {
        this.getterChain = getterChain.toArray(new EventPropertyGetter[0]);
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object value = null;

        for (int i = 0; i < getterChain.length; i++)
        {
            Object result = getterChain[i].get(eventBean);

            if (result == null)
            {
                return null;
            }

            // this is not the last element
            if (i < (getterChain.length - 1))
            {
                if (!(result instanceof Map))
                {
                    return null;    // not a map, ignore value
                }
                eventBean = new MapEventBean((Map) result, null);                
            }
            else
            {
                value = result;
            }
        }
        return value;
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        int lastElementIndex = getterChain.length - 1;

        // walk the getter chain up to the previous-to-last element, returning its object value.
        // any null values in between mean the property does not exists
        for (int i = 0; i < getterChain.length - 1; i++)
        {
            Object result = getterChain[i].get(eventBean);

            if (result == null)
            {
                return false;
            }
            else
            {
                if (!(result instanceof Map))
                {
                    return false;
                }
                eventBean = new MapEventBean((Map)result, null);
            }
        }

        return getterChain[lastElementIndex].isExistsProperty(eventBean);
    }
}

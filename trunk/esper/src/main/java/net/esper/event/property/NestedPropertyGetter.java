package net.esper.event.property;

import net.esper.event.*;
import java.util.List;

/**
 * Getter for one or more levels deep nested properties.
 */
public class NestedPropertyGetter implements EventPropertyGetter
{
    private final EventPropertyGetter[] getterChain;
    private final BeanEventTypeFactory beanEventTypeFactory;

    /**
     * Ctor.
     * @param getterChain is the chain of getters to retrieve each nested property
     * @param beanEventTypeFactory is the chache and factory for event bean types and event wrappers
     */
    public NestedPropertyGetter(List<EventPropertyGetter> getterChain, BeanEventTypeFactory beanEventTypeFactory)
    {
        this.getterChain = getterChain.toArray(new EventPropertyGetter[0]);
        this.beanEventTypeFactory = beanEventTypeFactory;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object value = null;

        for (int i = 0; i < getterChain.length; i++)
        {
            value = getterChain[i].get(eventBean);

            if (value == null)
            {
                return null;
            }

            if (i < (getterChain.length - 1))
            {
                EventType type = beanEventTypeFactory.createBeanType(value.getClass().getName(), value.getClass());
                eventBean = new BeanEventBean(value, type);
            }
        }
        return value;
    }
}

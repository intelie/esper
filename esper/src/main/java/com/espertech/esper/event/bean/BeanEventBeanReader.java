package com.espertech.esper.event.bean;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.EventBeanReader;

import java.util.ArrayList;
import java.util.List;

public class BeanEventBeanReader implements EventBeanReader
{
    private BeanEventPropertyGetter[] getterArray;

    public BeanEventBeanReader(BeanEventType type)
    {
        String[] properties = type.getPropertyNames();
        List<BeanEventPropertyGetter> getters = new ArrayList<BeanEventPropertyGetter>();
        for (String property : properties)
        {
            BeanEventPropertyGetter getter = (BeanEventPropertyGetter) type.getGetter(property);
            if (getter != null)
            {
                getters.add(getter);
            }
        }
        getterArray = getters.toArray(new BeanEventPropertyGetter[getters.size()]);
    }

    public Object[] read(EventBean event)
    {
        Object underlying = event.getUnderlying();
        Object[] values = new Object[getterArray.length];
        for (int i = 0; i < getterArray.length; i++)
        {
            values[i] = getterArray[i].getBeanProp(underlying);
        }
        return values;
    }
}

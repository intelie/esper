package com.espertech.esper.event.xml;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;
import org.w3c.dom.NodeList;

public class XPathPropertyArrayItemGetter implements EventPropertyGetter
{
    private final EventPropertyGetter getter;
    private final int index;

    public XPathPropertyArrayItemGetter(EventPropertyGetter getter, int index)
    {
        this.getter = getter;
        this.index = index;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object result = getter.get(eventBean);
        if (!(result instanceof NodeList))
        {
            return null;
        }

        NodeList nodeList = (NodeList) result;
        if (nodeList.getLength() <= index)
        {
            return null;
        }
        return nodeList.item(index);
    }

    public Object getFragment(EventBean eventBean) throws PropertyAccessException
    {
        return null;  // TODO
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return false;  // TODO
    }
}

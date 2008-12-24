package com.espertech.esper.event.bean;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.EventBean;

public interface BeanEventPropertyGetter extends EventPropertyGetter
{
    public Object getBeanProp(Object object) throws PropertyAccessException;    
    public boolean isBeanExistsProperty(Object object);
}

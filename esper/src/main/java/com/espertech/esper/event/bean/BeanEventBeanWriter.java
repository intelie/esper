package com.espertech.esper.event.bean;

import com.espertech.esper.event.EventPropertyWriter;
import com.espertech.esper.event.EventBeanWriter;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

public class BeanEventBeanWriter implements EventBeanWriter
{
    private static final Log log = LogFactory.getLog(BeanEventBeanWriter.class);

    private final BeanEventPropertyWriter[] writers;

    public BeanEventBeanWriter(BeanEventPropertyWriter[] writers)
    {
        this.writers = writers;
    }

    public void write(Object[] values, EventBean event)
    {
        for (int i = 0; i < values.length; i++)
        {
            writers[i].write(values[i], event);
        }
    }
}

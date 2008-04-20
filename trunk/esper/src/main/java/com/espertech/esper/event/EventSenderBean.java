package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPRuntimeSPI;
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Set;
import java.util.HashSet;

public class EventSenderBean implements EventSender
{
    private final EPRuntimeEventSender runtime;
    private final BeanEventType beanEventType;
    private final Set<Class> compatibleClasses;

    public EventSenderBean(EPRuntimeEventSender runtime, BeanEventType beanEventType)
    {
        this.runtime = runtime;
        this.beanEventType = beanEventType;
        compatibleClasses = new HashSet<Class>();
    }

    public void sendEvent(Object event)
    {
        // type check
        if (event.getClass() != beanEventType.getUnderlyingType())
        {
            synchronized (this)
            {
                if (!compatibleClasses.contains(event.getClass()))
                {
                    if (JavaClassHelper.isSubclassOrImplementsInterface(event.getClass(), beanEventType.getUnderlyingType()))
                    {
                        compatibleClasses.add(event.getClass());
                    }
                    else
                    {
                        throw new EPException("Event object of type " + event.getClass().getName() +
                                " does not equal, extend or implement the type " + beanEventType.getUnderlyingType().getName() +
                                " of event type '" + beanEventType.getAlias() + "'");
                    }
                }
            }
        }

        runtime.processWrappedEvent(new BeanEventBean(event, beanEventType));        
    }
}

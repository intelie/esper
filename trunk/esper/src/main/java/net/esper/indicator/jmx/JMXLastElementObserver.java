package net.esper.indicator.jmx;

import javax.management.DynamicMBean;

import net.esper.event.EventBean;

/**
 * DynamicMBean interface extension to set the last element to render in a JMX view.
 */
public interface JMXLastElementObserver extends DynamicMBean
{
    /**
     * Update JMX last element view with a new value.
     * @param element is the new last value
     */
    public void setLastValue(EventBean element);
}

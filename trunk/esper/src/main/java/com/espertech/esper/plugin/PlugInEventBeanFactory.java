package com.espertech.esper.plugin;

import com.espertech.esper.event.EventBean;

import java.net.URI;

public interface PlugInEventBeanFactory
{
    public EventBean create(Object event, URI resolutionURI);
}

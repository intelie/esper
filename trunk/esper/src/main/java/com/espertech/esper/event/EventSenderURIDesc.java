package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.plugin.PlugInEventBeanFactory;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.core.EPRuntimeImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.util.List;

public class EventSenderURIDesc
{
    private final PlugInEventBeanFactory beanFactory;
    private final URI resolutionURI;
    private final URI representationURI;

    public EventSenderURIDesc(PlugInEventBeanFactory beanFactory, URI resolutionURI, URI representationURI)
    {
        this.beanFactory = beanFactory;
        this.resolutionURI = resolutionURI;
        this.representationURI = representationURI;
    }

    public URI getResolutionURI()
    {
        return resolutionURI;
    }

    public URI getRepresentationURI()
    {
        return representationURI;
    }

    public PlugInEventBeanFactory getBeanFactory()
    {
        return beanFactory;
    }
}

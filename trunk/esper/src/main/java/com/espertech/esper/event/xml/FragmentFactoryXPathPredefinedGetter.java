package com.espertech.esper.event.xml;

import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import org.w3c.dom.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Fragment factory for use with XPath explicit properties.
 */
public class FragmentFactoryXPathPredefinedGetter implements FragmentFactory
{
    private static final Log log = LogFactory.getLog(FragmentFactoryXPathPredefinedGetter.class);

    private final EventAdapterService eventAdapterService;
    private final String eventTypeAlias;
    private final String propertyName;

    private volatile EventType eventType;

    /**
     * Ctor.
     * @param eventAdapterService for event type lookup
     * @param eventTypeAlias alias to look up
     * @param propertyName property
     */
    public FragmentFactoryXPathPredefinedGetter(EventAdapterService eventAdapterService, String eventTypeAlias, String propertyName)
    {
        this.eventAdapterService = eventAdapterService;
        this.eventTypeAlias = eventTypeAlias;
        this.propertyName = propertyName;
    }

    public EventBean getEvent(Node result)
    {
        if (eventType == null)
        {
            EventType candidateEventType = eventAdapterService.getExistsTypeByAlias(eventTypeAlias);
            if (candidateEventType == null)
            {
                log.warn("Event type by name '" + eventTypeAlias + "' was not found for property '" + propertyName + "'");
                return null;
            }
            if (!(candidateEventType instanceof BaseXMLEventType))
            {
                log.warn("Event type by name '" + eventTypeAlias + "' is not an XML event type for property '" + propertyName + "'");
                return null;
            }
            eventType = candidateEventType;
        }

        return eventAdapterService.adapterForTypedDOM(result, eventType);
    }
}

package com.espertech.esper.event.xml;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventAdapterService;
import org.w3c.dom.Node;

public class FragmentFactorySchemaElementComplex implements FragmentFactory
{
    private final EventAdapterService eventAdapterService;
    private final SchemaElementComplex complex;
    private final BaseXMLEventType xmlEventType;

    private volatile EventType fragmentType;

    public FragmentFactorySchemaElementComplex(SchemaElementComplex complex, EventAdapterService eventAdapterService, BaseXMLEventType xmlEventType)
    {
        this.complex = complex;
        this.eventAdapterService = eventAdapterService;
        this.xmlEventType = xmlEventType;
    }

    public EventBean getEvent(Node result)
    {
        if (fragmentType == null)
        {
            xmlEventType.getFragmentType(complex.)
        }
        FragmentEventType
        return null;
    }

    
}

package com.espertech.esper.support.event;

import com.espertech.esper.event.vaevent.ValueAddEventService;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.ConfigurationRevisionEventType;
import com.espertech.esper.client.ConfigurationVariantStream;
import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.view.StatementStopService;

import java.util.Map;

public class SupportValueAddEventService implements ValueAddEventService
{
    public void init(Map<String, ConfigurationRevisionEventType> config, Map<String, ConfigurationVariantStream> variantStreams, EventAdapterService eventAdapterService)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addRevisionEventType(String alias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addVariantStream(String varianteventTypeName, ConfigurationVariantStream variantStreamConfig, EventAdapterService eventAdapterService) throws ConfigurationException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventType getValueAddUnderlyingType(String alias)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventType createRevisionType(String namedWindowName, String alias, StatementStopService statementStopService, EventAdapterService eventAdapterService)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isRevisionTypeName(String alias)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ValueAddEventProcessor getValueAddProcessor(String alias)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventType[] getValueAddedTypes()
    {
        return new EventType[0];
    }
}

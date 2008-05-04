package com.espertech.esper.event.rev;

import com.espertech.esper.client.ConfigurationRevisionEvent;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;

import java.util.Map;

public interface RevisionService
{
    public void init(Map<String, ConfigurationRevisionEvent> config, EventAdapterService eventAdapterService);
    public void add(String alias, ConfigurationRevisionEvent config, EventAdapterService eventAdapterService);
    
    public EventType getRevisionUnderlyingType(String alias);
    public boolean isRevisionTypeAlias(String alias);
    public RevisionEventType createRevisionType(String namedWindowName, String alias);        
    public RevisionEventType getNamedWindowRevisionType(String namedWindowName);
    public RevisionProcessor getRevisionProcessor(String alias);
}

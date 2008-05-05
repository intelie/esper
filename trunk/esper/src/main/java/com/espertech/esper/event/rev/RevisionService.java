package com.espertech.esper.event.rev;

import com.espertech.esper.client.ConfigurationRevisionEventType;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.StatementStopService;

import java.util.Map;

public interface RevisionService
{
    public void init(Map<String, ConfigurationRevisionEventType> config, EventAdapterService eventAdapterService);
    public void add(String alias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService);
    
    public EventType createRevisionType(String namedWindowName, String alias, StatementStopService statementStopService, EventAdapterService eventAdapterService);
    public EventType getRevisionUnderlyingType(String alias);
    public boolean isRevisionTypeAlias(String alias);
    public EventType getIsNamedWindowRevisionType(String namedWindowName, EventType eventType);
    public RevisionProcessor getRevisionProcessor(String alias);
}

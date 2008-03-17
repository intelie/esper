package com.espertech.esper.regression.event;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProvider;

public class MyGetterUpdateListener implements StatementAwareUpdateListener
{
    private final EventPropertyGetter symbolGetter;
    private final EventPropertyGetter volumeGetter;

    private String lastSymbol;
    private Long lastVolume;
    private EPStatement epStatement;
    private EPServiceProvider epServiceProvider;

    public void update(EventBean[] eventBeans, EventBean[] eventBeans1, EPStatement epStatement, EPServiceProvider epServiceProvider)
    {
        this.epStatement = epStatement;
        this.epServiceProvider = epServiceProvider;
        lastSymbol = (String) symbolGetter.get(eventBeans[0]);
        lastVolume = (Long) volumeGetter.get(eventBeans[0]);
    }

    public MyGetterUpdateListener(EventType eventType)
    {
        symbolGetter = eventType.getGetter("symbol");
        volumeGetter = eventType.getGetter("volume");
    }

    public String getLastSymbol()
    {
        return lastSymbol;
    }

    public Long getLastVolume()
    {
        return lastVolume;
    }

    public EPStatement getEpStatement()
    {
        return epStatement;
    }

    public EPServiceProvider getEpServiceProvider()
    {
        return epServiceProvider;
    }
}

package net.esper.regression.event;

import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.client.UpdateListener;
import net.esper.client.StatementAwareUpdateListener;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProvider;

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

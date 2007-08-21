package net.esper.client.soda;

import java.io.Serializable;

public class Filter implements Serializable
{
    private String eventTypeAlias;
    private Expression filter;

    public static Filter create(String eventTypeAlias)
    {
        return new Filter(eventTypeAlias);
    }

    public static Filter create(String eventTypeAlias, Expression filter)
    {
        return new Filter(eventTypeAlias, filter);
    }

    public Filter(String eventTypeAlias)
    {
        this.eventTypeAlias = eventTypeAlias;
    }

    public Filter(String eventTypeAlias, Expression filter)
    {
        this.eventTypeAlias = eventTypeAlias;
        this.filter = filter;
    }

    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }

    public void setEventTypeAlias(String eventTypeAlias)
    {
        this.eventTypeAlias = eventTypeAlias;
    }

    public Expression getFilter()
    {
        return filter;
    }

    public void setFilter(Expression filter)
    {
        this.filter = filter;
    }
}

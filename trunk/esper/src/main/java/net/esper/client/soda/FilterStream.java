package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;
import java.io.StringWriter;

public class FilterStream extends ProjectedStream
{
    private Filter filter;

    public static FilterStream create(Filter filter)
    {
        return new FilterStream(filter);
    }

    public static FilterStream create(String eventTypeAlias)
    {
        return new FilterStream(Filter.create(eventTypeAlias));
    }

    public static FilterStream create(String eventTypeAlias, String streamName)
    {
        return new FilterStream(Filter.create(eventTypeAlias), streamName);
    }

    public static FilterStream create(String eventTypeAlias, Expression filter)
    {
        return new FilterStream(Filter.create(eventTypeAlias, filter));
    }

    public FilterStream(Filter filter)
    {
        super(new ArrayList<View>(), null);
        this.filter = filter;
    }

    public FilterStream(Filter filter, String name)
    {
        super(new ArrayList<View>(), name);
        this.filter = filter;
    }

    public FilterStream(Filter filter, String name, List<View> views)
    {
        super(views, name);
        this.filter = filter;
    }

    public Filter getFilter()
    {
        return filter;
    }

    public void toEQLStream(StringWriter writer)
    {
        filter.toEQL(writer);
    }
}

package net.esper.eql.db;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;

import java.util.List;
import java.util.Map;

public class SupportPollingStrategy implements PollExecStrategy
{
    private Map<MultiKey<Object>, List<EventBean>> results;

    public SupportPollingStrategy(Map<MultiKey<Object>, List<EventBean>> results)
    {
        this.results = results;
    }

    public void start()
    {

    }

    public List<EventBean> poll(Object[] lookupValues)
    {
        return results.get(new MultiKey<Object>(lookupValues));
    }

    public void done()
    {

    }

    public void destroy()
    {

    }
}

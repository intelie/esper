package net.esper.example.stockticker.monitor;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

import net.esper.client.EmittedListener;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class StockTickerEmittedListener implements EmittedListener
{
    private List<Object> matchEvents = Collections.synchronizedList(new LinkedList<Object>());

    public void emitted(Object object)
    {
        log.info(".emitted Received emitted " + object);
        matchEvents.add(object);
    }

    public int getSize()
    {
        return matchEvents.size();
    }

    public List getMatchEvents()
    {
        return matchEvents;
    }

    public void clearMatched()
    {
        matchEvents.clear();
    }

    private static final Log log = LogFactory.getLog(StockTickerEmittedListener.class);
}
package net.esper.example.matchmaker.monitor;

import java.util.List;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.client.EmittedListener;
import net.esper.example.matchmaker.eventbean.MatchAlertBean;

public class MatchAlertListener implements EmittedListener
{
    private List<MatchAlertBean> emittedList = new LinkedList<MatchAlertBean>();

    public void emitted(Object object)
    {
        log.info(".emitted Emitted object=" + object);
        emittedList.add((MatchAlertBean) object);
    }

    public int getSize()
    {
        return emittedList.size();
    }

    public List getEmittedList()
    {
        return emittedList;
    }

    public int getAndClearEmittedCount()
    {
        int count = emittedList.size();
        emittedList.clear();
        return count;
    }

    public void clearEmitted()
    {
        emittedList.clear();
    }

    private static final Log log = LogFactory.getLog(MatchAlertListener.class);
}
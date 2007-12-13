package net.esper.eql.db;

import junit.framework.TestCase;
import net.esper.collection.MultiKey;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTableList;
import net.esper.eql.join.PollResultIndexingStrategy;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;

import java.util.*;

public class TestDatabasePollingViewable extends TestCase
{
    private DatabasePollingViewable pollingViewable;
    private PollResultIndexingStrategy indexingStrategy;

    public void setUp() throws Exception
    {
        List<String> inputProperties = Arrays.asList(new String[] {"s0.intPrimitive"});

        DataCache dataCache = new DataCacheLRUImpl(100);

        Map<String, Class> resultProperties = new HashMap<String, Class>();
        resultProperties.put("myvarchar", String.class);
        EventType resultEventType = SupportEventAdapterService.getService().createAnonymousMapType(resultProperties);

        Map<MultiKey<Object>, List<EventBean>> pollResults = new HashMap<MultiKey<Object>, List<EventBean>>();
        pollResults.put(new MultiKey<Object>(new Object[] {-1}), new LinkedList<EventBean>());
        pollResults.put(new MultiKey<Object>(new Object[] {500}), new LinkedList<EventBean>());
        SupportPollingStrategy supportPollingStrategy = new SupportPollingStrategy(pollResults);

        pollingViewable = new DatabasePollingViewable(1, inputProperties, supportPollingStrategy, dataCache, resultEventType);

        pollingViewable.validate(new SupportStreamTypeSvc3Stream(), null, null, null);

        indexingStrategy = new PollResultIndexingStrategy()
        {
            public EventTable index(List<EventBean> pollResult, boolean isActiveCache)
            {
                return new UnindexedEventTableList(pollResult);
            }
        };        
    }

    public void testPoll()
    {
        EventBean[][] input = new EventBean[2][2];
        input[0] = new EventBean[] {makeEvent(-1), null};
        input[1] = new EventBean[] {makeEvent(500), null};
        EventTable[] resultRows = pollingViewable.poll(input, indexingStrategy);

        // should have joined to two rows
        assertEquals(2, resultRows.length);
        assertTrue(resultRows[0].isEmpty());
        assertTrue(resultRows[1].isEmpty());
    }

    private EventBean makeEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        return SupportEventAdapterService.getService().adapterForBean(bean);
    }
}

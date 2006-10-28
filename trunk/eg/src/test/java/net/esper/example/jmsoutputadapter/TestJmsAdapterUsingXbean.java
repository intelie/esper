package net.esper.example.jmsoutputadapter;

import junit.framework.TestCase;
import net.esper.example.marketdatafeed.MarketDataEvent;
import net.esper.example.marketdatafeed.FeedEnum;
import net.esper.event.EventType;
import net.esper.support.event.SupportEventTypeFactory;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 13, 2006
 * Time: 5:25:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJmsAdapterUsingXbean extends TestCase {
    private JmsAdapterUsingXbean jmsAdapter;

    protected void setUp() throws Exception
    {
        jmsAdapter = new JmsAdapterUsingXbean();
        jmsAdapter.initialize("TestJmsAdapter", "MarketDataEvent", MarketDataEvent.class);
        EventType eventType = SupportEventTypeFactory.createBeanType(MarketDataEvent.class);
    }

    public void testEvent()
    {
        //String stmt = "insert into TicksPerSecond " +
        //              "select feed, count(*) as cnt from MarketDataEvent.win:time_batch(1) group by feed";
        String stmt = " select symbol as sym, feed, count(*) as cnt from MarketDataEvent.win:length(1) group by feed";
        jmsAdapter.setStatement(stmt);
        try {
            jmsAdapter.sendEvent(new MarketDataEvent("SYM", FeedEnum.FEED_A));
            jmsAdapter.receiveEvent();
            jmsAdapter.tearDown();
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } 
    }

}

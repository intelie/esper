package net.esper.example.feedexample;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.UpdateListener;
import junit.framework.TestCase;

public class Sample extends TestCase
{
    public void testIt()
    {
        String stmtText =
                "insert into ThroughputPerFeed " + 
                " select feed, count(*) as cnt " +
                "from " + FeedEvent.class.getName() + ".win:time_batch(1 sec) " +
                "group by feed";

        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
        EPStatement stmt = engine.getEPAdministrator().createEQL(stmtText);

        UpdateListener listener = new MyListener();
        stmt.addListener(listener);

        /*
        while(true)
        {
            FeedEvent event;
            event = new FeedEvent(FeedEnum.FEED_A, "IBM", 70);
            engine.getEPRuntime().sendEvent(event);
            event = new FeedEvent(FeedEnum.FEED_B, "IBM", 70);
            engine.getEPRuntime().sendEvent(event);
        }
        */
    }
}


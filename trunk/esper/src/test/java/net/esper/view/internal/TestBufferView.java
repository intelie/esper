package net.esper.view.internal;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.view.SupportBufferObserver;
import net.esper.support.event.SupportEventBeanFactory;

public class TestBufferView extends TestCase
{
    private BufferView bufferView;
    private SupportBufferObserver observer;

    public void setUp()
    {
        observer = new SupportBufferObserver();
        bufferView = new BufferView(1);
        bufferView.setObserver(observer);
    }

    public void testUpdate()
    {
        // Observer starts with no data
        assertFalse(observer.getAndResetHasNewData());

        // Send some data
        EventBean newEvents[] = makeBeans("n", 1);
        EventBean oldEvents[] = makeBeans("o", 1);
        bufferView.update(newEvents, oldEvents);

        // make sure received
        assertTrue(observer.getAndResetHasNewData());
        assertEquals(1, observer.getAndResetStreamId());
        assertNotNull(observer.getAndResetNewEventBuffer());
        assertNotNull(observer.getAndResetOldEventBuffer());

        // Reset and send null data
        assertFalse(observer.getAndResetHasNewData());
        bufferView.update(null, null);
        assertTrue(observer.getAndResetHasNewData());
    }

    public void testViewAttachesTo()
    {
        assertNull(bufferView.attachesTo(null));
    }

    private EventBean[] makeBeans(String id, int numTrades)
    {
        EventBean[] trades = new EventBean[numTrades];
        for (int i = 0; i < numTrades; i++)
        {
            SupportBean_A bean = new SupportBean_A(id + i);
            trades[i] = SupportEventBeanFactory.createObject(bean);
        }
        return trades;
    }
}
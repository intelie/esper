package net.esper.core;

import junit.framework.TestCase;
import net.esper.dispatch.DispatchService;
import net.esper.dispatch.DispatchServiceImpl;
import net.esper.event.EventBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.util.SupportUpdateListener;

public class TestUpdateDispatchView extends TestCase
{
    private UpdateDispatchViewBlockingWait updateDispatchView;
    private SupportUpdateListener listenerOne;
    private SupportUpdateListener listenerTwo;
    private DispatchService dispatchService;

    public void setUp()
    {
        listenerOne = new SupportUpdateListener();
        listenerTwo = new SupportUpdateListener();

        EPStatementListenerSet listenerSet = new EPStatementListenerSet();
        listenerSet.addListener(listenerOne);
        listenerSet.addListener(listenerTwo);

        dispatchService = new DispatchServiceImpl();
        updateDispatchView = new UpdateDispatchViewBlockingWait(null, null, listenerSet, dispatchService, 1000);
    }

    public void testUpdateOnceAndDispatch()
    {
        EventBean[] oldData = makeEvents("old");
        EventBean[] newData = makeEvents("new");
        updateDispatchView.update(newData, oldData);

        assertFalse(listenerOne.isInvoked() || listenerTwo.isInvoked());
        dispatchService.dispatch();
        assertTrue(listenerOne.isInvoked() && listenerTwo.isInvoked());
        assertTrue(listenerOne.getLastNewData()[0] == newData[0]);
        assertTrue(listenerTwo.getLastOldData()[0] == oldData[0]);
    }

    public void testUpdateTwiceAndDispatch()
    {
        EventBean[] oldDataOne = makeEvents("old1");
        EventBean[] newDataOne = makeEvents("new1");
        updateDispatchView.update(newDataOne, oldDataOne);

        EventBean[] oldDataTwo = makeEvents("old2");
        EventBean[] newDataTwo = makeEvents("new2");
        updateDispatchView.update(newDataTwo, oldDataTwo);

        assertFalse(listenerOne.isInvoked() || listenerTwo.isInvoked());
        dispatchService.dispatch();
        assertTrue(listenerOne.isInvoked() && listenerTwo.isInvoked());
        assertTrue(listenerOne.getLastNewData()[1] == newDataTwo[0]);
        assertTrue(listenerTwo.getLastOldData()[1] == oldDataTwo[0]);
    }

    private EventBean[] makeEvents(String text)
    {
        return new EventBean[] { SupportEventBeanFactory.createObject(text) };
    }
}

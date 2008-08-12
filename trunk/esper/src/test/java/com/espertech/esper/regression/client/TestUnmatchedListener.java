package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;

import java.util.List;
import java.util.ArrayList;

public class TestUnmatchedListener extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testUnmatchedSendEvent()
    {
        MyUnmatchedListener listener = new MyUnmatchedListener();
        epService.getEPRuntime().setUnmatchedListener(listener);

        // no statement, should be unmatched
        SupportBean event = sendEvent();
        assertEquals(1, listener.getReceived().size());
        assertSame(event, listener.getReceived().get(0).getUnderlying());
        listener.reset();

        // no unmatched listener
        epService.getEPRuntime().setUnmatchedListener(null);
        sendEvent();
        assertEquals(0, listener.getReceived().size());

        // create statement and re-register unmatched listener
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from " + SupportBean.class.getName());
        epService.getEPRuntime().setUnmatchedListener(listener);
        sendEvent();
        assertEquals(0, listener.getReceived().size());

        // stop statement
        stmt.stop();
        event = sendEvent();
        assertEquals(1, listener.getReceived().size());
        assertSame(event, listener.getReceived().get(0).getUnderlying());
        listener.reset();

        // start statement
        stmt.start();
        sendEvent();
        assertEquals(0, listener.getReceived().size());

        // destroy statement
        stmt.destroy();
        event = sendEvent();
        assertEquals(1, listener.getReceived().size());
        assertSame(event, listener.getReceived().get(0).getUnderlying());
        listener.reset();
    }

    public void testUnmatchedInsertInto()
    {
        MyUnmatchedListener listener = new MyUnmatchedListener();
        epService.getEPRuntime().setUnmatchedListener(listener);

        // create insert into
        EPStatement insertInto = epService.getEPAdministrator().createEPL("insert into MyEvent select string from " + SupportBean.class.getName());

        // no statement, should be unmatched
        sendEvent("E1");
        assertEquals(1, listener.getReceived().size());
        assertEquals("E1", listener.getReceived().get(0).get("string"));
        listener.reset();

        // stop insert into, now SupportBean itself is unmatched
        insertInto.stop();
        SupportBean event = sendEvent("E2");
        assertEquals(1, listener.getReceived().size());
        assertSame(event, listener.getReceived().get(0).getUnderlying());
        listener.reset();

        // start insert-into
        sendEvent("E3");
        assertEquals(1, listener.getReceived().size());
        assertEquals("E3", listener.getReceived().get(0).get("string"));
        listener.reset();
    }

    private SupportBean sendEvent()
    {
        SupportBean bean = new SupportBean();
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendEvent(String string)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    public class MyUnmatchedListener implements UnmatchedListener
    {
        private List<EventBean> received;

        public MyUnmatchedListener()
        {
            this.received = new ArrayList<EventBean>();
        }

        public void update(EventBean event)
        {
            received.add(event);
        }

        public List<EventBean> getReceived()
        {
            return received;
        }

        public void reset()
        {
            received.clear();
        }
    }
}

package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.StatementLifecycleEvent;
import com.espertech.esper.core.StatementLifecycleObserver;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TestEPServiceProviderSPIStmtLifecycle extends TestCase
{
    public void testObserverStateChange()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        EPServiceProvider service = EPServiceProviderManager.getDefaultProvider(config);
        EPServiceProviderSPI spi = (EPServiceProviderSPI) service;

        SupportLifecycleObserver observer = new SupportLifecycleObserver();
        spi.getStatementLifecycleSvc().addObserver(observer);
        EPStatement stmt = service.getEPAdministrator().createEPL("select * from " + SupportBean.class.getName());
        assertEquals("CREATE;STATECHANGE;", observer.getEventsAsString());

        observer.flush();
        stmt.stop();
        assertEquals("STATECHANGE;", observer.getEventsAsString());
        assertEquals(stmt.getName(), observer.getEvents().get(0).getStatement().getName());

        observer.flush();
        stmt.addListener(new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                ;
            }
        });
        assertEquals("LISTENER_ADD;", observer.getEventsAsString());
        assertNotNull(observer.getLastContext());
        assertTrue(observer.getLastContext()[0] instanceof UpdateListener);

        observer.flush();
        stmt.removeAllListeners();
        assertEquals(StatementLifecycleEvent.LifecycleEventType.LISTENER_REMOVE_ALL.toString()+";", observer.getEventsAsString());
    }

    public class SupportLifecycleObserver implements StatementLifecycleObserver
    {
        private List<StatementLifecycleEvent> events = new ArrayList<StatementLifecycleEvent>();
        private Object[] lastContext;

        public void observe(StatementLifecycleEvent event)
        {
            events.add(event);
            lastContext = event.getParams();
        }

        public Object[] getLastContext()
        {
            return lastContext;
        }

        public List<StatementLifecycleEvent> getEvents()
        {
            return events;
        }

        public String getEventsAsString()
        {
            String result = "";
            for (StatementLifecycleEvent event : events) {
                result += event.getEventType().toString() + ";";
            }
            return result;
        }

        public void flush()
        {
            events.clear();
        }
    }

}

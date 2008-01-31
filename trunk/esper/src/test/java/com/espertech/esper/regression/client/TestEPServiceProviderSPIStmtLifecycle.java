package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.adapter.SupportAdapterLoader;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.adapter.AdapterLoader;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.StatementLifecycleSvc;
import com.espertech.esper.core.StatementLifecycleObserver;
import com.espertech.esper.core.StatementLifecycleEvent;

import java.util.*;

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
        EPStatement stmt = service.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName());
        
        assertEquals(0, observer.getEvents().size());
        stmt.stop();
        assertEquals(1, observer.getEvents().size());
        assertEquals(stmt.getName(), observer.getEvents().get(0).getStatementName());
    }

    public class SupportLifecycleObserver implements StatementLifecycleObserver
    {
        private List<StatementLifecycleEvent> events = new ArrayList<StatementLifecycleEvent>();

        public void observe(StatementLifecycleEvent event)
        {
            events.add(event);
        }

        public List<StatementLifecycleEvent> getEvents()
        {
            return events;
        }
    }

}

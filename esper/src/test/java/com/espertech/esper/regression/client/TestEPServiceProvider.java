package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.client.SupportServiceStateListener;
import com.espertech.esper.support.client.SupportStmtLifecycleObserver;
import com.espertech.esper.support.client.SupportStatementStateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.StatementLifecycleEvent;
import com.espertech.esper.client.EventBean;

import java.util.Arrays;

public class TestEPServiceProvider extends TestCase
{
    private EPServiceProvider epService;
    private SupportServiceStateListener listener;
    private SupportServiceStateListener listenerTwo;

    public void setUp()
    {
        listener = new SupportServiceStateListener();
        listenerTwo = new SupportServiceStateListener();

        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testDefaultEngine()
    {
        assertEquals("default", EPServiceProviderManager.getDefaultProvider().getURI());
        EPServiceProvider engineDefault = EPServiceProviderManager.getDefaultProvider();

        EPServiceProvider engine = EPServiceProviderManager.getProvider("default");
        assertSame(engineDefault, engine);

        engine = EPServiceProviderManager.getProvider(null);
        assertSame(engineDefault, engine);

        engine = EPServiceProviderManager.getProvider(null, SupportConfigFactory.getConfiguration());
        assertSame(engineDefault, engine);

        String[] uris = EPServiceProviderManager.getProviderURIs();
        assertTrue(Arrays.asList(uris).contains("default"));
    }

    public void testListenerStateChange()
    {
        epService.addServiceStateListener(listener);
        epService.destroy();
        assertSame(epService, listener.assertOneGetAndResetDestroyedEvents());

        epService.initialize();
        assertSame(epService, listener.assertOneGetAndResetInitializedEvents());

        epService.removeAllServiceStateListeners();
        epService.initialize();
        assertTrue(listener.getInitializedEvents().isEmpty());

        epService.addServiceStateListener(listener);
        epService.addServiceStateListener(listenerTwo);
        epService.initialize();
        assertSame(epService, listener.assertOneGetAndResetInitializedEvents());
        assertSame(epService, listenerTwo.assertOneGetAndResetInitializedEvents());

        epService.removeServiceStateListener(listener);
        epService.initialize();
        assertSame(epService, listenerTwo.assertOneGetAndResetInitializedEvents());
        assertTrue(listener.getInitializedEvents().isEmpty());
    }

    public void testStatementStateChange()
    {
        EPServiceProviderSPI spi = (EPServiceProviderSPI) epService;

        SupportStmtLifecycleObserver observer = new SupportStmtLifecycleObserver();
        spi.getStatementLifecycleSvc().addObserver(observer);
        SupportStatementStateListener listener = new SupportStatementStateListener();
        epService.addStatementStateListener(listener);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from " + SupportBean.class.getName());
        assertEquals("CREATE;STATECHANGE;", observer.getEventsAsString());
        assertEquals(stmt, listener.assertOneGetAndResetCreatedEvents());
        assertEquals(stmt, listener.assertOneGetAndResetStateChangeEvents());

        observer.flush();
        stmt.stop();
        assertEquals("STATECHANGE;", observer.getEventsAsString());
        assertEquals(stmt.getName(), observer.getEvents().get(0).getStatement().getName());
        assertEquals(stmt, listener.assertOneGetAndResetStateChangeEvents());

        observer.flush();
        stmt.addListener(new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            }
        });
        assertEquals("LISTENER_ADD;", observer.getEventsAsString());
        assertNotNull(observer.getLastContext());
        assertTrue(observer.getLastContext()[0] instanceof UpdateListener);

        observer.flush();
        stmt.removeAllListeners();
        assertEquals(StatementLifecycleEvent.LifecycleEventType.LISTENER_REMOVE_ALL.toString()+";", observer.getEventsAsString());

        stmt.destroy();
        assertEquals(stmt, listener.assertOneGetAndResetStateChangeEvents());
    }

}

package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

public class TestViewStartStop extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement sizeView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        String viewExpr = "select * from " + SupportBean.class.getName() + ".std:size()";

        sizeView = epService.getEPAdministrator().createEQL(viewExpr);
    }

    public void testSameWindowReuse()
    {
        String viewExpr = "select * from " + SupportBean.class.getName() + ".win:length(3)";
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(viewExpr);
        stmtOne.addListener(testListener);

        // send a couple of events
        sendEvent(1);
        sendEvent(2);
        sendEvent(3);
        sendEvent(4);

        // create same statement again
        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(viewExpr);
        stmtTwo.addListener(testListenerTwo);

        // Send event, no old data should be received
        sendEvent(5);
        assertNull(testListenerTwo.getLastOldData());
    }

    public void testStartStop()
    {
        // View created is automatically started
        assertEquals(0l, sizeView.iterator().next().get("size"));
        sizeView.stop();

        // Send an event, view stopped
        sendEvent();
        assertNull(sizeView.iterator());

        // Start view
        sizeView.start();
        assertEquals(0l, sizeView.iterator().next().get("size"));

        // Send event
        sendEvent();
        assertEquals(1l, sizeView.iterator().next().get("size"));

        // Stop view
        sizeView.stop();
        assertNull(sizeView.iterator());

        // Start again, iterator is zero
        sizeView.start();
        assertEquals(0l, sizeView.iterator().next().get("size"));
    }

    public void testAddRemoveListener()
    {
        // View is started when created

        // Add listener send event
        sizeView.addListener(testListener);
        assertNull(testListener.getLastNewData());
        assertEquals(0l, sizeView.iterator().next().get("size"));
        sendEvent();
        assertEquals(1l, testListener.getAndResetLastNewData()[0].get("size"));
        assertEquals(1l, sizeView.iterator().next().get("size"));

        // Stop view, send event, view
        sizeView.stop();
        sendEvent();
        assertNull(sizeView.iterator());
        assertNull(testListener.getLastNewData());

        // Start again
        sizeView.removeListener(testListener);
        sizeView.addListener(testListener);
        sizeView.start();

        sendEvent();
        assertEquals(1l, testListener.getAndResetLastNewData()[0].get("size"));
        assertEquals(1l, sizeView.iterator().next().get("size"));

        // Stop again, leave listeners
        sizeView.stop();
        sizeView.start();
        sendEvent();
        assertEquals(1l, testListener.getAndResetLastNewData()[0].get("size"));

        // Remove listener, send event
        sizeView.removeListener(testListener);
        sendEvent();
        assertNull(testListener.getLastNewData());

        // Add listener back, send event
        sizeView.addListener(testListener);
        sendEvent();
        assertEquals(3l, testListener.getAndResetLastNewData()[0].get("size"));
    }

    private void sendEvent()
    {
        sendEvent(-1);
    }

    private void sendEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}

package net.esper.regression.pattern;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestPatternStartStop extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement patternStmt;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        String viewExpr = "every tag=" + SupportBean.class.getName();

        patternStmt = epService.getEPAdministrator().createPattern(viewExpr);
    }

    public void testStartStop()
    {
        // Pattern started when created
        assertFalse(patternStmt.iterator().hasNext());

        // Stop pattern
        patternStmt.stop();
        sendEvent();
        assertNull(patternStmt.iterator());

        // Start pattern
        patternStmt.start();
        assertFalse(patternStmt.iterator().hasNext());

        // Send event
        SupportBean event = sendEvent();
        assertSame(event, patternStmt.iterator().next().get("tag"));

        // Stop pattern
        patternStmt.stop();
        assertNull(patternStmt.iterator());

        // Start again, iterator is zero
        patternStmt.start();
        assertFalse(patternStmt.iterator().hasNext());
    }

    public void testAddRemoveListener()
    {
        // Pattern started when created

        // Add listener
        patternStmt.addListener(testListener);
        assertNull(testListener.getLastNewData());
        assertFalse(patternStmt.iterator().hasNext());

        // Send event
        SupportBean event = sendEvent();
        assertEquals(event, testListener.getAndResetLastNewData()[0].get("tag"));
        assertSame(event, patternStmt.iterator().next().get("tag"));

        // Remove listener
        patternStmt.removeListener(testListener);
        event = sendEvent();
        assertSame(event, patternStmt.iterator().next().get("tag"));
        assertNull(testListener.getLastNewData());

        // Add listener back
        patternStmt.addListener(testListener);
        event = sendEvent();
        assertSame(event, patternStmt.iterator().next().get("tag"));
        assertEquals(event, testListener.getAndResetLastNewData()[0].get("tag"));
    }

    private SupportBean sendEvent()
    {
        SupportBean event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        return event;
    }
}

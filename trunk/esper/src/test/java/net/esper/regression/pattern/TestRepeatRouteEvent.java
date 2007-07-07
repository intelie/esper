package net.esper.regression.pattern;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.UpdateListener;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;

public class TestRepeatRouteEvent extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement patternStmt;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        String viewExpr = "every tag=" + SupportBean.class.getName();

        patternStmt = epService.getEPAdministrator().createPattern(viewExpr);
    }

    /**
     * Test route of an event within a listener.
     * The Listener when it receives an event will generate a single new event
     * that it routes back into the runtime, up to X number of times.
     */
    public void testRouteSingle()
    {
        SingleRouteUpdateListener listener = new SingleRouteUpdateListener();
        patternStmt.addListener(listener);

        // Send first event that triggers the loop
        sendEvent(0);

        // Should have fired X times
        assertEquals(1000, listener.getCount());
    }

    /**
     * Test route of multiple events within a listener.
     * The Listener when it receives an event will generate multiple new events
     * that it routes back into the runtime, up to X number of times.
     */
    public void testRouteCascade()
    {
        CascadeRouteUpdateListener listener = new CascadeRouteUpdateListener();
        patternStmt.addListener(listener);

        // Send first event that triggers the loop
        sendEvent(2);       // the 2 translates to number of new events routed

        // Should have fired X times
        assertEquals(9, listener.getCountReceived());
        assertEquals(8, listener.getCountRouted());

        //  Num    Received         Routes      Num
        //  2             1           2         3
        //  3             2           6         4
        //  4             6             -
    }

    public void testRouteTimer()
    {
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        // define time-based pattern and listener
        String viewExpr = "timer:at(*,*,*,*,*,*)";
        EPStatement atPatternStmt = epService.getEPAdministrator().createPattern(viewExpr);
        SingleRouteUpdateListener timeListener = new SingleRouteUpdateListener();
        atPatternStmt.addListener(timeListener);

        // register regular listener
        SingleRouteUpdateListener eventListener = new SingleRouteUpdateListener();
        patternStmt.addListener(eventListener);

        assertEquals(0, timeListener.getCount());
        assertEquals(0, eventListener.getCount());

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10000));

        assertEquals(1, timeListener.getCount());
        assertEquals(1000, eventListener.getCount());
    }

    private SupportBean sendEvent(int intValue)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intValue);
        epService.getEPRuntime().sendEvent(event);
        return event;
    }

    private SupportBean routeEvent(int intValue)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intValue);
        epService.getEPRuntime().route(event);
        return event;
    }

    class SingleRouteUpdateListener implements UpdateListener {

        private int count = 0;

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            count++;
            if (count < 1000)
            {
                routeEvent(0);
            }
        }

        public int getCount()
        {
            return count;
        }
    };

    class CascadeRouteUpdateListener implements UpdateListener {

        private int countReceived = 0;
        private int countRouted = 0;

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            countReceived++;
            SupportBean event = (SupportBean) (newEvents[0].get("tag"));
            int numNewEvents = event.getIntPrimitive();

            for (int i = 0; i < numNewEvents; i++)
            {
                if (numNewEvents < 4)
                {
                    routeEvent(numNewEvents + 1);
                    countRouted++;
                }
            }
        }

        public int getCountReceived()
        {
            return countReceived;
        }

        public int getCountRouted()
        {
            return countRouted;
        }
    };
}
